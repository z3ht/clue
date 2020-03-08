package mines.zinno.clue.layouts.status.enums;

//TODO add font parsing enum to allow for in line bolding and coloring

public enum Alert {
    
    WELCOME("Welcome", "Welcome to the game of Clue!\nPress the '?' button for a tutorial"),
    INCOMPLETE_FEATURE("Error", "This feature is not yet complete.\nPlease try again another time"),
    INVALID_GUESS("Invalid Guess", "This guess is invalid.\nPlease fill out all of the items and try again"),
    OUT_OF_TURN("Out of Turn", "You may not perform this action yet")
    ;
    
    
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
