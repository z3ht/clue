package mines.zinno.clue.controller;

import javafx.fxml.FXML;
import mines.zinno.clue.layout.status.InfoPane;

/**
 * The {@link StatusController} is the controller linked to the Status.fxml file
 */
public class StatusController {
    
    @FXML
    private InfoPane infoPane;

    /**
     * Get the {@link InfoPane}
     * 
     * @return {@link InfoPane}
     */
    public InfoPane getInfoPane() {
        return infoPane;
    }
    
}
