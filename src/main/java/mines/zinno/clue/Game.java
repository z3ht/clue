package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import mines.zinno.clue.layouts.Board;
import mines.zinno.clue.logic.character.ComputerCharacter;
import mines.zinno.clue.logic.character.PlayerCharacter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Pair;
import mines.zinno.clue.sheet.Sheet;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Application {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final Dimension SIZE = new Dimension(140, 90);
    private static final Pair<Double, Double> SCALE = new Pair<Double, Double>(10.0, 10.0);
    private static final double PADDING = 10;

    private static final double PRIMARY_DIVIDER = 0.7;
    private static final double SHEET_HEIGHT = 0.26;
    private static final int ROW_SIZE = 22;

    private Board board;

    private Sheet weaponsSheet;
    private Sheet roomsSheet;
    private Sheet suspectsSheet;

    private PlayerCharacter player;
    private List<ComputerCharacter> computers;

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

    public Board getBoard() {
        return board;
    }

    public Sheet getWeaponsSheet() {
        return weaponsSheet;
    }

    public Sheet getRoomsSheet() {
        return roomsSheet;
    }

    public Sheet getSuspectsSheet() {
        return suspectsSheet;
    }

    public PlayerCharacter getPlayer() {
        return player;
    }

    public List<ComputerCharacter> getComputers() {
        return computers;
    }

    private void populateStage(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Object.class.getResource("/fxml/Clue.fxml"));

        Scene scene = new Scene(root, 1400, 900);

        root.getChildrenUnmodifiable();

        stage.setTitle("Clue");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
