package mines.zinno.clue.constant;

import mines.zinno.clue.control.menu.ValueMenuItem;

/**
 * The {@link Difficulty} enum holds difficulty information
 */
public enum Difficulty {
    
    EASY("Easy"),
    REGULAR("Regular"),
    DIFFICULT("Difficult");
    
    private String name;
    private ValueMenuItem<Difficulty> menuItem;
    
    Difficulty(String name) {
        this.name = name;
    }

    /**
     * {@link Difficulty} in {@link String} format
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link Difficulty#getName()}
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return Corresponding {@link ValueMenuItem}<{@link Difficulty}>
     */
    public ValueMenuItem<Difficulty> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
    
}
