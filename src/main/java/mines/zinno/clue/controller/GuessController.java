package mines.zinno.clue.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.Suspect;
import mines.zinno.clue.constant.Weapon;

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
     * Get the {@link SelectableMenu}<{@link Suspect}>
     */
    public SelectableMenu<Suspect> getSuspectMenu() {
        return suspectMenu;
    }

    /**
     * Get the {@link SelectableMenu}<{@link Room}>
     */
    public SelectableMenu<Room> getRoomMenu() {
        return roomMenu;
    }

    /**
     * Get the {@link SelectableMenu}<{@link Weapon}>
     */
    public SelectableMenu<Weapon> getWeaponMenu() {
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

        for(Room room : Room.values()) {
            if(room.isExcluded())
                continue;
            this.roomMenu.getItems().add(room.getMenuItem());
        }
        for(Weapon weapon : Weapon.values())
            this.weaponMenu.getItems().add(weapon.getMenuItem());
    }
}
