package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.enums.Room;

import java.util.function.Consumer;

public class RoomPlace extends Place {

    private final Room room;

    public RoomPlace(Room room) {
        this(room ,true, 0);
    }

    public RoomPlace(Room room, boolean isReachable, int moveCost) {
        super(isReachable, moveCost);

        this.room = room;
    }

    @Override
    public void addHighlight(String styleClassName) {
        this.getStyleClass().add(styleClassName);
        applyToFamily(roomPlace -> roomPlace.addHighlight(styleClassName));
    }

    @Override
    public void delHighlight(String styleClassName) {
        this.getStyleClass().remove(styleClassName);
        applyToFamily(roomPlace -> roomPlace.delHighlight(styleClassName));

    }

    private void applyToFamily(Consumer<RoomPlace> consumer) {
        for(Place place : super.getAdjacent()) {
            if(!(place instanceof RoomPlace))
                continue;
            RoomPlace roomPlace = (RoomPlace) place;
            if(!(roomPlace.getRoom().equals(this.getRoom())))
                continue;
            consumer.accept(roomPlace);
        }
    }

    public Room getRoom() {
        return room;
    }

}
