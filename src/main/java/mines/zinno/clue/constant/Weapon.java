package mines.zinno.clue.constant;

import mines.zinno.clue.control.menu.ValueMenuItem;

/**
 * The {@link Weapon} enum holds weapon information
 */
public enum Weapon implements Card {

    KNIFE("Knife", 0),
    CANDLESTICK("Candlestick", 1),
    REVOLVER("Revolver", 2),
    ROPE("Rope", 3),
    LEAD_PIPE("Lead Pipe", 4),
    WRENCH("Wrench", 5)
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
