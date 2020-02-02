package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import mines.zinno.clue.layouts.status.Status;
import mines.zinno.clue.layouts.status.enums.Alert;
import mines.zinno.clue.runners.ClueRunner;
import mines.zinno.clue.shapes.character.Character;
import mines.zinno.clue.shapes.character.Computer;
import mines.zinno.clue.shapes.character.Player;
import mines.zinno.clue.shapes.place.Place;
import mines.zinno.clue.shapes.place.StartPlace;
import mines.zinno.clue.stages.dialogue.StatusDialogue;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        
        int numComputers = Integer.parseInt(getController().getSettingsDialogue().getController().getComputers().getSelected().orElse(Digit.FIVE).toString());
        List<StartPlace> startPlaces = new ArrayList<>();
        Place[][] grid = this.controller.getBoard().getGrid();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                if(!(grid[y][x] instanceof StartPlace))
                    continue;
                startPlaces.add((StartPlace) grid[y][x]);
            }
        }
        List<Room> rooms = Arrays.asList(Room.values());
        List<Weapon> weapons = Arrays.asList(Weapon.values());
        List<Suspect> suspects = Arrays.asList(Suspect.values());
        List<Suspect> players = new ArrayList<>(suspects);

        Collections.shuffle(rooms);
        Collections.shuffle(weapons);
        Collections.shuffle(suspects);
        
        Suspect chosenSuspect = (this.getController().getSettingsDialogue().getController().getCharacter() == null) ?
                suspects.get(0) :
                this.controller.getSettingsDialogue().getController().getCharacter().getSelected()
                        .orElse(suspects.get(0));
        
        players.remove(chosenSuspect);
        
        characters.add(
                new Player(
                        this,
                        chosenSuspect, 
                        startPlaces.get(0),
                        rooms.subList(0, rooms.size()/(numComputers+1)),
                        weapons.subList(0, weapons.size()/(numComputers+1)),
                        suspects.subList(0, suspects.size()/(numComputers + 1))
                )
        );
        for(int i = 0; i < numComputers; i++) {
            characters.add(
                    new Computer(
                            this,
                            players.get(i),
                            startPlaces.get(i+1),
                            rooms.subList((i+1)*(rooms.size()/(numComputers+1)), (i+2)*(rooms.size()/(numComputers+1))),
                            weapons.subList((i+1)*(weapons.size()/(numComputers+1)), (i+2)*(weapons.size()/(numComputers+1))),
                            suspects.subList((i+1)*(suspects.size()/(numComputers+1)), (i+2)*(suspects.size()/(numComputers+1)))
                    )
            );
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
