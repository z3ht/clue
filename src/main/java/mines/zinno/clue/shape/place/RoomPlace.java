package mines.zinno.clue.shape.place;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.layout.board.constant.DirectionKey;

/**
 * The {@link RoomPlace} is a subclass of the {@link Place} class. It is used by all cells within a room in the
 * {@link mines.zinno.clue.layout.board.ClueBoard}. The {@link RoomPlace} is a superclass of the {@link DoorPlace} and
 * {@link TeleportPlace} classes.
 */
public class RoomPlace extends Place {

    private final Room room;
    private boolean isCenter;

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
    
    @Override
    public void display() {
        this.setFill(new ImagePattern(new Image(ImgURL.ROOM_PLACE.getUrl().toExternalForm())));
        this.setOpacity(1);
    }

    /**
     * Returns true if this is the center of the room
     */
    public boolean isCenter() {
        return isCenter;
    }

    /**
     * Set the center of the room
     */
    public void setCenter(boolean center) {
        isCenter = center;
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
