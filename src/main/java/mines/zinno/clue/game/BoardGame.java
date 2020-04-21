package mines.zinno.clue.game;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import mines.zinno.clue.controller.ClueController;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import mines.zinno.clue.controller.GameController;
import mines.zinno.clue.constant.io.FXMLURL;
import mines.zinno.clue.constant.io.LogMessage;
import mines.zinno.clue.shape.character.Character;

import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * The {@link BoardGame} extends JavaFX's {@link Application} class and holds all essential BoardGame information.
 */
public abstract class BoardGame<T extends GameController> extends Application {

    private Stage stage;
    private T controller;

    private boolean isPlaying;
    private int numMoves;

    /**
     * Called by JavaFX when the {@link Application} is ready to start
     * 
     * @param primaryStage Default stage
     */
    public final void start(Stage primaryStage) throws IOException {
        LogMessage.START.log();

        this.stage = primaryStage;

        populateStage(this.stage);

        LogMessage.STAGE_POPULATED.log();
        
        addListeners(stage);
        stage.setOnShown((event) -> Platform.runLater(this::startGame));
        
        LogMessage.LISTENERS_ADDED.log();

        stage.show();

        LogMessage.STAGE_SHOWN.log();
    }

    /**
     * Called when the stage is shown. Begins the game.
     */
    protected abstract void startGame();

    /**
     * Populate clue stage
     */
    protected void populateStage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLURL.CLUE.getUrl());

        Parent root = fxmlLoader.load();

        this.controller = fxmlLoader.getController();

        Scene scene = new Scene(root, getSize().getWidth(), getSize().getHeight());

        stage.setScene(scene);
    }

    /**
     * Add clue listeners
     */
    protected abstract void addListeners(Stage stage);
    
    /**
     * Get the game controller
     * 
     * @return {@link ClueController}
     */
    public T getController() {
        return controller;
    }

    /**
     * Get the stage
     *
     * @return {@link Stage}
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Get {@link List}<{@link Character}> of game characters
     */
    public abstract List<Character> getCharacters();

    /**
     * Get the {@link Dimension}s of the board game application
     */
    public abstract Dimension getSize();

    /**
     * Get {@link Boolean} denoting whether or not the game is active
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Set the game active or not
     */
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    /**
     * Get number of moves until the game pauses
     */
    public int getNumMoves() {
        return numMoves;
    }

    /**
     * Set the number of moves until the game pauses
     */
    public void setNumMoves(int doNumMoves) {
        this.numMoves = doNumMoves;
    }

    /**
     * Add moves until the game pauses
     */
    public void addMoves(int addAmount) {
        this.numMoves += addAmount;
    }

    public abstract boolean hasStarted();
}
