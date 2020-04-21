package mines.zinno.clue.stage.dialogue;

import mines.zinno.clue.constant.io.FXMLURL;
import mines.zinno.clue.controller.ScrollableDialogueController;

import java.awt.*;

public class ScrollableDialogue extends Dialogue<ScrollableDialogueController> {

    public ScrollableDialogue(String name) {
        this(name, new Dimension(450, 225));
    }

    public ScrollableDialogue(String name, Dimension size) {
        super(name, FXMLURL.SCROLLABLE_DIALOGUE.getUrl(), size);

        this.getController().getInfoLabel().setText("");

        this.toFront();
    }

}
