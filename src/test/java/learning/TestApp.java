package learning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The learning.TestApp class helps me gain a better understanding of how JavaFX works
 *
 * It has no test cases and is mostly unrelated to the game
 */
public class TestApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Object.class.getResource("/TestApp.fxml"));

        Scene scene = new Scene(root, 600, 400);

        root.getChildrenUnmodifiable();

        primaryStage.setTitle("Test App!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
