package mines.zinno.clue.shape.character;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.shape.character.constant.Result;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.handler.GuessHandler;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.stage.dialogue.ShortDialogue;


/**
 * The player class extends the {@link Character} class. It is used by all person controlled characters in Clue.
 */
public class Player extends Character {

    public Player(Clue game, GuessHandler guessHandler, Suspect suspect, Place startPlace) {
        super(game, guessHandler, suspect, startPlace);
    }

    @Override
    public void endTurn() {
        super.endTurn();
        delHighlightPosMoves();
    }

    @Override
    public int roll() {
        if(turn != Turn.PRE_ROLL) {
            new ShortDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("It is not your turn to roll")).show();
            return -1;
        }
        int returnVal = super.roll();
        highlightPosMoves();
        return returnVal;
    }

    @Override
    public void moveTo(Place place) {
        // If move is out of turn, throw out of turn exception
        if(this.turn != Turn.POST_ROLL && this.turn != Turn.POST_MOVE) {
            Platform.runLater(
                    () -> new ShortDialogue(
                            Alert.IMPOSSIBLE_MOVE.getName(),
                            Alert.IMPOSSIBLE_MOVE.getText("You must roll before moving")
                        ).show()
            );
            return;
        }

        // If move is impossible, send impossible move message
        if(!place.isHighlighted()) {
            Platform.runLater(
                    () -> new ShortDialogue(
                            Alert.IMPOSSIBLE_MOVE.getName(),
                            Alert.IMPOSSIBLE_MOVE.getText("You do not have enough moves")
                        ).show()
            );
            return;
        }

        delHighlightPosMoves();

        super.moveTo(place);

        displayMovesLeft();
        highlightPosMoves();
    }

    /**
     * Make a guess as a player
     */
    public void guess() {
        // Ensure player has moved if moves are available
        if(getTurn() != Turn.POST_MOVE) {
            Platform.runLater(() -> new ShortDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("You must move before making a guess if possible")).show());
            return;
        }

        // Ensure player is inside a room
        if(!(this.getCurPlace() instanceof RoomPlace)) {
            Platform.runLater(() -> new ShortDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("You must be inside a room to make a guess")).show());
            return;
        }

        // Send missing value alerts or store selected value
        Suspect suspect = game.getController().getGuessDialogue().getController().getSuspectMenu().getSelected().orElse(null);
        if(suspect == null) {
            Platform.runLater(() -> new ShortDialogue(Action.GUESS_TITLE, Alert.INCOMPLETE_GUESS.getText("suspect")).show());
            return;
        }
        Room room = game.getController().getGuessDialogue().getController().getRoomMenu().getSelected().orElse(null);
        if(room == null) {
            Platform.runLater(() -> new ShortDialogue(Action.GUESS_TITLE, Alert.INCOMPLETE_GUESS.getText("room")).show());
            return;
        }
        Weapon weapon = game.getController().getGuessDialogue().getController().getWeaponMenu().getSelected().orElse(null);
        if(weapon == null) {
            Platform.runLater(() -> new ShortDialogue(Action.GUESS_TITLE, Alert.INCOMPLETE_GUESS.getText("weapon")).show());
            return;
        }

        delHighlightPosMoves();

        super.guess(suspect, room, weapon);

        this.turn = Turn.POST_GUESS;
    }

    @Override
    public void onWin() {
        Platform.runLater(() -> new ShortDialogue(
                Result.PLAYER_WIN.getName(),
                Result.PLAYER_WIN.getText(game.getMurderer(), game.getLocation(), game.getWeapon())
        ).show());
    }

    @Override
    public void onLose() {
        Platform.runLater(() -> new ShortDialogue(
                Result.PLAYER_LOSE.getName(),
                Result.PLAYER_LOSE.getText(game.getMurderer(), game.getLocation(), game.getWeapon())
        ).show());
    }

    private void highlightPosMoves() {
        for(Place place : moveTree.retrieveAllValues()) {
            if(place.isOccupied())
                continue;
            if(place instanceof RoomPlace && ((RoomPlace) place).getRoom() == this.prevRoom)
                continue;

            place.addHighlight(Color.GREEN);
        }
    }

    private void delHighlightPosMoves() {
        for(Place posMove : moveTree.retrieveAllValues()) {
            if(posMove.isOccupied())
                continue;
            posMove.delHighlight();
        }
    }

    private void displayMovesLeft() {
        game.getController().getInfoLabel().setText(Action.MOVES_LEFT.getText(this.rollNum));
    }
}
