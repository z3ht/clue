package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.resources.DirectionKey;

public class RoomPlace extends Place {

    private final Room room;

    public RoomPlace(Room room) {
        this(room ,true, 1);
    }

    public RoomPlace(Room room, int moveCost) {
        this(room,true, moveCost);
    }

    public RoomPlace(Room room, boolean isReachable) {
        this(room, isReachable, 1);
    }

    public RoomPlace(Room room, boolean isReachable, int moveCost) {
        super(DirectionKey.ALL, isReachable, moveCost);

        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public DirectionKey getDirection() {
        return
    }

}
