package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.resources.DirectionKey;

public class RoomPlace extends Place {

    private final Room room;

    public RoomPlace(Room room) {
        this(DirectionKey.ALL, room ,true, 0);
    }

    public RoomPlace(DirectionKey direction, Room room, boolean isReachable, int moveCost) {
        super(direction, isReachable, moveCost);

        this.room = room;
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    public Room getRoom() {
        return room;
    }

}
