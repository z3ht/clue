package mines.zinno.clue.shapes.character;

import mines.zinno.clue.Game;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.shapes.place.Place;

import java.util.List;

public class Player extends Character {

    public Player(Game game, Suspect suspect, Place startPlace, List<Room> providedRooms, List<Weapon> providedWeapons, List<Suspect> providedSuspects) {
        super(game, suspect, startPlace, providedRooms, providedWeapons, providedSuspects);
    }
    
}
