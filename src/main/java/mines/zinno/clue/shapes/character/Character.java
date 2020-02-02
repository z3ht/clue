package mines.zinno.clue.shapes.character;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Circle;
import mines.zinno.clue.Game;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.layouts.board.Board;
import mines.zinno.clue.layouts.board.utils.Location;
import mines.zinno.clue.shapes.character.enums.Turn;
import mines.zinno.clue.shapes.character.exceptions.BadTurnOrder;
import mines.zinno.clue.shapes.character.exceptions.ImpossibleMove;
import mines.zinno.clue.shapes.place.Place;
import mines.zinno.clue.vo.GuessVO;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Circle {
    
    private static final int NUM_DICE = 1;
    
    protected final Game game;
    protected final Suspect suspect;
    
    // Items the character is provided with at the start
    private final List<Room> providedRooms;
    private final List<Weapon> providedWeapons;
    private final List<Suspect> providedSuspects;

    protected Turn turn;
    protected int rollNum;
    protected Place curPlace;
    protected List<Place> posMoves = new ArrayList<>();
    
    // Items the character knows from guessing/being provided with
    protected ObservableList<Room> knownRooms = FXCollections.observableArrayList();
    protected ObservableList<Weapon> knownWeapons = FXCollections.observableArrayList();
    protected ObservableList<Suspect> knownSuspects = FXCollections.observableArrayList();
    
    public Character(Game game, Suspect suspect, Place startPlace, List<Room> providedRooms, List<Weapon> providedWeapons, List<Suspect> providedSuspects) {
        this.game = game;
        this.suspect = suspect;

        game.getController().getBoard().getChildren().add(this);
        
        moveTo(startPlace);
        
        this.providedRooms = providedRooms;
        this.providedWeapons = providedWeapons;
        this.providedSuspects = providedSuspects;
        
        this.knownRooms.addAll(providedRooms);
        this.knownWeapons.addAll(providedWeapons);
        this.knownSuspects.addAll(providedSuspects);
        
        displaySuspect();
    }
    
    public void beginTurn() {
        this.turn = Turn.PRE_ROLL;
        this.rollNum = -1;
    }

    public int roll() {
        if(turn != Turn.PRE_ROLL)
            throw new BadTurnOrder(Turn.PRE_ROLL.getBadTurnMessage());
        int rollNum = 0;
        for(int i = 0; i < NUM_DICE; i++)
            rollNum += Math.random() * 6 + 1;
        this.rollNum = rollNum;
        this.turn = Turn.POST_ROLL;
        return rollNum;
    }
    
    public List<Place> calcPosMoves() {
        return this.calcPosMoves(this.getCurPlace(), this.rollNum);
    }
    
    public List<Place> calcPosMoves(Place loc, int distance) {
        if(turn != Turn.POST_ROLL)
            throw new BadTurnOrder(Turn.POST_ROLL.getBadTurnMessage());
        if(distance == 0)
            return new ArrayList<>();
        List<Place> moves = new ArrayList<>();
        for(Place place : loc.getAdjacent()) {
            if(place.isOccupied())
                continue;
            moves.addAll(calcPosMoves(place, --distance));
            moves.add(place);
        }
        return moves;
    }
    
   public void moveTo(Place place) {
        this.moveTo(place, false);
   } 
    
    public void moveTo(Place place, boolean forceMove) {
        if(!forceMove && curPlace != null && curPlace.getDistance(place) > this.rollNum)
                throw new ImpossibleMove();
        
        if(curPlace != null)
            curPlace.setOccupied(false);
        this.curPlace = place;
        Location location = curPlace.getCenter();
        this.setCenterX(location.getX());
        this.setCenterY(location.getY());
        place.setOccupied(true);
    }
    
    public void guess(GuessVO guess) {
        
    }
    
    public Suspect getSuspect() {
        return suspect;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Turn getTurn() {
        return turn;
    }

    public int getRollNum() {
        return rollNum;
    }

    public Place getCurPlace() {
        return curPlace;
    }

    public List<Place> getPosMoves() {
        if(posMoves == null)
            calcPosMoves();
        return posMoves;
    }

    public ObservableList<Room> getKnownRooms() {
        return knownRooms;
    }

    public ObservableList<Weapon> getKnownWeapons() {
        return knownWeapons;
    }

    public ObservableList<Suspect> getKnownSuspects() {
        return knownSuspects;
    }

    public List<Room> getProvidedRooms() {
        return providedRooms;
    }

    public List<Weapon> getProvidedWeapons() {
        return providedWeapons;
    }

    public List<Suspect> getProvidedSuspects() {
        return providedSuspects;
    }

    private void displaySuspect() {
        Board board = this.game.getController().getBoard();
        this.setRadius(Math.min(board.getGrid()[0][0].getWidth(), board.getGrid()[0][0].getHeight())/1.66);
        this.setFill(suspect.getColor());
        this.setVisible(true);
    }
}
