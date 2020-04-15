package mines.zinno.clue.shape.character;

import javafx.scene.paint.Color;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.shape.character.constant.Result;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.handler.GuessHandler;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.stage.dialogue.BasicInfoDialogue;


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
            new BasicInfoDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("It is not your turn to roll")).show();
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
            new BasicInfoDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("You must roll before moving")).show();
            return;
        }

        // If move is impossible, send impossible move message
        if(!posMoves.contains(place)) {
            new BasicInfoDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("You do not have enough moves")).show();
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
        if(!(getTurn() == Turn.POST_MOVE || (getTurn() == Turn.POST_ROLL && calcPosMoves().isEmpty()))) {
            new BasicInfoDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("You must move before making a guess if possible")).show();
            return;
        }

        // Ensure player is inside a room
        if(!(this.getCurPlace() instanceof RoomPlace)) {
            new BasicInfoDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("You must be inside a room to make a guess")).show();
            return;
        }

        // Send missing value alerts or store selected value
        Suspect suspect = game.getController().getGuessDialogue().getController().getSuspectMenu().getSelected().orElse(null);
        if(suspect == null) {
            new BasicInfoDialogue(Action.GUESS_TITLE, Alert.INCOMPLETE_GUESS.getText("suspect")).show();
            return;
        }
        Room room = game.getController().getGuessDialogue().getController().getRoomMenu().getSelected().orElse(null);
        if(room == null) {
            new BasicInfoDialogue(Action.GUESS_TITLE, Alert.INCOMPLETE_GUESS.getText("room")).show();
            return;
        }
        Weapon weapon = game.getController().getGuessDialogue().getController().getWeaponMenu().getSelected().orElse(null);
        if(weapon == null) {
            new BasicInfoDialogue(Action.GUESS_TITLE, Alert.INCOMPLETE_GUESS.getText("weapon")).show();
            return;
        }
        
        delHighlightPosMoves();
        this.rollNum = 0;
        this.posMoves = null;
        
        super.guess(suspect, room, weapon);
    }


    @Override
    public String receiveCard(Character sender, Card card, RevealContext revealContext) {

        //TODO move to handle
        if(card instanceof Room)
            game.getController().getRoomsSheet().crossOut(card.getId());
        if(card instanceof Weapon)
            game.getController().getWeaponsSheet().crossOut(card.getId());
        if(card instanceof Suspect)
            game.getController().getSuspectsSheet().crossOut(card.getId());

    }
    
    @Override
    public void onWin() {
        new BasicInfoDialogue(Result.PLAYER_WIN.getName(), Result.PLAYER_WIN.getText(game.getMurderer())).show();
    }

    @Override
    public void onLose() {
        new BasicInfoDialogue(Result.PLAYER_LOSE.getName(), Result.PLAYER_LOSE.getText(game.getMurderer())).show();
    }
    
    private void highlightPosMoves() {
        for(Place place : posMoves) {
            place.addHighlight(Color.GREEN);
        }
    }
    
    private void delHighlightPosMoves() {
        for(Place posMove : posMoves) {
            posMove.delHighlight();
        }
    }
    
    private void displayMovesLeft() {
        game.getController().getInfoLabel().setText(Action.MOVES_LEFT.getText(this.rollNum));
    }
}
