package mines.zinno.clue.enums;

public enum  Rooms {

    HALL("Hall", 1),
    LOUNGE("Lounge", 2),
    DINING_ROOM("Dining Room", 3),
    KITCHEN("Kitchen", 4),
    BALL_ROOM("Ball Room", 5),
    CONSERVATORY("Conservatory", 6),
    BILLIARD_ROOM("Billiard Room", 7),
    LIBRARY("Library", 8),
    STUDY("Study", 9)
    ;

    private int id;
    private String name;

    Rooms(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
