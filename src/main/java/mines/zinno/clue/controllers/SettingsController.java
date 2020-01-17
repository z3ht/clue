package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.enums.Difficulty;
import mines.zinno.clue.enums.Digit;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.shapes.character.Character;

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
    
    public MenuButton getCharacter() {
        return character;
    }

    public MenuButton getDifficulty() {
        return difficulty;
    }

    public MenuButton getComputers() {
        return computers;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Difficulty difficulty : Difficulty.values())
            this.difficulty.getItems().add(difficulty.getMenuItem());
        this.difficulty.setSelectedItem(Difficulty.REGULAR);
        this.difficulty.setLocked(true);

        for(Suspect character : Suspect.values())
            this.character.getItems().add(character.getMenuItem());
        
        for(Digit digit : Digit.values())
            this.computers.getItems().add(digit.getMenuItem());
    }
}
