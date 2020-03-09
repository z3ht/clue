package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import mines.zinno.clue.controllers.ClueController;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;
import mines.zinno.clue.enums.Digit;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.enums.io.FXMLURL;
import mines.zinno.clue.enums.io.LogMessage;
import mines.zinno.clue.layouts.status.Info;
import mines.zinno.clue.layouts.status.enums.Alert;
import mines.zinno.clue.listeners.OnGuessConfirm;
import mines.zinno.clue.runners.ClueRunner;
import mines.zinno.clue.shapes.character.Character;
import mines.zinno.clue.shapes.character.Computer;
import mines.zinno.clue.shapes.character.Player;
import mines.zinno.clue.shapes.place.Place;
import mines.zinno.clue.shapes.place.StartPlace;
import mines.zinno.clue.stages.dialogue.InfoDialogue;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The {@link Game} extends JavaFX's {@link Application} class and holds all essential Clue information.
 */
public class Game extends Application {
    
    private static final Dimension SIZE = new Dimension(1400, 900);
    private static final String TITLE = "Clue";
    private static final boolean IS_RESIZABLE = false;
    
    private ClueController controller;

    private Player player;
    private List<Character> characters;
    
    private boolean isPlaying;
    private int numMoves;

    /**
     * Called by JavaFX when the {@link Application} is ready to start
     * 
     * @param primaryStage Default stage
     */
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

    /**
     * Called when the stage is shown. Responsible for beginning the game.
     */
    private void startGame(WindowEvent event) {
        formatBoard();
        createCharacters();
        createWelcomeStatus();
        beginGameThread();
    }

    /**
     * Begin the game thread
     */
    private void beginGameThread() {
        this.isPlaying = true;
        new Thread(new ClueRunner(this)).start();
    }

    /**
     * Create welcome status dialogue
     */
    private void createWelcomeStatus() {
        InfoDialogue infoDialogue = new InfoDialogue(Alert.WELCOME.getName());
        infoDialogue.getController().getInfoPane().getInfos().add(new Info(Alert.WELCOME));
        infoDialogue.setSize();
        infoDialogue.show();
    }

    /**
     * Create clue characters
     */
    private void createCharacters() {
        this.characters = new ArrayList<>();

        // Get the number of computers set in the settings menu
        int numComputers = Integer.parseInt(getController().getSettingsDialogue().getController().getComputers().getSelected().orElse(Digit.FIVE).toString());
        
        // Get the start places
        List<StartPlace> startPlaces = new ArrayList<>();
        Place[][] grid = this.controller.getBoard().getGrid();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                if(!(grid[y][x] instanceof StartPlace))
                    continue;
                startPlaces.add((StartPlace) grid[y][x]);
            }
        }
        
        // Randomize rooms, weapons, and suspects
        List<Room> rooms = Arrays.asList(Room.values());
        List<Weapon> weapons = Arrays.asList(Weapon.values());
        List<Suspect> suspects = Arrays.asList(Suspect.values());
        
        // Generate a list of all characters
        List<Suspect> characters = new ArrayList<>(suspects);
        
        Collections.shuffle(rooms);
        Collections.shuffle(weapons);
        Collections.shuffle(suspects);
        
        // Get chosen suspect from settings menu
        Suspect chosenSuspect = (this.getController().getSettingsDialogue().getController().getCharacter() == null) ?
                suspects.get(0) :
                this.controller.getSettingsDialogue().getController().getCharacter().getSelected()
                        .orElse(suspects.get(0));
        characters.remove(chosenSuspect);
        
        // Create player character
        this.player = new Player(
                this,
                chosenSuspect,
                startPlaces.get(0),
                rooms.subList(0, rooms.size()/(numComputers+1)),
                weapons.subList(0, weapons.size()/(numComputers+1)),
                suspects.subList(0, suspects.size()/(numComputers + 1))
        );
        this.characters.add(
                this.player
        );
        
        // Create computer characters
        for(int i = 0; i < numComputers; i++) {
            this.characters.add(
                    new Computer(
                            this,
                            characters.get(i),
                            startPlaces.get(i+1),
                            rooms.subList((i+1)*(rooms.size()/(numComputers+1)), (i+2)*(rooms.size()/(numComputers+1))),
                            weapons.subList((i+1)*(weapons.size()/(numComputers+1)), (i+2)*(weapons.size()/(numComputers+1))),
                            suspects.subList((i+1)*(suspects.size()/(numComputers+1)), (i+2)*(suspects.size()/(numComputers+1)))
                    )
            );
        }
        
    }

    /**
     * Format clue board
     */
    private void formatBoard() {
        this.controller.getBoard().format();
    }

    /**
     * Populate clue stage
     */
    private void populateStage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLURL.CLUE.getUrl());

        Parent root = fxmlLoader.load();

        this.controller = fxmlLoader.getController();

        Scene scene = new Scene(root, SIZE.getWidth(), SIZE.getHeight());

        stage.setTitle(TITLE);
        stage.setResizable(IS_RESIZABLE);

        stage.setScene(scene);
    }

    /**
     * Add clue listeners
     */
    private void addListeners(Stage stage) {
        // Close all windows when the main window is closed and stop the game
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            this.getController().getSettingsDialogue().close();
            this.getController().getHelpDialogue().close();
            this.getController().getGuessDialogue().close();
            this.isPlaying = false;
        });

        // Roll the dice
        this.getController().getRoll().addEventHandler(MouseEvent.MOUSE_CLICKED,
                (mouseEvent) -> player.roll()
        );

        // Skip to next player turn
        this.getController().getSkip().addEventHandler(MouseEvent.MOUSE_CLICKED,
                (mouseEvent) -> this.setNumMoves(this.getCharacters().indexOf(player))
        );

        // Continue to next character's turn
        this.getController().getNext().addEventHandler(MouseEvent.MOUSE_CLICKED,
                (mouseEvent) -> this.addMoves(1)
        );

        // Add OnGuessConfirm listener
        this.getController().getGuessDialogue().getController().getGuess().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new OnGuessConfirm(this)
        );
    }

    /**
     * {@link Boolean} denoting whether or not the game is active
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

    /**
     * Get the game controller
     * 
     * @return {@link ClueController}
     */
    public ClueController getController() {
        return controller;
    }

    /**
     * Get the game characters
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * Get the game player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
