package mines.zinno.clue.enums;

import mines.zinno.clue.layouts.board.resources.Location;

public enum Room {

    HALL("Hall", 1, "h", new Location(11, 3)),
    LOUNGE("Lounge", 2, "l", new Location(20, 2)),
    DINING_ROOM("Dining Room", 3, "d", new Location(20, 11)),
    KITCHEN("Kitchen", 4, "k", new Location(20, 20)),
    BALL_ROOM("Ball Room", 5, "a", new Location(11, 20)),
    CONSERVATORY("Conservatory", 6, "c", new Location(2, 20)),
    BILLIARD_ROOM("Billiard Room", 7, "b", new Location(2, 14)),
    LIBRARY("Library", 8, "i", new Location(3, 8)),
    STUDY("Study", 9, "s", new Location(3, 1)),
    EXIT("Exit", 10, "e", new Location(11, 11))
    ;

    private int id;
    private String name;
    private String key;
    private Location center;

    Room(String name, int id, String key, Location center) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.center = center;
    }

    public String getKey() {
        return key;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
