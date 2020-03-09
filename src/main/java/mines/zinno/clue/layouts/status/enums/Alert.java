package mines.zinno.clue.layouts.status.enums;

//TODO add font parsing enum to allow for in line bolding and coloring
/**
 * The {@link Alert} enum holds alert messages used throughout the Clue game. An alert is a popup message that occurs
 * throughout the game.
 */
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

    /**
     * Get the {@link Alert} text message
     */
    public String getText() {
        return text;
    }

    /**
     * @return {@link Alert#getText()}
     */
    @Override
    public String toString() {
        return getText();
    }

    /**
     * Get the name of an {@link Alert} in {@link String} format
     */
    public String getName() {
        return name;
    }
}
