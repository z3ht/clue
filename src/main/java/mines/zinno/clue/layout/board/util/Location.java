package mines.zinno.clue.layout.board.util;

/**
 * The {@link Location} utility class makes storing x and y coordinates simple and uniform
 */
public class Location {

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x and y values in that order
     * 
     * @return [x, y]
     */
    public int[] getLocation() {
        return new int[] {x, y};
    }

    /**
     * Get the x value
     * 
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y value
     * 
     * @return y value
     */
    public int getY() {
        return y;
    }
}
