package mines.zinno.clue.game;


import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mines.zinno.clue.controller.ClueController;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.controller.CustomBoardController;
import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.validator.IsRectangleValidator;
import mines.zinno.clue.layout.board.validator.NoBadDoorsValidator;
import mines.zinno.clue.runner.ClueRunner;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Computer;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.handler.GuessHandler;
import mines.zinno.clue.listener.OnClickContinue;
import mines.zinno.clue.shape.character.handler.handles.GuessHandles;
import mines.zinno.clue.shape.character.handler.identifier.GuessHandle;
import mines.zinno.clue.shape.character.handler.identifier.RevealHandle;
import mines.zinno.clue.shape.character.listener.OnExitEnter;
import mines.zinno.clue.shape.character.listener.PromptGuess;
import mines.zinno.clue.shape.character.listener.UpdateRoomGuess;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.StartPlace;
import mines.zinno.clue.stage.dialogue.BasicInfoDialogue;
import mines.zinno.clue.layout.board.validator.SubMaxSizeMapValidator;
import mines.zinno.clue.util.handler.Handler;


import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * The {@link Clue} class extends the {@link BoardGame} using the {@link ClueController} type. It holds all clue game
 * specific information.
 */
public class Clue extends BoardGame<ClueController> {

    private static final String TITLE = "Clue";
    private static final Dimension SIZE = new Dimension(1400, 900);
    private static final boolean IS_RESIZABLE = false;

    private Player player;
    private List<Character> characters;

    private Suspect murderer;
    private Room location;
    private Weapon weapon;
    
    @Override
    protected void populateStage(Stage stage) throws IOException {
        super.populateStage(stage);

        stage.setTitle(TITLE);
        stage.setResizable(IS_RESIZABLE);
    }
    
    @Override
    protected void addListeners(Stage stage) {
        // Close all windows when the main window is closed and stop the game
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            this.getController().getSettingsDialogue().close();
            this.getController().getHelpDialogue().close();
            this.getController().getGuessDialogue().close();
            setPlaying(false);
        });
        
        // Roll the dice
        this.getController().getRoll().addEventHandler(MouseEvent.MOUSE_CLICKED,
                (mouseEvent) -> player.roll()
        );

        // Skip to next player turn
        this.getController().getSkip().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new OnClickContinue(() -> this.setNumMoves(this.getCharacters().indexOf(player)), this)
        );

        // Continue to next character's turn
        this.getController().getNext().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new OnClickContinue(() -> this.addMoves(1), this)
        );

        // Add OnGuessConfirm listener
        this.getController().getGuessDialogue().getController().getGuess().addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                event -> this.getPlayer().guess()
        );
        
        // Add OnSettingsConfirm listener
        this.getController().getSettingsDialogue().getController().getBegin().addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                event -> {
                    this.setPlaying(false);
                    this.getController().getSuspectsSheet().getChildren().clear();
                    this.getController().getWeaponsSheet().getChildren().clear();
                    this.getController().getRoomsSheet().getChildren().clear();
                    this.startGame();
                }
        );
    }

    @Override
    protected void startGame() {
        drawBoard();
        createCharacters();
        createWelcomeStatus();
        beginGameThread();
        
        player.moveTo(this.getController().getBoard().getItemFromCoordinate(10, 7), true);
    }

    /**
     * Begin the game thread
     */
    private void beginGameThread() {
        setPlaying(true);
        new Thread(new ClueRunner(this)).start();
    }

    /**
     * Create welcome status dialogue
     */
    private void createWelcomeStatus() {
        new BasicInfoDialogue(Alert.WELCOME.getName(), Alert.WELCOME.getText()).show();
        this.getController().getInfoLabel().setText(Alert.WELCOME.getText());
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
        Place[][] grid = getController().getBoard().getGrid();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                if(!(grid[y][x] instanceof StartPlace))
                    continue;
                startPlaces.add((StartPlace) grid[y][x]);
            }
        }

        List<Room> rooms = new LinkedList<>(Arrays.asList(Room.values()));
        rooms.remove(Room.EXIT);
        List<Weapon> weapons = new LinkedList<>(Arrays.asList(Weapon.values()));
        List<Suspect> suspects = new LinkedList<>(Arrays.asList(Suspect.values()));

        // Generate a list of all characters
        List<Suspect> characters = new ArrayList<>(suspects);

        // Randomize rooms, weapons, and suspects
        Collections.shuffle(rooms);
        Collections.shuffle(weapons);
        Collections.shuffle(suspects);

        // Get chosen suspect from settings menu
        Suspect chosenCharacter = (this.getController().getSettingsDialogue().getController().getCharacter() == null) ?
                characters.get(0) :
                getController().getSettingsDialogue().getController().getCharacter().getSelected().orElse(characters.get(0));
        characters.remove(chosenCharacter);

        GuessHandler guessHandler = new GuessHandler(new GuessHandles(this));
        guessHandler.addIdentifyingAnnotation(
                RevealHandle.class,
                (senderData, revealAnnotation) ->
                        revealAnnotation.type().equals(senderData.handlerCaller) &&
                                (revealAnnotation.id().equals(senderData.providedID) ||
                                        Handler.ALL.equals(senderData.providedID) ||
                                        RevealContext.ANY.equals(senderData.providedID) ||
                                        RevealContext.ANY.equals(revealAnnotation.id()))
        );
        guessHandler.addIdentifyingAnnotation(
                GuessHandle.class,
                (senderData, revealAnnotation) -> revealAnnotation.type().equals(senderData.handlerCaller)
        );

        // Create player character
        this.player = new Player(
                this,
                guessHandler,
                chosenCharacter,
                startPlaces.get(0)
        );
        player.addTurnListener(new UpdateRoomGuess(this));
        player.addTurnListener(new OnExitEnter());
        player.addTurnListener(new PromptGuess(this));

        this.characters.add(this.player);

        // Create computer characters
        for(int i = 0; i < numComputers; i++) {
            this.characters.add(
                    new Computer(
                            this,
                            guessHandler,
                            characters.get(i),
                            startPlaces.get(i+1)
                    )
            );
        }

        // Assign the game's murderer, location, and weapon
        this.murderer = suspects.remove(0);
        this.location = rooms.remove(0);
        this.weapon = weapons.remove(0);

        // Assign characters with known suspects, weapons, and rooms
        int i = 0;
        while (suspects.size() > 0)
            this.characters.get(i++%this.characters.size())
                    .addProvidedCard(suspects.remove(0));
        while (weapons.size() > 0)
            this.characters.get(i++%this.characters.size()).addProvidedCard(weapons.remove(0));
        while (rooms.size() > 0)
            this.characters.get(i++%this.characters.size()).addProvidedCard(rooms.remove(0));
    }

    /**
     * Draw clue board
     */
    private void drawBoard() {
        // Add map validators
        getController().getBoard().addMapValidator(new SubMaxSizeMapValidator());
        getController().getBoard().addMapValidator(new IsRectangleValidator());
        getController().getBoard().addMapValidator(new NoBadDoorsValidator());
        
        // Check if custom board has any provided values
        CustomBoardController customBoardController = this.getController().getSettingsDialogue().getController().getBoardVersionDialogue().getController();
        try {
            getController().getBoard().setMap(customBoardController.getMapLocation().getText(), customBoardController.getImgLocation().getText());
        } catch (BadMapFormatException e) {
            new BasicInfoDialogue(BadMapFormatException.getName(), e.getMessage()).show();
        }
        
        // Draw the board
        getController().getBoard().draw();

        // Implement move functionality
        for(Place[] places : this.getController().getBoard().getGrid()) {
            for(Place place : places) {
                place.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> this.getPlayer().moveTo((Place) event.getTarget()));
            }
        }
    }

    @Override
    public Dimension getSize() {
        return SIZE;
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
     * Get the murderer
     */
    public Suspect getMurderer() {
        return murderer;
    }

    /**
     * Get the location
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Get the weapon
     */
    public Weapon getWeapon() {
        return weapon;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
