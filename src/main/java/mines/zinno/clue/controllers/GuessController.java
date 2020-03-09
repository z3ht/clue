package mines.zinno.clue.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.listeners.OnGuessConfirm;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The {@link GuessController} is the controller linked to the Guess.fxml file
 */
public class GuessController implements Initializable {

    @FXML
    private SelectableMenu<Suspect> suspectMenu;
    
    @FXML
    private SelectableMenu<Room> roomMenu;
    
    @FXML
    private SelectableMenu<Weapon> weaponMenu;
    
    @FXML
    private Button guess;

    /**
     * Get the suspect {@link MenuButton}
     * 
     * @return Suspect {@link MenuButton}
     */
    public MenuButton getSuspectMenu() {
        return suspectMenu;
    }

    /**
     * Get the room {@link MenuButton}
     *
     * @return Room {@link MenuButton}
     */
    public MenuButton getRoomMenu() {
        return roomMenu;
    }

    /**
     * Get the room {@link MenuButton}
     *
     * @return Room {@link MenuButton}
     */
    public MenuButton getWeaponMenu() {
        return weaponMenu;
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
     * Called by {@link javafx.fxml.FXMLLoader}. Initialize menu's with {@link mines.zinno.clue.control.menu.ValueMenuItem}s
     * from each of the Suspect/Room/Weapon enums.
     */
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
