package mines.zinno.clue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import mines.zinno.clue.layouts.Board;
import mines.zinno.clue.layouts.ClueBoard;
import mines.zinno.clue.layouts.Sheet;
import mines.zinno.clue.logic.character.ComputerCharacter;
import mines.zinno.clue.logic.character.PlayerCharacter;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Application {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private PlayerCharacter player;
    private List<ComputerCharacter> computers;

    @FXML
    private ClueBoard board;

    @FXML
    private Sheet suspects;

    @FXML
    private Sheet weapons;

    @FXML
    private Sheet rooms;

    @FXML
    private javafx.scene.control.Label infoLabel;


    public void start(Stage primaryStage) throws IOException {
        LOGGER.log(Level.INFO, "Clue Game Started");

        populateStage(primaryStage);

        LOGGER.log(Level.INFO, "Stage populated");

        primaryStage.show();

        LOGGER.log(Level.INFO, "Stage shown");
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public Sheet getSuspectsSheet() {
        return suspects;
    }

    public Sheet getWeaponsSheet() {
        return weapons;
    }

    public Sheet getRoomsSheet() {
        return rooms;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public ClueBoard getBoard() {
        return board;
    }

    public PlayerCharacter getPlayer() {
        return player;
    }

    public List<ComputerCharacter> getComputers() {
        return computers;
    }

    private void populateStage(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(Object.class.getResource("/fxml/Clue.fxml"));

        Scene scene = new Scene(fxmlLoader, 1400, 900);

        stage.setTitle("Clue");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
