package mines.zinno.clue.layouts.board.resources;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.place.Place;
import mines.zinno.clue.layouts.board.place.RoomPlace;
import mines.zinno.clue.layouts.board.place.StartPlace;
import mines.zinno.clue.layouts.board.place.TeleportPlace;

public enum PlaceKey {

    STUDY("s", new RoomPlace(Room.STUDY, 0)),
    HALL("h", new RoomPlace(Room.HALL, 0)),
    LOUNGE("l", new RoomPlace(Room.LOUNGE, 0)),
    LIBRARY("i", new RoomPlace(Room.LIBRARY, 0)),
    EXIT("e", new RoomPlace(Room.EXIT, 0)),
    DINING_ROOM("d", new RoomPlace(Room.DINING_ROOM, 0)),
    BILLIARD_ROOM("b", new RoomPlace(Room.BILLIARD_ROOM, 0)),
    CONSERVATORY("c", new RoomPlace(Room.CONSERVATORY, 0)),
    BALL_ROOM("a", new RoomPlace(Room.BALL_ROOM, 0)),
    KITCHEN("k", new RoomPlace(Room.KITCHEN, 0)),

    KITCHEN_TELEPORTER("K", new TeleportPlace(new Location(20, 20), 1)),
    CONSERVATORY_TELEPORTER("C", new TeleportPlace(new Location(3, 20), 1)),
    LOUNGE_TELEPORTER("L", new TeleportPlace(new Location(20, 3), 1)),
    STUDY_TELEPORTER("S", new TeleportPlace(new Location(3, 2), 1)),

    START("*", new StartPlace()),
    PATH("x", new Place()),
    UNREACHABLE(".", new Place(false))
    ;

    private String key;
    private Place value;

    PlaceKey(String key, Place value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Place getValue() {
        return value;
    }
}
