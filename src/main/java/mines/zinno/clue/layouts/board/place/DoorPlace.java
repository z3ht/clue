package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.resources.DirectionKey;

public class DoorPlace extends RoomPlace {

    public DoorPlace(DirectionKey direction, Room room) {
        this(direction, room, true, 1);
    }

    public DoorPlace( DirectionKey directionKey, Room room, boolean isReachable, int moveCost) {
        super(directionKey, room, isReachable, moveCost);
    }
}
