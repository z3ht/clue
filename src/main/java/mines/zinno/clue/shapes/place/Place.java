package mines.zinno.clue.shapes.place;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;

public class Place extends Rectangle {

    private Place[] adjacent;

    private final DirectionKey direction;
    private final boolean isReachable;
    private final int moveCost;

    private boolean isOccupied;

    public Place() {
        this(DirectionKey.ALL, true, 1);
    }

    public Place(DirectionKey direction, boolean isReachable, int moveCost) {
        this.isReachable = isReachable;
        this.moveCost = moveCost;
        this.direction = direction;
        
        this.delHighlight();
        
        this.setOnMouseClicked(event -> addHighlight(Color.RED));
    }

    public void addHighlight(Paint fill) {
        addHighlight(fill, 0.4);
    }
    
    public void addHighlight(Paint fill, double opacity) {
        this.opacityProperty().set(opacity);
        this.setFill(fill);
    }

    public void delHighlight() {
        this.opacityProperty().set(0);
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
    
    public Location getCenter() {
        return new Location((int) (this.getX() + (this.getWidth()/2)), (int) (this.getY() + (this.getHeight()/2)));
    }

    public DirectionKey getDirection() {
        return direction;
    }

    public void setAdjacent(Place[] adjacent) {
        this.adjacent = adjacent;
    }

    public Place[] getAdjacent() {
        return adjacent;
    }
}
