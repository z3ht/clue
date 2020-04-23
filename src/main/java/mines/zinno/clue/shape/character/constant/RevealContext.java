package mines.zinno.clue.shape.character.constant;

/**
 * The {@link RevealContext} enum provides Context for when a card is revealed to a character 
 */
public enum RevealContext {
    PROVIDED(
            Turn.PRE_ROLL,
            "You have been dealt the following cards:",
            "   - %s",
            null,
            null
    ),
    ON_GUESS(
            Turn.POST_GUESS,
            null,
            "%s showed you the %s card",
            null,
            "%s showed %s a card"
    ),
    LOST_GAME(
            Turn.POST_GUESS,
            "%s has been murdered! They had the following cards:",
            "   - %s",
            null,
            null
    ),
    ANY(null, null, null, null, null);

    Turn displayTurn;
    String playerHeader;
    String playerBody;
    String computerHeader;
    String computerBody;

    RevealContext(Turn displayTurn, String playerHeader, String playerBody, String computerHeader, String computerBody) {
        this.displayTurn = displayTurn;
        this.playerHeader = playerHeader;
        this.playerBody = playerBody;
        this.computerHeader = computerHeader;
        this.computerBody = computerBody;
    }

    /**
     * Get the turn the reveal context should be displayed on
     *
     * @return {@link Turn}
     */
    public Turn getDisplayTurn() {
        return displayTurn;
    }

    /**
     * Get the header for when a player is shown a card
     *
     * @return {@link String}
     */
    public String getPlayerHeader() {
        return playerHeader;
    }

    /**
     * Get the body for when a player is shown a card
     *
     * @return {@link String}
     */
    public String getPlayerBody() {
        return playerBody;
    }

    /**
     * Get the header for when a computer is shown a card
     *
     * @return {@link String}
     */
    public String getComputerHeader() {
        return computerHeader;
    }

    /**
     * Get the body for when a computer is shown a card
     *
     * @return {@link String}
     */
    public String getComputerBody() {
        return computerBody;
    }
}
