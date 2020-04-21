package mines.zinno.clue.shape.character.listener;

import javafx.application.Platform;
import mines.zinno.clue.constant.Action;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.character.constant.Turn;

public class OnRoll implements OnTurnListener<Character> {

    private Clue game;

    public OnRoll(Clue game) {
        this.game = game;
    }

    @Override
    public void update(Character character) {
        if(character.getTurn() != Turn.POST_ROLL)
            return;

        int rollNum = character.getRollNum();

        Platform.runLater(() -> game.getController().getInfoLabel().setText(
                Action.ROLL_NUM.getText((character instanceof Player) ? "You" : character.getCharacter().getName(),
                        rollNum
                )
        ));


    }

}
