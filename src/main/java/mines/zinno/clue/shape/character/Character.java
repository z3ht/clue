package mines.zinno.clue.shape.character;

import javafx.application.Platform;
import javafx.scene.shape.Circle;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.layout.board.ClueBoard;
import mines.zinno.clue.shape.character.handler.identifier.GuessHandle;
import mines.zinno.clue.shape.character.handler.identifier.RevealHandle;
import mines.zinno.clue.shape.character.listener.OnRoll;
import mines.zinno.clue.shape.character.listener.OnTurnListener;
import mines.zinno.clue.shape.character.handler.GuessHandler;
import mines.zinno.clue.util.Location;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.vo.GuessVO;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.shape.place.Teleportable;
import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.handler.basic.InsertHandler;
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

    private final GuessHandler guessHandler;
    private final List<OnTurnListener<Character>> turnListeners = new ArrayList<>();

    public Character(Clue game, GuessHandler guessHandler, Suspect character, Place startPlace) {
        this.game = game;
        this.guessHandler = guessHandler;
        this.character = character;

        this.turnListeners.add(this.guessHandler);
        this.turnListeners.add(new OnRoll(game));

        if(this.game != null) {
            this.game.getController().getBoard().getChildren().add(this);
            display();
        }
        
        moveTo(startPlace, true);
    }

    /**
     * Called by {@link mines.zinno.clue.runner.ClueRunner} when a character's turn is ready to begin
     */
    public void beginTurn() {
        this.turn = Turn.PRE_ROLL;
        updateTurnListeners();
    }

    /**
     * Called by {@link mines.zinno.clue.runner.ClueRunner} when a character's turn ends
     */
    public void endTurn() {
        this.turn = Turn.OTHER;
        updateTurnListeners();
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
        updateTurnListeners();
        
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
    @SuppressWarnings("unchecked")
    public Set<Place> calcPosMoves(Place startLoc, int distance, int maxSpread) {
        
        if(distance == 0)
            return new HashSet<>();
        
        final Predicate<Place> placeReqs = (place) -> place != null && !place.isOccupied();
        
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

        updateTurnListeners();
    }

    public void guess(GuessVO guessVO) {
        this.guess(guessVO.suspect, guessVO.room, guessVO.weapon);
    }
    
    /**
     * Make a guess
     */
    public void guess(Suspect suspect, Room room, Weapon weapon) {
        this.turn = Turn.POST_GUESS;

        guessHandler
                .get(InsertHandler.class, GuessHandle.class)
                .insert(Handler.ALL, this, suspect, room, weapon);

        boolean isFound = false;
        for(int i = 1; i < game.getCharacters().size(); i++) {
            Character character = game.getCharacters().get(i);

            for(Card card : character.getCards()) {
                if(!(card.getName().equals(suspect.getName()) ||
                        card.getName().equals(room.getName()) ||
                        card.getName().equals(weapon.getName())))
                    continue;

                guessHandler
                        .get(InsertHandler.class, RevealHandle.class)
                        .insert(RevealContext.ON_GUESS, character, card);
                isFound = true;
                break;
            }

            if(isFound)
                break;
        }

        this.updateTurnListeners();

        // Character is not in an exit
        if (!(((RoomPlace) this.getCurPlace()).getRoom().equals(Room.EXIT))) {
            return;
        }

        // Character has won the game
        if(!isFound) {
            onWin();
            game.setPlaying(false);
            return;
        }

        // Character has lost the game
        onLose();
        for(Character c : game.getCharacters()) {
            for(Card card : getCards())
                guessHandler
                        .get(InsertHandler.class, GuessHandle.class)
                        .insert(RevealContext.LOST_GAME, c, card);
        }
        game.getCharacters().remove(this);
        this.moveTo(null, true);
        this.updateTurnListeners();
    }

    /**
     * Add an observer updated when a player moves
     */
    public void addTurnListener(OnTurnListener<Character> turnEndListener) {
        this.turnListeners.add(turnEndListener);
    }

    /**
     * Update move listeners
     */
    protected void updateTurnListeners() {
        for(OnTurnListener<Character> turnEndListener : this.turnListeners) {
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
        guessHandler
                .get(InsertHandler.class, RevealHandle.class)
                .insert(RevealContext.PROVIDED, card);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Character))
            return false;
        Character other = (Character) obj;

        return this.getCharacter().getName().equals(other.getCharacter().getName());
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
