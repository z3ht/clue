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
import mines.zinno.clue.util.handler.basic.BasicHandle;
import mines.zinno.clue.util.handler.basic.InsertHandler;
import mines.zinno.clue.util.tree.Node;
import mines.zinno.clue.util.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@link Character} class extends JavaFX's {@link Circle} class. It holds information and logic pertaining to
 * a character's position and status.
 *
 * Satisfies {@link mines.zinno.clue.Assignments#C23A} and {@link mines.zinno.clue.Assignments#C24A} requirements (also
 * check subclasses)
 */
public abstract class Character extends Circle {
    
    private static final int NUM_DICE = 1;
    
    protected final Clue game;
    protected final Suspect character;
    
    protected List<Card> providedCards = new ArrayList<>();
    protected List<Card> cards = new ArrayList<>();

    protected Turn turn;
    protected int rollNum;
    protected Place curPlace;
    protected Room prevRoom;

    protected Tree<Place> moveTree;

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

        this.prevRoom = null;
        if(this.curPlace instanceof RoomPlace)
            this.prevRoom = ((RoomPlace) this.curPlace).getRoom();

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

        this.updateMoveTree();

        this.turn = Turn.POST_ROLL;
        updateTurnListeners();

        // Allow player to skip moving if no moves are available
        Place[] posMoves = this.moveTree.retrieveAllValues().stream()
                .filter((p) -> p != null && !p.isOccupied())
                .toArray(Place[]::new);
        if(posMoves.length == 0)
            this.turn = Turn.POST_GUESS;
        updateTurnListeners();

        return rollNum;
    }

    /**
     * Calculate possible moves from {@link Character#getCurPlace()} and {@link Character#getRollNum()}
     */
    public void updateMoveTree() {
        this.updateMoveTree(this.getCurPlace(), this.getRollNum());
    }

    /**
     * Calculate possible moves from {@link Character#getCurPlace()} and provided number
     */
    @SuppressWarnings("unchecked")
    public void updateMoveTree(Place place, int number) {

        Tree<Place> tree = new Tree<>(place);
        tree.populate(
                (curNode) ->
                        // This casts correctly. Java doesn't like Parameterized arrays
                        Arrays.stream(curNode.getValue().getAdjacent())
                                .filter((p) -> p != null && (!p.isOccupied() || p instanceof RoomPlace))
                                .filter((adj) -> (curNode.getCost() + adj.getMoveCost() <= number))
                                .map((adj) -> new Node<>(adj, curNode))
                                .toArray(Node[]::new),
                100
        );

        this.moveTree = tree;
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

            if(moveTree != null && !forceMove) {
                // Decrement roll number
                this.rollNum -= moveTree.findPath(place).getCost();
            }
        }
        
        // Move to the void
        if(place == null) {
            this.curPlace = null;
            this.setVisible(false);
            return;
        }

        if(place.isOccupied())
            place = randomizeLoc(place);
        
        // Move to new place
        this.curPlace = place;
        this.curPlace.setOccupied(true);

        Location location = curPlace.getCenter();
        Platform.runLater(() -> {
            this.setCenterX(location.getX());
            this.setCenterY(location.getY());
            this.toFront();
        });

        if(curPlace != null)
            updateMoveTree();

        if(!forceMove)
            updateTurnListeners();
    }

    @SuppressWarnings("unchecked")
    protected final Place randomizeLoc(Place loc) {
        Tree<Place> randRoomLoc = new Tree<>(loc);
        randRoomLoc.populate((curNode) ->
                    // This casts correctly. Java doesn't like Parameterized arrays
                    Arrays.stream(curNode.getValue().getAdjacent())
                            .filter((place) -> place != null && !place.isOccupied())
                            .filter((adj) -> (curNode.getCost() + adj.getMoveCost() <= 0))
                            .map((adj) -> new Node<>(adj, curNode))
                            .toArray(Node[]::new),
                25);
        Set<Place> posLocs = randRoomLoc.retrieveAllValues().stream()
                .filter((place) -> place != null && !place.isOccupied())
                .collect(Collectors.toSet());
        return (posLocs.size() == 0) ? loc : (Place) posLocs.toArray()[(int) (posLocs.size()*Math.random())];
    }

    /**
     * Make a guess
     */
    public void guess(GuessVO guessVO) {
        this.guess(guessVO.suspect, guessVO.room, guessVO.weapon);
    }
    
    /**
     * Make a guess
     */
    public void guess(Suspect suspect, Room room, Weapon weapon) {
        this.turn = Turn.POST_GUESS;

        boolean isAccusation = ((RoomPlace) this.getCurPlace()).getRoom().equals(Room.EXIT);

        guessHandler
                .get(InsertHandler.class, GuessHandle.class)
                .insert(Handler.ALL, this, suspect, isAccusation, room, weapon);

        boolean isFound = false;
        for(int i = 1; i < game.getCharacters().size(); i++) {
            Character character = game.getCharacters().get(i);

            for(Card card : character.getProvidedCards()) {
                if(!(card == suspect || card == room || card == weapon))
                    continue;

                if(character != this)
                    guessHandler
                        .get(InsertHandler.class, RevealHandle.class)
                        .insert(RevealContext.ON_GUESS, character, this, card);

                isFound = true;
                break;
            }

            if(isFound)
                break;
        }

        // Character is not in an exit
        if (!isAccusation) {
            if(!isFound)
                guessHandler
                        .get(InsertHandler.class, BasicHandle.class)
                        .insert("nothing", this);

            this.updateTurnListeners();
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
        game.getCharacters().remove(this);
        for(Character c : game.getCharacters()) {
            for(Card card : getProvidedCards())
                guessHandler
                        .get(InsertHandler.class, RevealHandle.class)
                        .insert(RevealContext.LOST_GAME, c, card);
        }
        this.moveTo(null, true);
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
     * Get the move tree
     */
    public Tree<Place> getMoveTree() {
        return this.moveTree;
    }

    /**
     * Get the character's provided cards
     */
    public List<Card> getProvidedCards() {
        return this.providedCards;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Provide character with a card
     */
    public void addProvidedCard(Card card) {
        this.providedCards.add(card);
        guessHandler
                .get(InsertHandler.class, RevealHandle.class)
                .insert(RevealContext.PROVIDED, this, card);
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
