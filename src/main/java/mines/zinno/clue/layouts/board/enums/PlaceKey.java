package mines.zinno.clue.layouts.board.enums;

import mines.zinno.clue.shapes.place.BasicPlace;
import mines.zinno.clue.shapes.place.Place;
import mines.zinno.clue.shapes.place.StartPlace;

/**
 * The {@link PlaceKey} assigns place character's with an enum value. Some place key's are temporarily assigned as OTHER
 * until being reassigned
 */
public enum PlaceKey {

    START('*', new StartPlace()),
    PATH('x', new BasicPlace()),
    UNREACHABLE('.', new Place(DirectionKey.ALL, false, 1)),
    OTHER((char) 0, null);    // null

    private char key;
    private Place place;

    PlaceKey(char key, Place place) {
        this.key = key;
        this.place = place;
    }

    public Place getPlace() {return place;}

    public char getKey() {
        return key;
    }

    public static PlaceKey getPlaceKey(char key) {
        for (PlaceKey place: PlaceKey.values())
            if(place.getKey() == key)
                return place;
        return OTHER;
    }

}
