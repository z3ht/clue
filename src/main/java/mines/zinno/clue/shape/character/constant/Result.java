package mines.zinno.clue.shape.character.constant;

import mines.zinno.clue.constant.Alert;

/**
 * The types of results from a character making an accusation
 */
public enum Result {

    COMPUTER_WIN("%s has found the murderer!\nThe murderer was %s in the %s using the %s"),
    COMPUTER_LOSE("%s has been murdered!"),
    PLAYER_WIN("You have found the murderer!\nThe murderer was %s in the %s using the %s"),
    PLAYER_LOSE("You have been murdered!\nThe murderer was %s in the %s using the %s");

    private String name;
    private String text;

    Result(String text) {
        this.name = "Game Over";
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
