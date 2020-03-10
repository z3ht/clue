package mines.zinno.clue.layouts.board.enums;

import mines.zinno.clue.shapes.place.Place;

/**
 * The {@link PlaceKey} assigns static {@link Place} characters with an enum value. Non-static place keys are temporarily
 * assigned as OTHER until being reassigned. A static place is one whose {@link Place}: 1) always corresponds to the
 * same key character and 2) functionality remains identical across the board.
 */
public enum PlaceKey {

    START('*'),
    PATH('x'),
    UNREACHABLE('.'),
    OTHER((char) 0);    // null

    private char key;

    PlaceKey(char key) {
        this.key = key;
    }

    /**
     * Get {@link Place}s key
     */
    public char getKey() {
        return key;
    }

    /**
     * Find {@link PlaceKey} from its character key
     * 
     * @param key Character key assigned to the {@link Place}
     * @return Corresponding {@link PlaceKey}
     */
    public static PlaceKey getPlaceKey(char key) {
        for (PlaceKey place: PlaceKey.values())
            if(place.getKey() == key)
                return place;
        return OTHER;
    }

}
