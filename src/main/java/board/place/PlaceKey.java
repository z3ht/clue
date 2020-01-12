package board.place;

public enum PlaceKey {


    WALL("-", new Place());



    String key;
    Place place;

    PlaceKey(String key, Place place) {
        this.key = key;
        this.place = place;
    }


}
