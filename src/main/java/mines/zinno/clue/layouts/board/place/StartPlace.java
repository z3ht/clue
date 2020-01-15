package mines.zinno.clue.layouts.board.place;

public class StartPlace extends Place {

    public StartPlace() {
        this(true, 1);
    }

    public StartPlace(int moveCost) {
        this(true, moveCost);
    }

    public StartPlace(boolean isReachable) {
        this(isReachable, 1);
    }

    public StartPlace(boolean isReachable, int moveCost) {
        super(isReachable, moveCost);
    }

}
