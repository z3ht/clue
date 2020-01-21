package mines.zinno.clue.layouts.status.enums;

//TODO add font parsing enum to allow for in line bolding and coloring

public enum Alert {
    
    WELCOME("Welcome", "Welcome to the game of Clue!\nPress the '?' button for a tutorial");
    
    
    private String name;
    private String text;
    
    Alert(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }

    public String getName() {
        return name;
    }
}
