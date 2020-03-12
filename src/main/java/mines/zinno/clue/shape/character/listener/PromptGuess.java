package mines.zinno.clue.shape.character.listener;

import mines.zinno.clue.controller.ClueController;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.listener.OnMoveListener;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.place.RoomPlace;

/**
 * The {@link PromptGuess} class is an observer in the observer design pattern. It implements the
 * {@link OnMoveListener} interface and is responsible for opening the guess dialogue when a player
 * enters a room.
 */
public class PromptGuess implements OnMoveListener<Player> {
    
    private BoardGame<ClueController> game;

    public PromptGuess(BoardGame<ClueController> game) {
        this.game = game;
    }

    @Override
    public void update(Player p) {

        // Return if player is not in a room
        if(!(p.getCurPlace() instanceof RoomPlace)) {
            return;
        }

        game.getController().getGuessDialogue().show();
        
    }
}
