package learning;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The learning.BasicApp class helps me gain a better understanding of how JavaFX works
 *
 * It has no test cases and is unrelated to the game
 */
public class BasicApp extends Application {

    private static final int SCALE = 10;
    private static final int PADDING = 5;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void  start(Stage primaryStage) {

        Scene primaryScene = new Scene(new Group(), 160*SCALE, 90*SCALE);

        GridPane primaryGrid = new GridPane();
        primaryGrid.getStylesheets().add(BasicApp.class.getResource("/css/ui-style.css").toExternalForm());
        primaryGrid.setId("background");

        primaryGrid.getColumnConstraints().add(new ColumnConstraints(110*SCALE));

        primaryGrid.setPrefSize(primaryScene.getWidth(), primaryScene.getHeight());
        primaryGrid.setPadding(new Insets(PADDING));

        GridPane gameGrid = new GridPane();
        gameGrid.setPadding(new Insets(PADDING));
        gameGrid.setMinSize(1100-PADDING*2, 900-PADDING*2);
        gameGrid.setStyle("-fx-background-color: #009900;");
        primaryGrid.add(gameGrid, 0, 0);

        GridPane infoGrid = new GridPane();
        infoGrid.setMinSize(500-PADDING*2, 900-PADDING*2);

        infoGrid.getRowConstraints().add(new RowConstraints(25*SCALE));
        infoGrid.getRowConstraints().add(new RowConstraints(25*SCALE-PADDING));
        infoGrid.getRowConstraints().add(new RowConstraints(25*SCALE-PADDING));
        infoGrid.getRowConstraints().add(new RowConstraints(15*SCALE-PADDING));

//        GridPane weaponsSheet = new Sheet("Weapons", readCSV(this.getClass().getResource("weapons.csv").getPath()), 25);
//        weaponsSheet.setPadding(new Insets(PADDING));
//        weaponsSheet.setPrefSize(50*SCALE, 25*SCALE);
//        weaponsSheet.setStyle("-fx-background-color: #2200ff;");
//        infoGrid.add(weaponsSheet, 0, 0);

        GridPane roomsSheet = new GridPane();
        roomsSheet.setPadding(new Insets(PADDING));
        roomsSheet.setStyle("-fx-background-color: #993300;");
        roomsSheet.setPrefSize(50*SCALE, 25*SCALE);
        infoGrid.add(roomsSheet, 0, 1);

        GridPane suspectsSheet = new GridPane();
        suspectsSheet.setPadding(new Insets(PADDING));
        suspectsSheet.setStyle("-fx-background-color: #00eeee;");
        suspectsSheet.setPrefSize(50*SCALE, 25*SCALE);
        infoGrid.add(suspectsSheet, 0, 2);

        GridPane options =  new GridPane();
        options.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
        options.setPrefSize(50*SCALE, 15*SCALE);
        options.setStyle("-fx-background-color: #ff00ff;");

        options.getRowConstraints().add(new RowConstraints(3*SCALE));
        options.getRowConstraints().add(new RowConstraints(6*SCALE));
        options.getRowConstraints().add(new RowConstraints(6*SCALE));

        options.getColumnConstraints().add(new ColumnConstraints(25*SCALE));

        GridPane info = new GridPane();

        info.getColumnConstraints().add(new ColumnConstraints(34*SCALE));
        info.getColumnConstraints().add(new ColumnConstraints(8*SCALE));
        info.getColumnConstraints().add(new ColumnConstraints(8*SCALE));

        Label infoLabel = new Label("Info Box");
        info.add(infoLabel, 0, 0);

        Button tutorial = new Button("?");
        info.add(tutorial, 1, 0);

        Button settings = new Button("S");
        info.add(settings, 2, 0);

        options.add(info, 0, 0, 1, 2);

        Button roll = new Button("Roll");
        options.add(roll, 0, 1);

        Button guess = new Button("Guess");
        options.add(guess, 1, 1);

        Button continueB = new Button("Continue");
        options.add(continueB, 0, 2);

        Button skip = new Button("Skip");
        options.add(skip, 1, 2);

        infoGrid.add(options, 0, 3);

        primaryGrid.add(infoGrid, 1, 0);

        Group root = (Group) primaryScene.getRoot();
        root.getChildren().add(primaryGrid);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
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
//            LOGGER.log(Level.WARNING, String.format("CSV not found at: %s", path));
            return Collections.emptyList();
        }
    }


}
