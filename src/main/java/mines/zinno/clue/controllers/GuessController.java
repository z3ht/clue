package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;

import java.net.URL;
import java.util.ResourceBundle;

public class GuessController implements Initializable {

    @FXML
    private SelectableMenu<Suspect> suspectMenu;
    
    @FXML
    private SelectableMenu<Room> roomMenu;
    
    @FXML
    private SelectableMenu<Weapon> weaponMenu;
    
    @FXML
    private Button guess;

    public MenuButton getSuspectMenu() {
        return suspectMenu;
    }

    public MenuButton getRoomMenu() {
        return roomMenu;
    }
    
    public MenuButton getWeaponMenu() {
        return weaponMenu;
    }

    public Button getGuess() {
        return guess;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Suspect suspect : Suspect.values())
            this.suspectMenu.getItems().add(suspect.getMenuItem());
        for(Room room : Room.values())
            this.roomMenu.getItems().add(room.getMenuItem());
        for(Weapon weapon : Weapon.values())
            this.weaponMenu.getItems().add(weapon.getMenuItem());
    }
}
