package mines.zinno.clue.layout.board.constant;

/**
 * The {@link DirectionKey} converts direction characters into boolean arrays assigning valid adjacent locations
 * in the North East South West order
 */
public enum DirectionKey {

    HORIZONTAL_DOOR('-', new boolean[] {false, true, false, true}),
    VERTICAL_DOOR('|', new boolean[] {true, false, true, false}),
    ALL(' ', new boolean[] {true, true, true, true}),
    CENTER('*', new boolean[] {true, true, true, true});

    private Character key;
    private boolean[] openLocs;

    DirectionKey(Character key, boolean[] openLocs) {
        this.openLocs = openLocs;
        this.key = key;
    }

    /**
     * Get the direction's character key
     * 
     * @return character key
     */
    public Character getKey() {
        return key;
    }

    /**
     * Get the direction's possible adjacent locations. True values indicate reachable adjacent locations and false
     * indicates unreachable adjacent locations.
     * 
     * @return possible adjacent locations
     */
    public boolean[] getOpenLocs() {
        return openLocs;
    }

    /**
     * Get the reachability of an adjacent location. True values indicate reachable adjacent locations and false
     * indicates unreachable adjacent locations.
     * 
     * @param loc adjacent location (N=0; E=1; S=2; W=3)
     * @return The location's reachability
     */
    public boolean isOpen(int loc) {
        return openLocs[loc];
    }

    /**
     * Get the direction enum value from it's key
     * 
     * @param key direction character key
     * @return direction enum value
     */
    public static DirectionKey getDirection(char key) {
        for (DirectionKey direction : DirectionKey.values())
            if(direction.getKey() == key)
                return direction;
        return null;
    }

}
