package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;
import mines.zinno.clue.controllers.ClueController;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import mines.zinno.clue.enums.FXMLURL;
import mines.zinno.clue.enums.LogMessage;
import mines.zinno.clue.layouts.status.Status;
import mines.zinno.clue.layouts.status.enums.Alert;
import mines.zinno.clue.shapes.character.Character;
import mines.zinno.clue.stages.dialogue.StatusDialogue;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    
    private static final Dimension SIZE = new Dimension(1400, 900);
    private static final String TITLE = "Clue";
    private static final boolean IS_RESIZABLE = false;
    
    private ClueController controller;
    private List<Character> characters;
    
    private boolean isPlaying;
    private int numMoves;

    public void start(Stage primaryStage) throws IOException {
        LogMessage.START.log();

        populateStage(primaryStage);

        LogMessage.STAGE_POPULATED.log();
        
        addListeners(primaryStage);
        primaryStage.setOnShown(this::startGame);
        
        LogMessage.LISTENERS_ADDED.log();

        primaryStage.show();

        LogMessage.STAGE_SHOWN.log();
    }

    private void startGame(WindowEvent event) {
        this.controller.getBoard().format();
        this.characters = new ArrayList<>();

        StatusDialogue statusDialogue = new StatusDialogue(Alert.WELCOME.getName());
        statusDialogue.getController().getStatusPane().getStatuses().add(new Status(Alert.WELCOME));
        statusDialogue.setSize();
        statusDialogue.show();
        
        int numComputers = Integer.parseInt(getController().getSettingsDialogue().getController().getComputers().getSelectedItem().getValue().toString());
        
//        characters.add(new Player());
//        for(int i = 0; i < numComputers; i++) {
//            characters.add(new Computer());
//        }
//        
//        this.isPlaying = true;
//        new Thread(new ClueRunner(this)).start();
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

    public ClueController getController() {
        return controller;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    private void populateStage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLURL.CLUE.getUrl());

        Parent root = fxmlLoader.load();

        this.controller = fxmlLoader.getController();

        Scene scene = new Scene(root, SIZE.getWidth(), SIZE.getHeight());

        stage.setTitle(TITLE);
        stage.setResizable(IS_RESIZABLE);
        
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
