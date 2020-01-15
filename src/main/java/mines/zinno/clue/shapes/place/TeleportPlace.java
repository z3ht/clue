package mines.zinno.clue.shapes.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;

public class TeleportPlace extends RoomPlace implements Teleportable {

    public TeleportPlace(DirectionKey direction, Room room) {
        this(direction, room, true, 1);
    }

    public TeleportPlace(DirectionKey direction, Room room, boolean isReachable, int moveCost) {
        super(direction, room, isReachable, moveCost);
    }

    @Override
    public Location teleportTo() {
        return super.getRoom().getCenter();
    }
}
