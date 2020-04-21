package mines.zinno.clue.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ScrollableDialogueController extends DialogueController {

    @FXML
    private AnchorPane scrollPane;

    public AnchorPane getScrollPane() {
        return scrollPane;
    }
}
