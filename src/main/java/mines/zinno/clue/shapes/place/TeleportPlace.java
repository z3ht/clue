package mines.zinno.clue.shapes.place;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;

/**
 * The {@link TeleportPlace} is a subclass of the {@link RoomPlace} class. This class corresponds to the secret passages
 * on the {@link mines.zinno.clue.layouts.board.ClueBoard}. It implements the {@link Teleportable} interface to send
 * characters to the {@link Room} a secret passage leads to.
 */
public class TeleportPlace extends RoomPlace implements Teleportable {

    public TeleportPlace(DirectionKey direction, Room room) {
        this(direction, room, true, 1);
    }

    public TeleportPlace(DirectionKey direction, Room room, boolean isReachable, int moveCost) {
        super(direction, room, isReachable, moveCost);
    }

    /**
     * Teleport to the {@link Room} at the end of a secret passage
     */
    @Override
    public Location teleportTo() {
        return super.getRoom().getCenter();
    }
}
