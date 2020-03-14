package mines.zinno.clue.shape.place;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.layout.board.constant.DirectionKey;
import mines.zinno.clue.layout.board.util.Location;

/**
 * The {@link DoorPlace} is a subclass of the {@link RoomPlace} class. This class corresponds to the doors on the
 * {@link mines.zinno.clue.layout.board.ClueBoard}. It implements the {@link Teleportable} interface to send characters
 * to the middle of a room after entering.
 */
public class DoorPlace extends RoomPlace implements Teleportable {

    public DoorPlace(DirectionKey direction, Room room) {
        this(direction, room, true, 1);
    }

    public DoorPlace(DirectionKey direction, Room room, boolean isReachable, int moveCost) {
        super(direction, room, isReachable, moveCost);
    }
    
    @Override
    public void display() {
        this.setFill(new ImagePattern(new Image(ImgURL.DOOR_PLACE.getUrl().toExternalForm())));
        this.setOpacity(1);
    }

    /**
     * Teleport to the middle of the room
     * 
     * @return {@link Room#getCenter()}
     */
    @Override
    public Location teleportTo() {
        return super.getRoom().getCenter();
    }
}
