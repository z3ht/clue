package mines.zinno.clue.layouts.status.enums;

//TODO add font parsing enum to allow for in line bolding and coloring
/**
 * The {@link Action} enum holds action messages used throughout the Clue game. This enum is used when a standard event
 * triggers during the game.
 */
public enum Action {
    
    GUESS("%s guessed %s committed the murder in the %s using the %s"),
    ANONYMOUS_CLUE("\t- %s showed %s a card"),
    CLUE("%s revealed %s to you")
    ;
    
    private String text;
    
    Action(String text) {
        this.text = text;
    }

    /**
     * @return {@link Action#getText()}
     */
    @Override
    public String toString() {
        return getText();
    }

    /**
     * Get the {@link Action} text message
     */
    public String getText() {
        return text;
    }
}
