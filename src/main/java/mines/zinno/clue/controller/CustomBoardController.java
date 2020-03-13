package mines.zinno.clue.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
