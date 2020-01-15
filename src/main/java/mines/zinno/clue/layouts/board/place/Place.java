package mines.zinno.clue.layouts.board.place;

import javafx.scene.shape.Rectangle;

public class Place extends Rectangle {

    private Place[] adjacent;
    private final boolean isReachable;
    private final int moveCost;

    private boolean isOccupied;

    public Place() {
        this(true, 1);
    }

    public Place(boolean isReachable, int moveCost) {
        this.isReachable = isReachable;
        this.moveCost = moveCost;
    }

    public void addHighlight(String syleClassName) {
        this.getStyleClass().add(syleClassName);
    }

    public void delHighlight(String styleClassName) {
        this.getStyleClass().remove(styleClassName);
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

    public void setAdjacent(Place[] adjacent) {
        this.adjacent = adjacent;
    }

    public Place[] getAdjacent() {
        return adjacent;
    }
}
