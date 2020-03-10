package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.enums.io.FXMLURL;
import mines.zinno.clue.layouts.board.Board;
import mines.zinno.clue.stages.dialogue.Dialogue;
import mines.zinno.clue.layouts.board.ClueBoard;
import mines.zinno.clue.layouts.Sheet;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The {@link ClueController} is the controller linked to the Clue.fxml file
 */
public class ClueController extends GameController implements Initializable {
    
    @FXML
    private Button settings;
    private Dialogue<SettingsController> settingsDialogue;

    @FXML
    private Button help;
    private Dialogue helpDialogue;

    @FXML
    private Button guess;
    private Dialogue<GuessController> guessDialogue;
    
    @FXML
    private Button roll;

    @FXML
    private Button skip;

    @FXML
    private Button next;

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

    /**
     * Called by {@link javafx.fxml.FXMLLoader}. Constructs dialogue windows.
     */
    public ClueController() {
        this.settingsDialogue = new Dialogue<>("Settings", FXMLURL.SETTINGS.getUrl(), new Dimension(415, 500));
        this.helpDialogue = new Dialogue("Help", FXMLURL.HELP.getUrl(), new Dimension(450, 150));
        this.guessDialogue = new Dialogue<>("Guess", FXMLURL.GUESS.getUrl(), new Dimension(600, 250));
    }

    /**
     * Called by {@link javafx.fxml.FXMLLoader}. Add click listeners to corresponding buttons that open dialogue
     * windows
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.settings.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.settingsDialogue.show());
        this.help.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.helpDialogue.show());
        this.guess.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.guessDialogue.show());
    }

    /**
     * Get the suspect {@link Sheet}
     * 
     * @return Suspect {@link Sheet}
     */
    public Sheet getSuspectsSheet() {
        return suspects;
    }

    /**
     * Get the weapons {@link Sheet}
     *
     * @return Weapons {@link Sheet}
     */
    public Sheet getWeaponsSheet() {
        return weapons;
    }

    /**
     * Get the rooms {@link Sheet}
     *
     * @return Rooms {@link Sheet}
     */
    public Sheet getRoomsSheet() {
        return rooms;
    }

    /**
     * Get the info {@link Label}
     *
     * @return Info {@link Label}
     */
    public Label getInfoLabel() {
        return infoLabel;
    }

    /**
     * Get the guess {@link Button}
     *
     * @return Guess {@link Button}
     */
    public Button getGuess() {
        return guess;
    }

    /**
     * Get the skip {@link Button}
     *
     * @return Skip {@link Button}
     */
    public Button getSkip() {
        return skip;
    }

    /**
     * Get the next {@link Button}
     *
     * @return Next {@link Button}
     */
    public Button getNext() {
        return next;
    }

    /**
     * Get the roll {@link Button}
     *
     * @return Roll {@link Button}
     */
    public Button getRoll() {
        return roll;
    }

    /**
     * Get the settings dialogue {@link Dialogue}<{@link SettingsController}>
     *
     * @return Settings dialogue
     */
    public Dialogue<SettingsController> getSettingsDialogue() {
        return settingsDialogue;
    }

    /**
     * Get the help {@link Dialogue}
     *
     * @return Help {@link Dialogue}
     */
    public Dialogue getHelpDialogue() {
        return helpDialogue;
    }

    /**
     * Get the guess dialogue {@link Dialogue}<{@link GuessController}>
     *
     * @return Guess dialogue
     */
    public Dialogue<GuessController> getGuessDialogue() {
        return guessDialogue;
    }

    @Override
    public ClueBoard getBoard() {
        return board;
    }
}
