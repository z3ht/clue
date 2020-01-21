package mines.zinno.clue.enums;

import mines.zinno.clue.control.menu.ValueMenuItem;

public enum Difficulty {
    
    EASY("Easy"),
    REGULAR("Regular"),
    DIFFICULT("Difficult");
    
    private String name;
    private ValueMenuItem<Difficulty> menuItem;
    
    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public ValueMenuItem<Difficulty> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
    
}
