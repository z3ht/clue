package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mines.zinno.clue.layouts.board.ClueBoard;
import mines.zinno.clue.layouts.Sheet;

public class ClueController {

    @FXML
    private ClueBoard board;

    @FXML
    private Sheet suspects;

    @FXML
    private Sheet weapons;

    @FXML
    private Sheet rooms;

    @FXML
    private Label infoLabel;

    public Sheet getSuspectsSheet() {
        return suspects;
    }

    public Sheet getWeaponsSheet() {
        return weapons;
    }

    public Sheet getRoomsSheet() {
        return rooms;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public ClueBoard getBoard() {
        return board;
    }
}
