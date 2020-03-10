package mines.zinno.clue.enums;

import mines.zinno.clue.control.menu.ValueMenuItem;

/**
 * The {@link Weapon} enum holds weapon information
 */
public enum Weapon implements Card {

    KNIFE("knife", 1),
    CANDLESTICK("candlestick", 2),
    REVOLVER("revolver", 3),
    ROPE("rope", 4),
    LEAD_PIPE("lead pipe", 5),
    WRENCH("wrench", 6)
    ;

    private int id;
    private String name;

    Weapon(String name, int id) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return {@link Weapon#getName()}
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return Corresponding {@link ValueMenuItem}<{@link Suspect}>
     */
    public ValueMenuItem<Weapon> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
}
