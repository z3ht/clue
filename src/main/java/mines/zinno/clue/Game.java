package mines.zinno.clue;

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

    public void start(Stage primaryStage) {
        LOGGER.log(Level.INFO, "Clue mines.zinno.clue.Game Started");

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

    private void populateStage(Stage stage) {
        Scene primaryScene = new Scene(new Group(), SIZE.width*SCALE.getKey(), SIZE.height*SCALE.getValue());

        GridPane primaryGrid = new GridPane();
        primaryGrid.getStylesheets().add(Object.class.getResource("/css/ui-style.css").toExternalForm());
        primaryGrid.setId("background");

        ColumnConstraints primaryDivider = new ColumnConstraints();
        primaryDivider.setPercentWidth(PRIMARY_DIVIDER);
        primaryGrid.getColumnConstraints().add(primaryDivider);
        primaryGrid.setHgap(PADDING);
        primaryGrid.setPadding(new Insets(PADDING));

        primaryGrid.setPrefSize(primaryScene.getWidth(), primaryScene.getHeight());

        this.board = new Board();
        board.setPadding(new Insets(PADDING));
        board.setMinSize((primaryScene.getWidth()-PADDING*2)*(PRIMARY_DIVIDER), primaryScene.getHeight()-PADDING*2);
        board.setStyle("-fx-background-color: #009900;");
        primaryGrid.add(board, 0, 0);

        GridPane infoGrid = new GridPane();
        infoGrid.setMinSize((primaryScene.getWidth()-PADDING*2)*(1-PRIMARY_DIVIDER), primaryScene.getHeight()-PADDING*2);
        infoGrid.setMaxSize((primaryScene.getWidth()-PADDING*2)*(1-PRIMARY_DIVIDER), primaryScene.getHeight()-PADDING*2);

        infoGrid.getRowConstraints().add(new RowConstraints(0.22*(primaryScene.getHeight()-PADDING*2)));
        infoGrid.getRowConstraints().add(new RowConstraints(0.22*(primaryScene.getHeight()-PADDING*2)));
        infoGrid.getRowConstraints().add(new RowConstraints(0.3*(primaryScene.getHeight()-PADDING*2)));
        infoGrid.getRowConstraints().add(new RowConstraints(0.25*(primaryScene.getHeight()-PADDING*2)));

        this.suspectsSheet = new Sheet("Suspects", readCSV(Object.class.getResource("/roles.csv").toExternalForm()), ROW_SIZE);
        infoGrid.add(suspectsSheet, 0, 0);

        this.weaponsSheet = new Sheet("Weapons", readCSV(Object.class.getResource("/weapons.csv").getPath()), ROW_SIZE);
        infoGrid.add(weaponsSheet, 0, 1);

        this.roomsSheet = new Sheet("Rooms", readCSV(Object.class.getResource("/rooms.csv").getPath()), ROW_SIZE);
        infoGrid.add(roomsSheet, 0, 2);
//
//        GridPane options =  new GridPane();
//        options.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
//        options.setPrefSize(50*SCALE, 15*SCALE);
//        options.setStyle("-fx-background-color: #ff00ff;");
//
//        options.getRowConstraints().add(new RowConstraints(3*SCALE));
//        options.getRowConstraints().add(new RowConstraints(6*SCALE));
//        options.getRowConstraints().add(new RowConstraints(6*SCALE));
//
//        options.getColumnConstraints().add(new ColumnConstraints(25*SCALE));
//
//        GridPane info = new GridPane();
//
//        info.getColumnConstraints().add(new ColumnConstraints(34*SCALE));
//        info.getColumnConstraints().add(new ColumnConstraints(8*SCALE));
//        info.getColumnConstraints().add(new ColumnConstraints(8*SCALE));
//
//        Label infoLabel = new Label("Info Box");
//        info.add(infoLabel, 0, 0);
//
//        Button tutorial = new Button("?");
//        info.add(tutorial, 1, 0);
//
//        Button settings = new Button("S");
//        info.add(settings, 2, 0);
//
//        options.add(info, 0, 0, 1, 2);
//
//        Button roll = new Button("Roll");
//        options.add(roll, 0, 1);
//
//        Button guess = new Button("Guess");
//        options.add(guess, 1, 1);
//
//        Button continueB = new Button("Continue");
//        options.add(continueB, 0, 2);
//
//        Button skip = new Button("Skip");
//        options.add(skip, 1, 2);
//
//        infoGrid.add(options, 0, 3);

        primaryGrid.add(infoGrid, 1, 0);

        Group root = (Group) primaryScene.getRoot();
        root.getChildren().add(primaryGrid);
        stage.setScene(primaryScene);
    }

    private List<String> readCSV(String path) {
        try {
            List<String> values = new ArrayList<String>();
            Scanner sc = new Scanner(new File(path));
            sc.useDelimiter(",");
            while(sc.hasNext())
                values.add(sc.next());
            return values;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, String.format("CSV not found at: %s", path));
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
