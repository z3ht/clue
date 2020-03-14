package mines.zinno.clue.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * The {@link CustomBoardController} is the controller linked to the CustomBoard.fxml file
 */
public class CustomBoardController {
    
    @FXML
    private TextField mapLocation;
    
    @FXML
    private TextField imgLocation;


    public TextField getMapLocation() {
        return mapLocation;
    }

    public TextField getImgLocation() {
        return imgLocation;
    }
}
