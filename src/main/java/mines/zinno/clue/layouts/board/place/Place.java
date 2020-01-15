package mines.zinno.clue.layouts.board.place;

import javafx.scene.shape.Rectangle;
import mines.zinno.clue.layouts.board.resources.DirectionKey;

public class Place extends Rectangle {

    private final DirectionKey direction;
    private final boolean isReachable;
    private final int moveCost;

    private boolean isOccupied;

    public Place() {
        this(DirectionKey.ALL,true, 1);
    }

    public Place(int moveCost) {
        this(DirectionKey.ALL,true, moveCost);
    }

    public Place(boolean isReachable) {
        this(DirectionKey.ALL, isReachable, 1);
    }

    public Place(DirectionKey direction, boolean isReachable, int moveCost) {
        this.direction = direction;
        this.isReachable = isReachable;
        this.moveCost = moveCost;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public DirectionKey getDirection() {
        return direction;
    }
}
