package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;
import mines.zinno.clue.controllers.ClueController;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Application {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private ClueController controller;
    private List<Character> characters;

    public void start(Stage primaryStage) throws IOException {
        LOGGER.log(Level.INFO, "Clue Game Started");

        populateStage(primaryStage);

        LOGGER.log(Level.INFO, "Stage populated");
        
        addListeners(primaryStage);
        
        LOGGER.log(Level.INFO, "Listeners added");

        primaryStage.setOnShown(this::startGame);

        primaryStage.show();

        LOGGER.log(Level.INFO, "Stage shown");
    }

    private void startGame(WindowEvent event) {
        this.controller.getBoard().format();
        this.controller.getSuspectsSheet().crossOut(2);
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public ClueController getController() {
        return controller;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    private void populateStage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Object.class.getResource("/fxml/Clue.fxml"));

        Parent root = fxmlLoader.load();

        this.controller = fxmlLoader.getController();

        Scene scene = new Scene(root, 1400, 900);
        
        stage.setTitle("Clue");
        stage.setResizable(false);
        stage.setScene(scene);
    }
    
    private void addListeners(Stage stage) {
        
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            this.getController().getSettingsDialogue().getStage().close();
            this.getController().getHelpDialogue().getStage().close();
            this.getController().getGuessDialogue().getStage().close();
        });
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
