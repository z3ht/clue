package mines.zinno.clue.shape.character;

import javafx.scene.shape.Circle;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.layout.board.Board;
import mines.zinno.clue.layout.board.util.Location;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link Character} class extends JavaFX's {@link Circle} class. It holds information and logic pertaining to
 * a character's position and status.
 */
public abstract class Character extends Circle {
    
    private static final int NUM_DICE = 1;
    
    protected final BoardGame boardGame;
    protected final Suspect character;
    
    protected List<Card> providedCards = new ArrayList<>();

    protected Turn turn;
    protected int rollNum;
    protected Place curPlace;
    protected Set<Place> posMoves = new HashSet<>();
    
    public Character(BoardGame boardGame, Suspect character, Place startPlace) {
        this.boardGame = boardGame;
        this.character = character;

        boardGame.getController().getBoard().getChildren().add(this);
        
        moveTo(startPlace, true);
        
        display();
    }

    /**
     * Called by {@link mines.zinno.clue.runner.ClueRunner} when a character's turn is ready to begin
     */
    public void beginTurn() {
        this.turn = Turn.PRE_ROLL;
    }

    /**
     * Roll dice
     */
    public int roll() {
        int rollNum = 0;
        for(int i = 0; i < NUM_DICE; i++)
            rollNum += Math.random() * 6 + 1;
        this.rollNum = rollNum;
        this.turn = Turn.POST_ROLL;
        
        this.posMoves = calcPosMoves();
        
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
        if(distance <= 0)
            return new HashSet<>();
        Set<Place> moves = new HashSet<>();
        for(Place place : loc.getAdjacent()) {
            if(place == null || place.isOccupied())
                continue;
            moves.addAll(calcPosMoves(place, distance-place.getMoveCost()));
            moves.add(place);
        }
        return moves;
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
     * @param forceMove Force movement (t:y; f:n)
     */
    public void moveTo(Place place, boolean forceMove) {
        this.setVisible(true);
        
        // Set turn to post move
        this.turn = Turn.POST_MOVE;
        
        if(curPlace != null) {
            // Mark previous place as unoccupied
            curPlace.setOccupied(false);

            // Decrement roll number
            this.rollNum -= curPlace.getDistance(place);
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
        this.setCenterX(location.getX());
        this.setCenterY(location.getY());
        place.setOccupied(true);

        // Calc new possible moves
        this.posMoves = calcPosMoves();
    }

    /**
     * Make a guess
     * 
     * @return True when another player shows a card; False when no card is shown; null if the player wins or loses
     */
    public Boolean guess(Suspect suspect, Room room, Weapon weapon) {
        this.turn = Turn.POST_GUESS;
        
        boolean isFound = false;
        
        for(int i = 1; i < boardGame.getCharacters().size(); i++) {
            Character c = (Character) boardGame.getCharacters().get(i);
            
            for(Card card : c.getCards()) {
                if(!(card.getName().equals(suspect.getName()) ||
                        card.getName().equals(room.getName()) ||
                        card.getName().equals(weapon.getName())))
                    continue;
                
                this.receiveCard(c, card, RevealContext.ON_GUESS);
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
            boardGame.setPlaying(false);
            return null;
        }

        // Character has lost the game
        onLose();
        for(Object o : boardGame.getCharacters()) {
            Character c = (Character) o;
            for(Card card : getCards())
                c.receiveCard(this, card, RevealContext.LOST_GAME);
        }
        boardGame.getCharacters().remove(this);
        this.moveTo(null, true);
        
        return null;
    }

    /**
     * Receive a card
     */
    public abstract void receiveCard (Character sender, Card card, RevealContext revealContext);

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
    }

    /**
     * Display character
     */
    private void display() {
        Board board = this.boardGame.getController().getBoard();
        this.setRadius(Math.min(board.getGrid()[0][0].getWidth(), board.getGrid()[0][0].getHeight())/1.66);
        this.setFill(character.getColor());
    }
}
