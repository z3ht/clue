package mines.zinno.clue.shapes.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;

/**
 * The {@link DoorPlace} is a subclass of the {@link RoomPlace} class. This class corresponds to the doors on the
 * {@link mines.zinno.clue.layouts.board.ClueBoard}. It implements the {@link Teleportable} interface to send characters
 * to the middle of a room after entering.
 */
public class DoorPlace extends RoomPlace implements Teleportable {

    public DoorPlace(DirectionKey direction, Room room) {
        this(direction, room, true, 1);
    }

    public DoorPlace(DirectionKey direction, Room room, boolean isReachable, int moveCost) {
        super(direction, room, isReachable, moveCost);
    }

    /**
     * Teleport to the middle of the room
     * 
     * @return {@link Room#getCenter()}
     */
    @Override
    public Location teleportTo() {
        return super.getRoom().getCenter();
    }
}
