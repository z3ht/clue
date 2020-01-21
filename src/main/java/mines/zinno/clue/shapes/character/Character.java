package mines.zinno.clue.shapes.character;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Circle;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.layouts.board.utils.Location;
import mines.zinno.clue.shapes.character.enums.Turn;
import mines.zinno.clue.shapes.character.exceptions.BadTurnOrder;
import mines.zinno.clue.shapes.character.exceptions.ImpossibleMove;
import mines.zinno.clue.shapes.place.Place;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Circle {
    
    private static final int NUM_DICE = 1;
    
    private final Suspect suspect;
    
    private final List<Room> providedRooms;
    private final List<Weapon> providedWeapons;
    private final List<Suspect> providedSuspects;

    protected Turn turn;
    protected int roll;
    protected Place curPlace;
    protected List<Place> posMoves = new ArrayList<>();
    
    protected ObservableList<Room> knownRooms = FXCollections.observableArrayList();
    protected ObservableList<Weapon> knownWeapons = FXCollections.observableArrayList();
    protected ObservableList<Suspect> knownSuspects = FXCollections.observableArrayList();
    
    public Character(Suspect suspect, Place startPlace, List<Room> providedRooms, List<Weapon> providedWeapons, List<Suspect> providedSuspects) {
        this.suspect = suspect;
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
        this.roll = -1;
    }

    public int roll() {
        if(turn != Turn.PRE_ROLL)
            throw new BadTurnOrder(Turn.PRE_ROLL.getBadTurnMessage());
        int rollNum = 0;
        for(int i = 0; i < NUM_DICE; i++)
            rollNum += Math.random() * 6 + 1;
        this.roll = rollNum;
        this.turn = Turn.POST_ROLL;
        return rollNum;
    }
    
    public List<Place> calcPosMoves() {
        return this.calcPosMoves(this.roll);
    }
    
    public List<Place> calcPosMoves(int distance) {
        if(turn != Turn.POST_ROLL)
            throw new BadTurnOrder(Turn.POST_ROLL.getBadTurnMessage());
        // TODO calc pos moves
        return null;
    }
    
   public void moveTo(Place place) {
        this.moveTo(place, false);
   } 
    
    public void moveTo(Place place, boolean forceMove) {
        if(!forceMove && curPlace != null && curPlace.getDistance(place) > this.roll)
                throw new ImpossibleMove();
        
        if(curPlace != null)
            curPlace.setOccupied(false);
        this.curPlace = place;
        Location location = curPlace.getCenter();
        this.setCenterX(location.getX());
        this.setCenterY(location.getY());
        place.setOccupied(true);
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

    public int getRoll() {
        return roll;
    }

    public Place getCurPlace() {
        return curPlace;
    }

    public List<Place> getPosMoves() {
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

    }
}
