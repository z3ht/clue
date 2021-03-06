package mines.zinno.clue.listener;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.constant.Alert;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.stage.dialogue.ShortDialogue;

/**
 * The {@link OnClickContinue} class is responsible for envoking {@link Runnable} actions when a continue
 * button is clicked
 */
public class OnClickContinue implements EventHandler<MouseEvent> {

    private Runnable action;
    private Clue clue;

    public OnClickContinue(Runnable action, Clue clue) {
        this.action = action;
        this.clue = clue;
    }

    /**
     * Called when a continue button is clicked and performs the class's provided action
     *
     * @param event {@link MouseEvent}
     */
    @Override
    public void handle(MouseEvent event) {

        Platform.runLater(() -> {
            Character curCharacter = clue.getCharacters().get(0);

            if(curCharacter.getTurn() == Turn.PRE_ROLL || curCharacter.getTurn() == Turn.POST_ROLL) {
                new ShortDialogue(Alert.OUT_OF_TURN.getName(), Alert.OUT_OF_TURN.getText()).show();
                return;
            }

            action.run();
        });
    }
}
