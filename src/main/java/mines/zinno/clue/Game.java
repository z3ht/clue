package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;
import mines.zinno.clue.controllers.ClueController;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import mines.zinno.clue.layouts.status.Status;
import mines.zinno.clue.layouts.status.enums.Alert;
import mines.zinno.clue.runners.ClueRunner;
import mines.zinno.clue.shapes.character.Character;
import mines.zinno.clue.shapes.character.Computer;
import mines.zinno.clue.shapes.character.Player;
import mines.zinno.clue.stages.dialogue.StatusDialogue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Application {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private ClueController controller;
    private List<Character> characters;
    
    private boolean isPlaying;
    private int numMoves;

    public void start(Stage primaryStage) throws IOException {
        LOGGER.log(Level.INFO, "Clue Game Started");

        populateStage(primaryStage);

        LOGGER.log(Level.INFO, "Stage populated");
        
        addListeners(primaryStage);
        primaryStage.setOnShown(this::startGame);
        
        LOGGER.log(Level.INFO, "Listeners added");

        primaryStage.show();

        LOGGER.log(Level.INFO, "Stage shown");
    }

    private void startGame(WindowEvent event) {
        this.controller.getBoard().format();
        this.characters = new ArrayList<>();

        StatusDialogue statusDialogue = new StatusDialogue("Welcome");
        statusDialogue.getController().getStatusPane().getStatuses().add(new Status(Alert.WELCOME));
        statusDialogue.setSize();
        statusDialogue.show();
        
        int numComputers = Integer.parseInt(getController().getSettingsDialogue().getController().getComputers().getSelectedItem().getValue().toString());
        
        characters.add(new Player());
        for(int i = 0; i < numComputers; i++) {
            characters.add(new Computer());
        }
        
        this.isPlaying = true;
        new Thread(new ClueRunner(this)).start();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getNumMoves() {
        return numMoves;
    }

    public void setNumMoves(int doNumMoves) {
        this.numMoves = doNumMoves;
    }
    
    public void addMoves(int addAmount) {
        this.numMoves += addAmount;
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
            this.getController().getSettingsDialogue().close();
            this.getController().getHelpDialogue().close();
            this.getController().getGuessDialogue().close();
        });
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
