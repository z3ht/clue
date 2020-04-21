package mines.zinno.clue.constant;

import javafx.scene.paint.Color;
import mines.zinno.clue.control.menu.ValueMenuItem;

/**
 * The {@link Suspect} enum holds suspect information
 */
public enum Suspect implements Card {

    MISS_SCARLETT("Miss Scarlett", 4, Color.RED),
    PROF_PLUM("Prof. Plum", 1, Color.PURPLE),
    COL_MUSTARD("Col. Mustard", 0, Color.ORANGE),
    MRS_PEACOCK("Mrs. Peacock", 3, Color.BLUE),
    MR_GREEN("Mr. Green",2, Color.GREEN),
    MRS_WHITE("Mrs. White", 5, Color.WHITE)
    ;

    private int id;
    private String name;
    private Color color;

    Suspect(String name, int id, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * @return {@link Suspect#getName()}
     */
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Corresponding {@link ValueMenuItem}<{@link Suspect}>
     */
    public ValueMenuItem<Suspect> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }

    /**
     * Get {@link Color} of the character
     */
    public Color getColor() {
        return color;
    }
}
