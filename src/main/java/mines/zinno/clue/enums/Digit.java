package mines.zinno.clue.enums;

import mines.zinno.clue.control.menu.ValueMenuItem;

public enum Digit {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    ;
    
    private String name;
    
    Digit(String name) {
        this.name = name;    
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public ValueMenuItem<Digit> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
}
