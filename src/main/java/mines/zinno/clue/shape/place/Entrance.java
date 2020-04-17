package mines.zinno.clue.shape.place;

import mines.zinno.clue.constant.Room;
import mines.zinno.clue.layout.board.constant.DirectionKey;

public class Entrance extends RoomPlace {
    public Entrance(Room room) {
        super(room);
    }

    public Entrance(Room room, boolean isReachable, int moveCost) {
        super(room, isReachable, moveCost);
    }

    public Entrance(DirectionKey directionKey, Room room, boolean isReachable, int moveCost) {
        super(directionKey, room, isReachable, moveCost);
    }
}
