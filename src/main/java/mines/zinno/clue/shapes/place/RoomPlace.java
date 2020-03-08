package mines.zinno.clue.shapes.place;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.enums.DirectionKey;

/**
 * The {@link RoomPlace} is a subclass of the {@link Place} class. It is used by all cells within a room in the
 * {@link mines.zinno.clue.layouts.board.ClueBoard}. The {@link RoomPlace} is a superclass of the {@link DoorPlace} and
 * {@link TeleportPlace} classes.
 */
public class RoomPlace extends Place {

    private final Room room;

    public RoomPlace(Room room) {
        this(room ,true, 0);
    }
    
    public RoomPlace(Room room, boolean isReachable, int moveCost) {
        this(DirectionKey.ALL, room, isReachable, moveCost);
    }

    public RoomPlace(DirectionKey directionKey, Room room, boolean isReachable, int moveCost) {
        super(directionKey, isReachable, moveCost);

        this.room = room;
    }

    /**
     * Highlight room this place is within
     * 
     * @param fill Fill color
     */
    @Override
    public void addHighlight(Paint fill) {
        this.addHighlight(fill, 0.4);
    }

    /**
     * Highlight room this place is within
     * 
     * @param fill Fill color
     * @param opacity Opacity value [0,1]
     */
    @Override
    public void addHighlight(Paint fill, double opacity) {
        this.highlightFamily(fill, opacity, 10);
    }

    /**
     * Remove room highlight
     */
    @Override
    public void delHighlight() {
        this.highlightFamily(Color.TRANSPARENT, 0, 10);
    }

    private void highlightFamily(Paint fill, double opacity, int spread) {
        if(spread <= 0)
            return;
        
        super.addHighlight(fill, opacity);

        if(this.getAdjacent() == null)
            return;
        
        for(Place place : this.getAdjacent()) {
            if(!(place instanceof RoomPlace))
                continue;
            RoomPlace roomPlace = (RoomPlace) place;
            if(!(roomPlace.getRoom().equals(this.getRoom())))
                continue;
            roomPlace.highlightFamily(fill, opacity, spread-1);
        }
    }

    /**
     * Get the room this place is inside
     * 
     * @return {@link Room}
     */
    public Room getRoom() {
        return room;
    }

}
