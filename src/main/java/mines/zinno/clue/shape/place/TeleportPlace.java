package mines.zinno.clue.shape.place;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.layout.board.constant.DirectionKey;
import mines.zinno.clue.util.Location;

/**
 * The {@link TeleportPlace} is a subclass of the {@link RoomPlace} class. This class corresponds to the secret passages
 * on the {@link mines.zinno.clue.layout.board.ClueBoard}. It implements the {@link Teleportable} interface to send
 * characters to the {@link Room} a secret passage leads to.
 */
public class TeleportPlace extends RoomPlace implements Teleportable, Entrance {

    public TeleportPlace(DirectionKey direction, Room room) {
        this(direction, room, true, 1);
    }

    public TeleportPlace(DirectionKey direction, Room room, boolean isReachable, int moveCost) {
        super(direction, room, isReachable, moveCost);
    }

    @Override
    public void display() {
        this.setFill(new ImagePattern(new Image(ImgURL.TELEPORT_PLACE.getUrl().toExternalForm())));
        this.setOpacity(1);
    }

    /**
     * Teleport to the {@link Room} at the end of a secret passage
     */
    @Override
    public Location teleportTo() {
        return super.getRoom().getCenter();
    }
}
