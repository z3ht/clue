package mines.zinno.clue.enums;

public enum Weapons {

    KNIFE("Knife", 1),
    CANDLESTICK("Candlestick", 2),
    REVOLVER("Revolver", 3),
    ROPE("Rope", 4),
    LEAD_PIPE("Lead Pipe", 5),
    WRENCH("Wrench", 6)
    ;

    private int id;
    private String name;

    Weapons(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
