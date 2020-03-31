package mines.zinno.clue.shape.character.listener;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.constant.Alert;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.stage.dialogue.BasicInfoDialogue;

public class OnContinue implements EventHandler<MouseEvent> {

    private Runnable action;
    private Clue clue;

    public OnContinue(Runnable action, Clue clue) {
        this.action = action;
        this.clue = clue;
    }

    @Override
    public void handle(MouseEvent event) {
        
        Character curCharacter = clue.getCharacters().get(0);
        
        if(curCharacter.getTurn() == Turn.PRE_ROLL || curCharacter.getTurn() == Turn.POST_ROLL) {
            new BasicInfoDialogue(Alert.OUT_OF_TURN.getName(), Alert.OUT_OF_TURN.getText()).show();
            return;
        }
        
        action.run();
    }
}
