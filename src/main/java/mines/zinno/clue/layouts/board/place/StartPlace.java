package mines.zinno.clue.layouts.board.place;

public class StartPlace extends Place {

    public StartPlace() {
        this(true, 1);
    }

    public StartPlace(boolean isReachable, int moveCost) {
        super(isReachable, moveCost);
    }

}
