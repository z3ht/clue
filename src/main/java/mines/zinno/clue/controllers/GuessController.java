package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;

public class GuessController {

    @FXML
    private MenuButton suspectMenu;
    
    @FXML
    private MenuButton roomMenu;
    
    @FXML
    private MenuButton weaponMenu;

    public MenuButton getSuspectMenu() {
        return suspectMenu;
    }

    public MenuButton getRoomMenu() {
        return roomMenu;
    }
    
    public MenuButton getWeaponMenu() {
        return weaponMenu;
    }
}
