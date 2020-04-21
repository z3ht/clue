package mines.zinno.clue.shape.character.listener;

import javafx.application.Platform;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.stage.dialogue.ShortDialogue;

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
        Platform.runLater(() -> game.getController().getInfoLabel().setText("It is now your turn"));

        Platform.runLater(() -> new ShortDialogue("Clue", "It is now your turn").show());
    }

}
