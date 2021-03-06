package mines.zinno.clue.shape.place;

import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.layout.board.constant.DirectionKey;
import mines.zinno.clue.util.Location;
import mines.zinno.clue.util.tree.Costable;

/**
 * The {@link Place} class is the most generic cell type used by the {@link mines.zinno.clue.layout.board.ClueBoard}.
 * The {@link Place} class is a superclass to the {@link BasicPlace} and {@link RoomPlace} classes.
 * 
 * Satisfies {@link mines.zinno.clue.Assignments#C12A2} requirements
 */
public class Place extends Rectangle implements Costable {
    
    private Place[] adjacent;

    private final DirectionKey direction;
    private final boolean isReachable;
    private final int moveCost;

    private boolean isOccupied;
    boolean isHighlighted;
    
    private final Rectangle highlight;

    public Place() {
        this(DirectionKey.ALL, true, 1);
    }

    public Place(DirectionKey direction, boolean isReachable, int moveCost) {
        super();
        
        this.isReachable = isReachable;
        this.moveCost = moveCost;
        this.direction = direction;
        
        this.highlight = new Rectangle();
        
        this.setOpacity(0);
    }

    public void display() {
        this.setFill(new ImagePattern(new Image(ImgURL.PLACE.getUrl().toExternalForm())));
        this.setOpacity(1);
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
        if(!(this.getParent().getChildrenUnmodifiable().contains(highlight)))
            createHighlight();
        this.highlight.opacityProperty().set(opacity);
        this.highlight.setFill(fill);

        this.isHighlighted = true;
    }

    private void createHighlight() {
        ((Pane) this.getParent()).getChildren().add(highlight);
        this.highlight.setWidth(this.getWidth());
        this.highlight.setHeight(this.getHeight());
        this.highlight.setX(this.getX());
        this.highlight.setY(this.getY());
        this.highlight.setVisible(true);
        this.highlight.addEventFilter(Event.ANY, (event) -> {
            this.fireEvent(event);
            event.consume();
        });
    }

    /**
     * Remove a highlight
     */
    public void delHighlight() {
        this.isHighlighted = false;
        this.highlight.opacityProperty().set(0);
    }
    
    /**
     * Set place occupied
     * 
     * @param occupied isOccupied
     */
    public synchronized void setOccupied(boolean occupied) {
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

    @Override
    public int getCost() {
        return this.getMoveCost();
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }
}
