package mines.zinno.clue.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The {@link DialogueController} is the controller linked to the ShortDialogue.fxml file
 */
public class DialogueController {

    @FXML
    private Label infoLabel;

    /**
     * Get the info label
     * 
     * @return {@link Label}
     */
    public Label getInfoLabel() {
        return infoLabel;
    }


    
}
