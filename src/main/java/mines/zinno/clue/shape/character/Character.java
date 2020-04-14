package mines.zinno.clue.shape.character;

import javafx.application.Platform;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.layout.board.ClueBoard;
import mines.zinno.clue.listener.OnMoveListener;
import mines.zinno.clue.listener.OnTurnEndListener;
import mines.zinno.clue.util.Location;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.vo.GuessVO;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.shape.place.Teleportable;
import mines.zinno.clue.util.tree.Node;
import mines.zinno.clue.util.tree.Tree;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The {@link Character} class extends JavaFX's {@link Circle} class. It holds information and logic pertaining to
 * a character's position and status.
 */
public abstract class Character extends Circle {
    
    private static final int NUM_DICE = 1;
    
    protected final Clue game;
    protected final Suspect character;
    
    protected List<Card> providedCards = new ArrayList<>();

    protected Turn turn;
    protected int rollNum;
    protected Place curPlace;
    protected Set<Place> posMoves = new HashSet<>();

    private final List<OnTurnEndListener<Character>> turnEndListeners = new ArrayList<>();
    private final List<OnMoveListener<Character>> moveListeners = new ArrayList<>();
    
    public Character(Clue game, Suspect character, Place startPlace) {
        this.game = game;
        this.character = character;
        
        if(game != null) {
            game.getController().getBoard().getChildren().add(this);
            display();
        }
        
        moveTo(startPlace, true);
    }

    /**
     * Called by {@link mines.zinno.clue.runner.ClueRunner} when a character's turn is ready to begin
     */
    public void beginTurn() {
        this.turn = Turn.PRE_ROLL;
        updateTurnEndListeners();
    }

    /**
     * Called by {@link mines.zinno.clue.runner.ClueRunner} when a character's turn ends
     */
    public void endTurn() {
        this.turn = Turn.OTHER;
        updateTurnEndListeners();
    }

    /**
     * Roll dice
     */
    public int roll() {
        int rollNum = 0;
        for(int i = 0; i < NUM_DICE; i++)
            rollNum += Math.random() * 6 + 1;
        this.rollNum = rollNum;

        this.posMoves = calcPosMoves();

        this.turn = Turn.POST_ROLL;
        updateTurnEndListeners();
        

        //TODO move to turn end  listener
        Platform.runLater(() -> 
                game.getController().getInfoLabel().setText(
                        Action.ROLL_NUM.getText((this instanceof Player) ? "You" : this.getCharacter().getName(),
                                this.getRollNum()
                        )
                )
        );
        
        return rollNum;
    }

    /**
     * Calculate possible moves from {@link Character#getCurPlace()} and {@link Character#getRollNum()}
     */
    public Set<Place> calcPosMoves() {
        return this.calcPosMoves(this.getCurPlace(), this.getRollNum());
    }

    /**
     * Calculate possible moves from a given place and distance
     */
    public Set<Place> calcPosMoves(Place loc, int distance) {
        return this.calcPosMoves(loc, distance, distance+8);
    }

    /**
     * Calculate possible moves from a given place and distance
     */
    public Set<Place> calcPosMoves(Place startLoc, int distance, int maxSpread) {
        
        if(distance == 0)
            return new HashSet<>();
        
        Predicate<Place> placeReqs = (place) -> place != null && !place.isOccupied();
        
        Tree<Place> tree = new Tree<>(startLoc);
        tree.populate(
                (curNode) -> 
                        // This casts correctly. Java doesn't like Parameterized arrays
                        Arrays.stream(curNode.getValue().getAdjacent())
                                .filter(placeReqs)
                                .filter((adj) -> (calcDistance(curNode) + adj.getMoveCost() <= distance))
                                .map((adj) -> new Node<>(adj, curNode))
                                .toArray(Node[]::new),
                (curClosest, other) -> calcDistance(curClosest) > calcDistance(other),
                maxSpread
        );

        return tree.retrieveAllValues().stream()
                .filter(placeReqs)
                .collect(Collectors.toSet());
    }

    private int calcDistance(Tree<Place> placeNode) {
        int distance = 0;
        while(placeNode instanceof Node && ((Node<Place>) placeNode).getParent() != null) {
            distance += placeNode.getValue().getMoveCost();
            placeNode = ((Node<Place>) placeNode).getParent();
        }
        return distance;
    }

    /**
     * Request movement to a place
     */
    public void moveTo(Place place) {
        this.moveTo(place, false);
    }

    /**
     * Move to a place
     * 
     * @param forceMove Force movement (t:y; f:n) (Currently not used)
     */
    public void moveTo(Place place, boolean forceMove) {
        if(place instanceof Teleportable) {
            place = game.getController().getBoard().getItemFromCoordinate(((Teleportable) place).teleportTo());
        }
        
        this.setVisible(true);
        
        // Set turn to post move
        this.turn = Turn.POST_MOVE;
        
        if(curPlace != null) {
            // Mark previous place as unoccupied
            curPlace.setOccupied(false);

            if(!forceMove) {
                // Decrement roll number
                int cost = place.getDistance(curPlace);
                this.rollNum -= (cost == -1) ? this.rollNum : cost;
            }
        }
        
        // Move to the void
        if(place == null) {
            this.setVisible(false);
            this.setCenterX(Integer.MAX_VALUE);
            this.setCenterY(Integer.MAX_VALUE);
            return;
        }
        
        // Move to new place
        this.curPlace = place;
        Location location = curPlace.getCenter();
        place.setOccupied(true);

        Platform.runLater(() -> {
            this.setCenterX(location.getX());
            this.setCenterY(location.getY());
            this.toFront();
        });

        if(!forceMove)
            // Calc new possible moves
            this.posMoves = calcPosMoves();

        updateMoveListeners();
        updateTurnEndListeners();
    }

    public Boolean guess(GuessVO guessVO) {
        return this.guess(guessVO.suspect, guessVO.room, guessVO.weapon);
    }
    
    /**
     * Make a guess
     * 
     * @return True when another player shows a card; False when no card is shown; null if the player wins or loses
     */
    public Pair<RevealContext, List<String>> guess(Suspect suspect, Room room, Weapon weapon) {
        this.turn = Turn.POST_GUESS;
        
        Pair<RevealContext, List<String>> returnVal = new Pair<>(null, new ArrayList<>());
        boolean isFound = false;
        
        for(int i = 1; i < game.getCharacters().size(); i++) {
            Character c = game.getCharacters().get(i);
            
            for(Card card : c.getCards()) {
                if(!(card.getName().equals(suspect.getName()) ||
                        card.getName().equals(room.getName()) ||
                        card.getName().equals(weapon.getName())))
                    continue;

                if (!(((RoomPlace) this.getCurPlace()).getRoom().equals(Room.EXIT)))
                    returnVal.getValue().add(Action.CLUE.getText(c, card));
                isFound = true;
                 break;
            }
            
            if(isFound)
                break;
        }
        
        // Character not in exit room
        if (!(((RoomPlace) this.getCurPlace()).getRoom().equals(Room.EXIT)))
            return isFound;

        // Character has won the game
        if(!isFound) {
            onWin();
            game.setPlaying(false);
            return null;
        }

        // Character has lost the game
        onLose();
        for(Object o : game.getCharacters()) {
            Character c = (Character) o;
            for(Card card : getCards())
                c.receiveCard(this, card, RevealContext.LOST_GAME);
        }
        game.getCharacters().remove(this);
        this.moveTo(null, true);

        return null;
    }

    /**
     * Add an observer updated when a player moves
     */
    public void addMoveListener(OnMoveListener<Character> moveListener) {
        this.moveListeners.add(moveListener);
    }

    /**
     * Update move listeners
     */
    protected void updateMoveListeners() {
        for(OnMoveListener<Character> moveListener : this.moveListeners) {
            moveListener.update(this);
        }
    }

    /**
     * Add an observer updated when a player moves
     */
    public void addTurnEndListener(OnTurnEndListener<Character> turnEndListener) {
        this.turnEndListeners.add(turnEndListener);
    }

    /**
     * Update move listeners
     */
    protected void updateTurnEndListeners() {
        for(OnTurnEndListener<Character> turnEndListener : this.turnEndListeners) {
            turnEndListener.update(this);
        }
    }

    /**
     * Called when a character wins
     */
    public abstract void onWin();

    /**
     * Called when a character loses
     */
    public abstract void onLose();

    /**
     * Get character's {@link Suspect}
     */
    public Suspect getCharacter() {
        return character;
    }

    /**
     * Set the character's {@link Turn}
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * Get the character's {@link Turn}
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * Get the character's roll number
     */
    public int getRollNum() {
        return rollNum;
    }

    /**
     * Get the character's current {@link Place}
     */
    public Place getCurPlace() {
        return curPlace;
    }

    /**
     * Get the character's possible moves
     * 
     * @return {@link Set}<{@link Place}>
     */
    public Set<Place> getPosMoves() {
        if(posMoves == null)
            this.posMoves = calcPosMoves();
        return posMoves;
    }

    /**
     * Get the character's provided cards
     */
    public List<Card> getCards() {
        return this.providedCards;
    }

    /**
     * Provide character with a card
     */
    public void addProvidedCard(Card card) {
        this.providedCards.add(card);
        receiveCard(null, card, RevealContext.PROVIDED);
    }

    /**
     * Display character
     */
    private void display() {
        ClueBoard board = this.game.getController().getBoard();
        this.setRadius(Math.min(board.getGrid()[0][0].getWidth(), board.getGrid()[0][0].getHeight())/1.66);
        this.setFill(character.getColor());
        this.setVisible(true);
    }
}
