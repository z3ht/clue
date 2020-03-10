package mines.zinno.clue.constant;

import mines.zinno.clue.control.menu.ValueMenuItem;

/**
 * The {@link Difficulty} enum holds digits. It is used in the {@link mines.zinno.clue.control.menu.SelectableMenu} to
 * set the number of players.
 */
public enum Digit {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5")
    ;
    
    private String name;
    
    Digit(String name) {
        this.name = name;    
    }

    /**
     * {@link Digit} in {@link String} format
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link Digit#getName()}
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return Corresponding {@link ValueMenuItem}<{@link Digit}>
     */
    public ValueMenuItem<Digit> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
}
