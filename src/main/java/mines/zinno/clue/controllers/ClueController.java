package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mines.zinno.clue.dialogues.Dialogue;
import mines.zinno.clue.layouts.board.ClueBoard;
import mines.zinno.clue.layouts.Sheet;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ClueController implements Initializable {
    
    @FXML
    private Button settings;
    private Dialogue settingsDialogue;
    
    @FXML
    private Button help;
    private Dialogue helpDialogue;
    
    @FXML
    private Button guess;
    private Dialogue guessDialogue;
    
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
    
    public ClueController() {
        this.settingsDialogue = new Dialogue("Settings", ClueController.class.getResource("/fxml/Settings.fxml"), new Dimension(415, 500));
        this.helpDialogue = new Dialogue("Help", ClueController.class.getResource("/fxml/Help.fxml"), new Dimension(450, 150));
        this.guessDialogue = new Dialogue("Guess", ClueController.class.getResource("/fxml/Guess.fxml"), new Dimension(600, 250));
    }

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

    public Dialogue getSettingsDialogue() {
        return settingsDialogue;
    }

    public Dialogue getHelpDialogue() {
        return helpDialogue;
    }

    public Dialogue getGuessDialogue() {
        return guessDialogue;
    }

    public ClueBoard getBoard() {
        return board;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
