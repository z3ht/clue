package mines.zinno.clue.enums;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import mines.zinno.clue.control.menu.ValueMenuItem;

public enum Suspect {

    COL_MUSTARD("Col. Mustard", 1, Color.ORANGE),
    PROF_PLUM("Prof. Plum", 2, Color.PURPLE),
    MR_GREEN("Mr. Green",3, Color.GREEN),
    MRS_PEACOCK("Mrs. Peacock", 4, Color.BLUE),
    MISS_SCARLETT("Miss Scarlett", 5, Color.RED),
    MRS_WHITE("Mrs. White", 6, Color.WHITE)
    ;

    private int id;
    private String name;
    private Color color;

    Suspect(String name, int id, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
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

    public Color getColor() {
        return color;
    }
}
