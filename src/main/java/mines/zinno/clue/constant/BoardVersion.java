package mines.zinno.clue.constant;

import mines.zinno.clue.control.menu.ValueMenuItem;

public enum BoardVersion {
    
    CLASSIC("Classic"),
    CUSTOM("Custom");

    private String name;

    BoardVersion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * @return Corresponding {@link ValueMenuItem}<{@link BoardVersion}>
     */
    public ValueMenuItem<BoardVersion> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
}
