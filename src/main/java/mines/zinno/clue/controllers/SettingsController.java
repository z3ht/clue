package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.enums.Difficulty;
import mines.zinno.clue.enums.Digit;
import mines.zinno.clue.enums.Suspect;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private SelectableMenu<Suspect> character;

    @FXML
    private SelectableMenu<Difficulty> difficulty;

    @FXML
    private SelectableMenu<Digit> computers;

    @FXML
    private Button begin;
    
    public SelectableMenu<Suspect> getCharacter() {
        return character;
    }

    public SelectableMenu<Difficulty> getDifficulty() {
        return difficulty;
    }

    public SelectableMenu<Digit> getComputers() {
        return computers;
    }

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
    }
}
