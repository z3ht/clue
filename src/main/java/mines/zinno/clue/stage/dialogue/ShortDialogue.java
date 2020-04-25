package mines.zinno.clue.stage.dialogue;

import mines.zinno.clue.controller.DialogueController;
import mines.zinno.clue.constant.io.FXMLURL;

import java.awt.*;

/**
 * The {@link ShortDialogue} displays basic game information
 */
public class ShortDialogue extends Dialogue<DialogueController> {

    /**
     * Create a default {@link ShortDialogue}
     *
     * @param name The name of the dialogue
     * @param text The text on the dialogue
     */
    public ShortDialogue(String name, String text) {
        super(name, FXMLURL.SHORT_DIALOGUE.getUrl(), new Dimension(450, 150));
        this.setAlwaysOnTop(true);

        setResizable(false);

        this.getController().getInfoLabel().setText(text);
    }
    
}
