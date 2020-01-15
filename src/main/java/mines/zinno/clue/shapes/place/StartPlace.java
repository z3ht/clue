package mines.zinno.clue.shapes.place;

import mines.zinno.clue.layouts.board.enums.DirectionKey;

public class StartPlace extends Place {

    public StartPlace() {
        this(true, 1);
    }

    public StartPlace(boolean isReachable, int moveCost) {
        super(DirectionKey.ALL, isReachable, moveCost);
    }

}
