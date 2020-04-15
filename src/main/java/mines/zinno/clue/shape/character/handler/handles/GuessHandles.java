package mines.zinno.clue.shape.character.handler.handles;

import javafx.util.Pair;
import mines.zinno.clue.constant.Card;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.Suspect;
import mines.zinno.clue.constant.Weapon;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.layout.status.Info;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.handler.identifier.GuessHandle;
import mines.zinno.clue.shape.character.handler.identifier.RevealHandle;
import mines.zinno.clue.stage.dialogue.InfoDialogue;
import mines.zinno.clue.util.handler.basic.ExecuteHandler;
import mines.zinno.clue.util.handler.basic.InsertHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class GuessHandles {

    private final Clue game;

    Map<RevealContext, List<Pair<Boolean, Card[]>>> revealCardMap = new HashMap<>();

    private final static Turn GUESS_TURN = Turn.POST_GUESS;
    private final static String GUESS_FORMAT = "%s guessed the murderer was %s in the %s using the %s";

    private String guess;

    public GuessHandles(Clue game) {
        this.game = game;
    }

    @GuessHandle(type = ExecuteHandler.class)
    @RevealHandle(
            type = ExecuteHandler.class,
            id = RevealContext.ANY
    )
    public void execute(Character sender) {
        boolean shouldDisplay = false;
        InfoDialogue guessDialogue = new InfoDialogue("Clue");

        for (Iterator<RevealContext> i = revealCardMap.keySet().iterator(); i.hasNext();) {
            RevealContext revealContext = i.next();
            if (revealContext.getDisplayTurn() !=  sender.getTurn())
                continue;
            StringBuilder infoSB = new StringBuilder();
            for (String s : revealCardMap.get(revealContext))
                infoSB.append(s);
            guessDialogue.getController().getInfoPane().getInfos().add(new Info(infoSB.toString()));
            i.remove();
            shouldDisplay = true;
        }

        if (sender.getTurn() == GUESS_TURN && guess != null) {
            guessDialogue.getController().getInfoPane().getInfos().add(new Info(guess));
            guess = null;
            shouldDisplay = true;
        }

        guessDialogue.setSize();

        if (shouldDisplay) {
            guessDialogue.show();
            guessDialogue.toBack();
            game.getStage().toBack();
        }
    }

    @GuessHandle(type = InsertHandler.class)
    public void guess(Character sender, Suspect suspect, Room room, Weapon weapon) {
        guess = String.format(GUESS_FORMAT,
                (sender instanceof Player) ? "You" : sender.getCharacter().getName(),
                suspect.getName(),
                room.getName(),
                weapon.getName()
        );
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.PROVIDED
    )
    public void receiveFromStart(Character sender, Card card) {
        if(!(sender instanceof Player))
            return;

        List<String> cards = revealCardMap.get(RevealContext.PROVIDED)
        if()

    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.ON_GUESS
    )
    public void receiveFromGuess(Character sender, Character receiver, Card card) {

    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.LOST_GAME
    )
    public void receiveFromLoss(Character sender, Character receiver, Card card) {

    }



}
