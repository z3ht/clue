package mines.zinno.clue.constant;

import mines.zinno.clue.control.menu.ValueMenuItem;
import mines.zinno.clue.layout.board.util.Location;

/**
 * The {@link Room} enum holds room information
 */
public enum Room implements Card {

    HALL("hall", 1, 'h', new Location(11, 3)),
    LOUNGE("lounge", 2, 'l', new Location(20, 2)),
    DINING_ROOM("dining room", 3, 'd', new Location(20, 11)),
    KITCHEN("kitchen", 4, 'k', new Location(20, 20)),
    BALL_ROOM("ball room", 5, 'a', new Location(11, 20)),
    CONSERVATORY("conservatory", 6, 'c', new Location(2, 20)),
    BILLIARD_ROOM("billiard room", 7, 'b', new Location(2, 14)),
    LIBRARY("library", 8, 'i', new Location(3, 8)),
    STUDY("study", 9, 's', new Location(3, 1)),
    EXIT("exit", 10, 'e', new Location(11, 11))
    ;

    private int id;
    private String name;
    private char key;
    private Location center;

    Room(String name, int id, char key, Location center) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.center = center;
    }

    /**
     * @return Corresponding Room {@link Character} key
     */
    public char getKey() {
        return key;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Get the center Location of {@link Room}
     */
    public Location getCenter() {
        return center;
    }

    /**
     * @return Corresponding {@link ValueMenuItem}<{@link Room}>
     */
    public ValueMenuItem<Room> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }

    /**
     * @return {@link Room#getName()}
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Get the room whose key matches the provided key or null if none exist
     */
    public static Room getRoom(char key) {
        for(Room room : Room.values()) {
            if(room.getKey() == key || (char) (key + 32) == room.getKey())
                return room;
        }
        return null;
    }

}
