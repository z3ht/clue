package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.resources.Location;

public class DoorPlace extends RoomPlace implements Teleportable {

    public DoorPlace(Room room) {
        this(room, true, 1);
    }

    public DoorPlace(Room room, boolean isReachable, int moveCost) {
        super(room, isReachable, moveCost);
    }

    @Override
    public Location teleportTo() {
        return super.getRoom().getCenter();
    }
}
