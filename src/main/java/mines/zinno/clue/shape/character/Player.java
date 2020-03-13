package mines.zinno.clue.shape.character;

import javafx.scene.paint.Color;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.listener.OnMoveListener;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.stage.dialogue.BasicInfoDialogue;

import java.util.ArrayList;
import java.util.List;

/**
 * The player class extends the {@link Character} class. It is used by all person controlled characters in Clue.
 */
public class Player extends Character {
    
    List<OnMoveListener<Player>> moveListeners = new ArrayList<>();
    
    private Clue game;
    
    public Player(Clue game, Suspect suspect, Place startPlace) {
        super(game, suspect, startPlace);
        this.game = game;
    }

    @Override
    public int roll() {
        if(turn != Turn.PRE_ROLL) {
            new BasicInfoDialogue(Alert.IMPOSSIBLE_MOVE.getName(), Alert.IMPOSSIBLE_MOVE.getText("It is not your turn to roll")).show();
            return -1;
        }
        int returnVal = super.roll();
        game.getController().getInfoLabel().setText(Action.ROLL_NUM.getText(this.getRollNum()));
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
        
        updateMoveListeners();
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
        
        Boolean isFound = super.guess(suspect, room, weapon);
        
        // Nothing found
        if(isFound != null && !isFound) {
            new BasicInfoDialogue(Action.GUESS_TITLE, Action.NOTHING_FOUND.getText()).show();
        }
    }


    @Override
    public void receiveCard(Character sender, Card card, RevealContext revealContext) {
        if(card instanceof Room)
            game.getController().getRoomsSheet().crossOut(card.getId());
        if(card instanceof Weapon)
            game.getController().getWeaponsSheet().crossOut(card.getId());
        if(card instanceof Suspect)
            game.getController().getSuspectsSheet().crossOut(card.getId());

        switch (revealContext) {
            case ON_GUESS:
                new BasicInfoDialogue(Action.GUESS_TITLE, Action.CLUE.getText(sender.getCharacter().getName(), card.getName())).show();
                break;
            case LOST_GAME:
                break;
            case PROVIDED:
                break;
        }
            
        
    }
    
    @Override
    public void onWin() {
        
    }

    @Override
    public void onLose() {

    }

    /**
     * Add an observer updated when a player moves
     */
    public void addMoveListener(OnMoveListener<Player> moveListener) {
        this.moveListeners.add(moveListener);
    }

    /**
     * Update move listeners
     */
    protected void updateMoveListeners() {
        for(OnMoveListener<Player> moveListener : this.moveListeners) {
            moveListener.update(this);
        }
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
