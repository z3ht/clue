package mines.zinno.clue.layouts.board.place;

import mines.zinno.clue.layouts.board.resources.DirectionKey;

public class StartPlace extends Place {

    public StartPlace() {
        this(true, 1);
    }

    public StartPlace(boolean isReachable, int moveCost) {
        super(DirectionKey.ALL, isReachable, moveCost);
    }

}
