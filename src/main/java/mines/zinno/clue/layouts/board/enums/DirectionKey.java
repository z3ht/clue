package mines.zinno.clue.layouts.board.enums;

public enum DirectionKey {

    HORIZONTAL_DOOR('-', new boolean[] {false, true, false, true}),
    VERTICAL_DOOR('|', new boolean[] {true, false, true, false}),
    RIGHT_WALL('}', new boolean[] {true, false, true, true}),
    LEFT_WALL('{', new boolean[] {true, true, true, false}),
    LOWER_WALL('.', new boolean[] {true, true, false, true}),
    UPPER_WALL('^', new boolean[] {false, true, true, true}),
    ALL(' ', new boolean[] {true, true, true, true});

    private char key;
    private boolean[] openLocs;

    DirectionKey(char key, boolean[] openLocs) {
        this.openLocs = openLocs;
        this.key = key;
    }

    public char getKey() {
        return key;
    }

    public boolean[] getOpenLocs() {
        return openLocs;
    }
    
    public boolean isOpen(int loc) {
        return openLocs[loc];
    }

    public static DirectionKey getDirection(char key) {
        for (DirectionKey direction : DirectionKey.values())
            if(direction.getKey() == key)
                return direction;
        return null;
    }

}
