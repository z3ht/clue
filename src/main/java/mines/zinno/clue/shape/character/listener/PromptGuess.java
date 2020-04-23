package mines.zinno.clue.shape.character.listener;

import javafx.application.Platform;
import mines.zinno.clue.controller.ClueController;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.place.RoomPlace;

/**
 * The {@link PromptGuess} class is an observer in the observer design pattern. It implements the
 * {@link OnTurnListener} interface and is responsible for opening the guess dialogue when a player
 * enters a room.
 */
public class PromptGuess implements OnTurnListener<Character> {
    
    private final BoardGame<ClueController> game;

    public PromptGuess(BoardGame<ClueController> game) {
        this.game = game;
    }

    @Override
    public void update(Character character) {

        if(character.getTurn() != Turn.POST_MOVE)
            return;

        // Return if player is not in a room
        if(!(character.getCurPlace() instanceof RoomPlace)) {
            return;
        }

        Platform.runLater(() -> game.getController().getGuessDialogue().show());
        
    }
}
