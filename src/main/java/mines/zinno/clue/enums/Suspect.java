package mines.zinno.clue.enums;

import mines.zinno.clue.control.menu.ValueMenuItem;

public enum Suspect {

    COL_MUSTARD("Col. Mustard", 1),
    PROF_PLUM("Prof. Plum", 2),
    MR_GREEN("Mr. Green",3),
    MRS_PEACOCK("Mrs. Peacock", 4),
    MISS_SCARLETT("Miss Scarlett", 5),
    MRS_WHITE("Mrs. White", 6)
    ;

    private int id;
    private String name;

    Suspect(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public ValueMenuItem<Suspect> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
    
}
