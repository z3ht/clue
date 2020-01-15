package mines.zinno.clue.shapes.place;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.layouts.board.enums.DirectionKey;

public class RoomPlace extends Place {

    private final Room room;

    public RoomPlace(DirectionKey direction, Room room) {
        this(direction, room ,true, 0);
    }

    public RoomPlace(DirectionKey directionKey, Room room, boolean isReachable, int moveCost) {
        super(directionKey, isReachable, moveCost);

        this.room = room;
    }

    @Override
    public void addHighlight(Paint fill) {
        this.addHighlight(fill, 0.4);
    }

    @Override
    public void addHighlight(Paint fill, double opacity) {
        this.highlightFamily(fill, opacity, 10);
    }

    @Override
    public void delHighlight() {
        this.highlightFamily(Color.TRANSPARENT, 0, 10);
    }

    public void highlightFamily(Paint fill, double opacity, int spread) {
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

    public Room getRoom() {
        return room;
    }

}
