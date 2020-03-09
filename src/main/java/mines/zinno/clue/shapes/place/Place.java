package mines.zinno.clue.shapes.place;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;

/**
 * The {@link Place} class is the most generic cell type used by the {@link mines.zinno.clue.layouts.board.ClueBoard}.
 * The {@link Place} class is a superclass to the {@link BasicPlace} and {@link RoomPlace} classes.
 */
public class Place extends Rectangle {

    private static final int MAX_SPREAD = 15;
    
    private Place[] adjacent;

    private final DirectionKey direction;
    private final boolean isReachable;
    private final int moveCost;

    private boolean isOccupied;

    public Place() {
        this(DirectionKey.ALL, true, 1);
    }

    public Place(DirectionKey direction, boolean isReachable, int moveCost) {
        super();
        
        this.isReachable = isReachable;
        this.moveCost = moveCost;
        this.direction = direction;
        
        this.delHighlight();
        
        // TODO Remove
        this.setOnMouseClicked(event -> addHighlight(Color.RED));
        // END
    }

    /**
     * Highlight this place
     * 
     * @param fill Fill color
     */
    public void addHighlight(Paint fill) {
        addHighlight(fill, 0.4);
    }

    /**
     * Highlight this place with a specific opacity
     * 
     * @param fill Fill color
     * @param opacity Opacity value [0,1]
     */
    public void addHighlight(Paint fill, double opacity) {
        this.opacityProperty().set(opacity);
        this.setFill(fill);
    }

    /**
     * Get the Manhattan distance from this place to another (max. {@value MAX_SPREAD})
     * 
     * @param place Opposing place
     * @return the Manhattan distance between the places (max. {@value MAX_SPREAD})
     */
    public int getDistance(Place place) {
        return this.getDistance(place, MAX_SPREAD);
    }

    /**
     * Get the Manhattan distance from this place to another with custom maximum
     * 
     * @param place Opposing place
     * @param maxSpread Maximum radius searched for opposing place
     * @return the Manhattan distance between the places (max {@value MAX_SPREAD})
     */
    public int getDistance(Place place, int maxSpread) {
        //TODO code distance from pieces
        return -1;
    }

    /**
     * Remove a highlight
     */
    public void delHighlight() {
        this.opacityProperty().set(0);
    }

    /**
     * Set place occupied
     * 
     * @param occupied isOccupied
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Determine if a place is occupied
     * 
     * @return isOccupied
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Determine if a place is reachable
     * 
     * @return isReachable
     */
    public boolean isReachable() {
        return isReachable;
    }

    /**
     * Get the cost to move to this place
     */
    public int getMoveCost() {
        return moveCost;
    }

    /**
     * Get center pixel location of this place
     * 
     * @return Center of this place in pixel {@link Location}
     */
    public Location getCenter() {
        return new Location((int) (this.getX() + (this.getWidth()/2)), (int) (this.getY() + (this.getHeight()/2)));
    }

    /**
     * Get the {@link DirectionKey} for this location
     */
    public DirectionKey getDirection() {
        return direction;
    }

    /**
     * Set adjacent places to this location
     * 
     * @param adjacent adjacent places
     */
    public void setAdjacent(Place[] adjacent) {
        this.adjacent = adjacent;
    }

    /**
     * Get adjacent places to this place
     */
    public Place[] getAdjacent() {
        return adjacent;
    }
}
