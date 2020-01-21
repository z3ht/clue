package mines.zinno.clue.enums;

import mines.zinno.clue.control.menu.ValueMenuItem;

public enum Weapon {

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    public ValueMenuItem<Weapon> getMenuItem() {
        return new ValueMenuItem<>(name, this);
    }
}
