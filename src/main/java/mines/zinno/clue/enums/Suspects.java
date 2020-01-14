package mines.zinno.clue.enums;

public enum Suspects {

    COL_MUSTARD("Col. Mustard", 1),
    PROF_PLUM("Prof. Plum", 2),
    MR_GREEN("Mr. Green",3),
    MRS_PEACOCK("Mrs. Peacock", 4),
    MISS_SCARLETT("Miss Scarlett", 5),
    MRS_WHITE("Mrs. White", 6)
    ;

    private int id;
    private String name;

    Suspects(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
