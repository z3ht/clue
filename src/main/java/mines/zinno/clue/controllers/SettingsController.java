package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.enums.Alert;
import mines.zinno.clue.enums.Difficulty;
import mines.zinno.clue.enums.Digit;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.stages.dialogue.BasicInfoDialogue;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The {@link SettingsController} is the controller linked to the Settings.fxml file
 */
public class SettingsController implements Initializable {

    @FXML
    private SelectableMenu<Suspect> character;

    @FXML
    private SelectableMenu<Difficulty> difficulty;

    @FXML
    private SelectableMenu<Digit> computers;

    @FXML
    private Button begin;

    /**
     * Get the character {@link SelectableMenu}<{@link Suspect}>
     * 
     * @return Character {@link SelectableMenu}<{@link Suspect}>
     */
    public SelectableMenu<Suspect> getCharacter() {
        return character;
    }

    /**
     * Get the difficulty {@link SelectableMenu}<{@link Difficulty}>
     *
     * @return Difficulty {@link SelectableMenu}<{@link Difficulty}>
     */
    public SelectableMenu<Difficulty> getDifficulty() {
        return difficulty;
    }

    /**
     * Get the computer {@link SelectableMenu}<{@link Digit}>
     *
     * @return Computer {@link SelectableMenu}<{@link Digit}>
     */
    public SelectableMenu<Digit> getComputers() {
        return computers;
    }

    /**
     * Called by {@link javafx.fxml.FXMLLoader}. Initialize computer, difficulty, and character options
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Digit digit : Digit.values())
            this.computers.getItems().add(digit.getMenuItem());
        this.computers.setSelectedItem(Digit.FIVE);
        this.computers.setLocked(true);
        
        for(Difficulty difficulty : Difficulty.values())
            this.difficulty.getItems().add(difficulty.getMenuItem());
        this.difficulty.setSelectedItem(Difficulty.REGULAR);
        this.difficulty.setLocked(true);

        for(Suspect character : Suspect.values())
            this.character.getItems().add(character.getMenuItem());
        
        begin.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
            new BasicInfoDialogue(Alert.INCOMPLETE_FEATURE.getName(), Alert.INCOMPLETE_FEATURE.getText()).show()
        );
    }
}
