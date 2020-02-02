package mines.zinno.clue.shapes.character;

import mines.zinno.clue.Game;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.shapes.character.enums.Turn;
import mines.zinno.clue.shapes.place.DoorPlace;
import mines.zinno.clue.vo.GuessVO;
import mines.zinno.clue.shapes.place.Place;
import mines.zinno.clue.shapes.place.RoomPlace;

import java.util.List;

public class Computer extends Character {

    public Computer(Game game, Suspect suspect, Place startPlace, List<Room> providedRooms, List<Weapon> providedWeapons, List<Suspect> providedSuspects) {
        super(game, suspect, startPlace, providedRooms, providedWeapons, providedSuspects);
    }

    @Override
    public void beginTurn() {
        super.beginTurn();
        this.roll();
        this.calcPosMoves();
        Place place = calcBestMove();
        this.moveTo(place);
        if(place instanceof RoomPlace) {
            this.guess(calcBestGuess());
        }
        this.setTurn(Turn.POST_MOVE);
    }
    
    public Place calcBestMove() {
        int minDistance = Integer.MAX_VALUE;
        DoorPlace closestGoodDoor = null;
        for (Place[] places : this.game.getController().getBoard().getGrid()) {
            for(Place place : places) {
                if(!(place instanceof DoorPlace))
                    continue;
                DoorPlace door = (DoorPlace) place;
                if(getKnownRooms().contains(door.getRoom()))
                    continue;
                int curDistance = this.getCurPlace().getDistance(door);
                if(curDistance >= minDistance)
                    continue;

                minDistance = curDistance;
                closestGoodDoor = door;
            }
        }
        
        Place bestMove = null;
        minDistance = Integer.MAX_VALUE;
        for(Place place : this.getPosMoves()) {
            int curDistance = closestGoodDoor.getDistance(place);
            if(curDistance > minDistance)
                continue;
            
            minDistance = curDistance;
            bestMove = place;
        }
        
        return bestMove;
    }
    
    public GuessVO calcBestGuess() {
        return new GuessVO(
//                calcGuessItem()
        );
    }
    
    private <T> T calcGuessItem(T[] items, List<T> known, T defaultVal) {
        T guessT = null;
        for(T t : items) {
            if (known.contains(t))
                continue;
            guessT = t;
        }
        if(guessT == null)
            guessT = defaultVal;
        return guessT;
    }
    
}
