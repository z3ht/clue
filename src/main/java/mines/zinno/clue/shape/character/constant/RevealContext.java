package mines.zinno.clue.shape.character.constant;

/**
 * The {@link RevealContext} enum provides Context for when a card is revealed to a character 
 */
public enum RevealContext {
    PROVIDED(
            Turn.PRE_ROLL,
            "You have been dealt the following cards:\n",
            null
    ),
    ON_GUESS(
            Turn.POST_GUESS,
            "You guessed the murderer was %s in the %s using the %s ",
            "%s guessed the murderer was %s in the %s using the %s"
    ),
    LOST_GAME(
            Turn.POST_GUESS,
            "Your accusation was incorrect and you have been murdered by %s using the %s in the %s\n" +
                    "View the settings menu to play again",
            "%s has been murdered by %s"
    ),
    ANY(null, null, null);

    Turn displayTurn;
    String playerFormat;
    String computerFormat;

    RevealContext(Turn displayTurn, String playerFormat, String computerFormat) {
        this.displayTurn = displayTurn;
        this.playerFormat = playerFormat;
        this.computerFormat = computerFormat;
    }

    public Turn getDisplayTurn() {
        return displayTurn;
    }

    public String getPlayerFormat() {
        return playerFormat;
    }

    public String getComputerFormat() {
        return computerFormat;
    }
}
