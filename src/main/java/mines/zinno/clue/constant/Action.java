package mines.zinno.clue.constant;

/**
 * The {@link Action} enum holds action messages used throughout the Clue game. This enum is used when a standard event
 * triggers during the game.
 */
public enum Action {
    
    GUESS("%s guessed %s committed the murder in the %s using the %s"),
    NOTHING_FOUND("Nobody has the cards you're looking for"),
    ANONYMOUS_CLUE("\t- %s showed %s a card"),
    CLUE("%s reveals %s to you"),
    ROLL_NUM("%s rolled a %d"),
    MOVES_LEFT("You have %d move(s) left")
    ;
    
    public final static String GUESS_TITLE = "Guess";
    
    private String text;
    
    Action(String text) {
        this.text = text;
    }

    /**
     * Get the {@link Action} text message
     */
    public String getText(Object... args) {
        return String.format(text, args);
    }
}
