package mines.zinno.clue.shape.character.vo;

import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.Suspect;
import mines.zinno.clue.constant.Weapon;

public class GuessVO {
    
    public Suspect suspect;
    public Room room;
    public Weapon weapon;


    public GuessVO(Suspect suspect, Room room, Weapon weapon) {
        this.suspect = suspect;
        this.room = room;
        this.weapon = weapon;
    }

    public GuessVO() {}
}
