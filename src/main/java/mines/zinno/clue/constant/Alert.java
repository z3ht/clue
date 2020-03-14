package mines.zinno.clue.constant;

/**
 * The {@link Alert} enum holds alert messages used throughout the Clue game. An alert is a popup message that occurs
 * throughout the game.
 */
public enum Alert {
    
    WELCOME("Welcome", "Welcome to the game of Clue!\nPress the '?' button for a tutorial"),
    INCOMPLETE_FEATURE("Error", "This feature is not yet complete.\nPlease try again another time"),
    INVALID_GUESS("Invalid Guess", "This guess is invalid.\nPlease fill out all of the items and try again"),
    OUT_OF_TURN("Out of Turn", "You may not perform this action yet"),
    IMPOSSIBLE_MOVE("Impossible Move", "This action is not possible\n%s"),
    INCOMPLETE_GUESS("Incomplete Guess", "Your guess must include a %s"),
    REACHED_EXIT("Warning", "You have entered the exit room\nGuesses here will win or lose you the game")
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
    public String getText(Object... args) {
        return String.format(text, args);
    }
    
    /**
     * Get the name of an {@link Alert} in {@link String} format
     */
    public String getName() {
        return name;
    }
}
