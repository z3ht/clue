package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import mines.zinno.clue.layouts.status.StatusPane;

public class StatusController {
    
    @FXML
    private StatusPane statusPane;

    public StatusPane getStatusPane() {
        return statusPane;
    }

    public void setStatusPane(StatusPane statusPane) {
        this.statusPane = statusPane;
    }
}
