package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.layouts.board.resources.Location;

public class TeleportPlace extends Place {

    private final Location teleportPlaceCoordinate;

    public TeleportPlace(Location teleportPlaceCoordinate) {
        this(teleportPlaceCoordinate,true, 1);
    }

    public TeleportPlace(Location teleportPlaceCoordinate, int moveCost) {
        this(teleportPlaceCoordinate,true, moveCost);
    }

    public TeleportPlace(Location teleportPlaceCoordinate, boolean isReachable) {
        this(teleportPlaceCoordinate ,isReachable, 1);
    }

    public TeleportPlace(Location teleportPlaceCoordinate, boolean isReachable, int moveCost) {
        super(isReachable, moveCost);

        this.teleportPlaceCoordinate = teleportPlaceCoordinate;
    }

    public Location getTeleportPlaceCoordinate() {
        return teleportPlaceCoordinate;
    }
}
