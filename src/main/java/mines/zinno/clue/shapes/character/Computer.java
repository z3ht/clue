package mines.zinno.clue.shapes.character;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.shapes.character.enums.Turn;
import mines.zinno.clue.shapes.character.vo.GuessVO;
import mines.zinno.clue.shapes.place.Place;
import mines.zinno.clue.shapes.place.RoomPlace;

import java.util.List;

public class Computer extends Character {

    public Computer(Suspect suspect, Place startPlace, List<Room> providedRooms, List<Weapon> providedWeapons, List<Suspect> providedSuspects) {
        super(suspect, startPlace, providedRooms, providedWeapons, providedSuspects);
    }

    @Override
    public void beginTurn() {
        super.beginTurn();
        this.roll();
        this.calcPosMoves();
        Place place = calcBestMove();
        this.moveTo(place);
        if(place instanceof RoomPlace) {
            calcBestGuess()
        }
        this.setTurn(Turn.POST_MOVE);
    }
    
    public Place calcBestMove() {
        //TODO calc best move
        return null;
    }
    
    public GuessVO calcBestGuess() {
        //TODO calc best guess;
        return null;
    }
}
