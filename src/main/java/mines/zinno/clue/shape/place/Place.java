package mines.zinno.clue.shape.place;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.layout.board.constant.DirectionKey;
import mines.zinno.clue.layout.board.util.Location;

import java.util.*;

/**
 * The {@link Place} class is the most generic cell type used by the {@link mines.zinno.clue.layout.board.ClueBoard}.
 * The {@link Place} class is a superclass to the {@link BasicPlace} and {@link RoomPlace} classes.
 */
public class Place extends Rectangle {

    private static final int MAX_SPREAD = 12;
    
    private Place[] adjacent;

    private final DirectionKey direction;
    private final boolean isReachable;
    private final int moveCost;

    private boolean isOccupied;
    
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
        this.highlight.opacityProperty().set(0);
    }

    /**
     * Get the Manhattan distance from another place to this one (max. {@value MAX_SPREAD})
     * 
     * @param place Start place
     * @return the Manhattan distance between the places (max. {@value MAX_SPREAD})
     */
    public int getDistance(Place place) {
        return this.getDistance(place, MAX_SPREAD);
    }

    /**
     * Breadth first recursive search that spans through adjacent places in search of this {@link Place}
     *
     * @param place Start place
     * @param maxSpread Maximum radius searched for this place
     * @return the Manhattan distance between the places (max {@value MAX_SPREAD})
     */
    private int getDistance(Place place, int maxSpread) {
        Queue<Pair<Place, Integer>> tree = new LinkedList<>();
        tree.add(new Pair<>(place, 0));
        return getDistance(tree, maxSpread);
    }
    
    
    private int getDistance(Queue<Pair<Place, Integer>> tree, int maxSpread) {
        Pair<Place, Integer> top = tree.peek();
        
        // Exit point
        if(top == null || top.getValue() == maxSpread)
            return -1;
        
        // Exit point
        if(top.getKey() == this)
            return top.getValue();
        
        // Remove when value is known to not be the target
        tree.remove();
        
        // Add adjacents not already in the tree
        for(Place adj : top.getKey().getAdjacent()) {
            if(adj == null)
                continue;
            
            boolean shouldContinue = false;
            for(Pair<Place, Integer> val : tree) {
                if(adj == val.getKey()) {
                    shouldContinue = true;
                    break;
                }
            }
            if(shouldContinue)
                continue;
            
            tree.add(new Pair<>(adj, top.getValue() + adj.getMoveCost()));
        }
        
        // Continue through tree
        return getDistance(tree, maxSpread);
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
