package mines.zinno.clue.stage.dialogue;

import mines.zinno.clue.constant.io.FXMLURL;
import mines.zinno.clue.controller.DialogueController;

import java.awt.*;

/**
 * The {@link ScrollableDialogue} class holds scrollable information
 */
public class ScrollableDialogue extends Dialogue<DialogueController> {

    public ScrollableDialogue(String name) {
        this(name, new Dimension(450, 225));
    }

    public ScrollableDialogue(String name, Dimension size) {
        super(name, FXMLURL.SCROLLABLE_DIALOGUE.getUrl(), size);

        this.getController().getInfoLabel().setText("");
        this.setAlwaysOnTop(true);

        setResizable(false);

        this.toFront();
    }

}
