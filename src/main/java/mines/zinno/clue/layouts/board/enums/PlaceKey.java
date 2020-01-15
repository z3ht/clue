package mines.zinno.clue.layouts.board.enums;

public enum PlaceKey {

    START('*'),
    PATH('x'),
    UNREACHABLE('.'),
    OTHER((char) 0);

    private char key;

    PlaceKey(char key) {
        this.key = key;
    }

    public char getKey() {
        return key;
    }

    public static PlaceKey getPlace(char key) {
        for (PlaceKey place: PlaceKey.values())
            if(place.getKey() == key)
                return place;
        return OTHER;
    }

}
