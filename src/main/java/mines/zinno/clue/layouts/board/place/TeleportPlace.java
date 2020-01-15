package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.layouts.board.resources.DirectionKey;
import mines.zinno.clue.layouts.board.resources.Location;

public class TeleportPlace extends Place {

    private final Location teleportPlaceCoordinate;

    public TeleportPlace(Location teleportPlaceCoordinate) {
        this(teleportPlaceCoordinate,true, 1);
    }

    public TeleportPlace(Location teleportPlaceCoordinate, boolean isReachable, int moveCost) {
        super(DirectionKey.ALL, isReachable, moveCost);

        this.teleportPlaceCoordinate = teleportPlaceCoordinate;
    }

    public Location getTeleportPlaceCoordinate() {
        return teleportPlaceCoordinate;
    }
}
