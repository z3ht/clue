package mines.zinno.clue.shape.character;

import mines.zinno.clue.constant.*;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.vo.GuessVO;
import mines.zinno.clue.shape.place.DoorPlace;
import mines.zinno.clue.shape.place.Entrance;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Computer extends Character {

    private Clue game;

    public Computer(Clue game, Suspect suspect, Place startPlace) {
        super(game, suspect, startPlace);
        this.game = game;
    }

    @Override
    public void beginTurn() {
        super.beginTurn();
        this.roll();
        
        this.moveTo(calcBestMove());
        if(this.curPlace instanceof RoomPlace) {
            GuessVO guess = calcBestGuess();
            
            if(guess == null)
                return;
            
            this.guess(guess);
            this.setTurn(Turn.POST_GUESS);
        }
    }

    private GuessVO calcBestGuess() {
        GuessVO bestGuess = new GuessVO();

        if(!(this.curPlace instanceof RoomPlace))
            return null;
        
        RoomPlace curRoomPlace = (RoomPlace) this.curPlace;
        
        if(curRoomPlace.getRoom() == Room.EXIT) {
            List<Room> rooms = new ArrayList<>(Arrays.asList(Room.values()));
            for(Card card : this.getCards()) {
                if(!(card instanceof Room))
                    continue;
                rooms.remove(card);
            }
            bestGuess.room = rooms.get(0);
        } else
            bestGuess.room = curRoomPlace.getRoom();

        List<Weapon> weapon = new ArrayList<>(Arrays.asList(Weapon.values()));
        for(Card card : this.getCards()) {
            if(!(card instanceof Weapon))
                continue;
            weapon.remove(card);
        }
        bestGuess.weapon = weapon.get(0);
        
        List<Suspect> suspect = new ArrayList<>(Arrays.asList(Suspect.values()));
        for(Card card : this.getCards()) {
            if(!(card instanceof Suspect))
                continue;
            suspect.remove(card);
        } 
        bestGuess.suspect = suspect.get(0);
        
        return bestGuess;
    }

    public Place calcBestMove() {
        // Determine the closest door that hasn't been ruled out yet
        double minDoorDistance = Double.MAX_VALUE;
        RoomPlace closestGoodEntrance = null;
        for (Place[] places : game.getController().getBoard().getGrid()) {
            for(Place place : places) {
                if(!(place instanceof RoomPlace && place instanceof Entrance))
                    continue;
                RoomPlace entrance = (RoomPlace) place;

                boolean shouldContinue = false;
                for(Card c : getCards()) {
                    if(!(c instanceof DoorPlace))
                        continue;
                    DoorPlace doorCard = (DoorPlace) c;
                    
                    if(doorCard.equals(entrance)) {
                        shouldContinue = true;
                        break;
                    }
                }
                if(shouldContinue)
                    continue;
                
                double curDistance = this.getManhattanDistance(this.curPlace, entrance);
                if(curDistance == -1 || curDistance > minDoorDistance)
                    continue;
                
                minDoorDistance = curDistance;
                closestGoodEntrance = entrance;
            }
        }
        
        // Do the move that puts the computer closest to the best door
        Place bestMove = null;
        double minDistance = Double.MAX_VALUE;
        for(Place place : this.getPosMoves()) {
            double curDistance = getManhattanDistance(place, closestGoodEntrance);
            if(curDistance == -1 || curDistance > minDistance)
                continue;

            minDistance = curDistance;
            bestMove = place;
        }

        return bestMove;
    }

    @Override
    public void onWin() {
        
    }

    @Override
    public void onLose() {

    }

    @Override
    public void receiveCard(Character sender, Card card, RevealContext revealContext) {

    }
    
    private double getManhattanDistance(Place p1, Place p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getX() - p2.getX());
    }
}
