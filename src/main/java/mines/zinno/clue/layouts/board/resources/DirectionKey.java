package mines.zinno.clue.layouts.board.resources;

public enum DirectionKey {

    HORIZONTAL("-"),
    VERTICAL("|"),
    ALL(" ");

    String key;

    DirectionKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
