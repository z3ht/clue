package mines.zinno.clue.shape.character.listener;

import javafx.application.Platform;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.stage.dialogue.ShortDialogue;

/**
 * The {@link OnNewTurn} class is called when a player's turn begins
 */
public class OnNewTurn implements OnTurnListener<Character> {


    private final static String YOUR_TURN = "It is now your turn!";

    private final Clue game;

    public OnNewTurn(Clue game) {
        this.game = game;
    }

    @Override
    public void update(Character character) {
        if(character.getTurn() != Turn.PRE_ROLL)
            return;
        Platform.runLater(() -> game.getController().getInfoLabel().setText(YOUR_TURN));

        Platform.runLater(() -> new ShortDialogue("", YOUR_TURN).show());
    }

}
