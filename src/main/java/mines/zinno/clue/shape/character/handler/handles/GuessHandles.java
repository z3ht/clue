package mines.zinno.clue.shape.character.handler.handles;

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

import java.util.*;

@SuppressWarnings("unused")
public class GuessHandles {

    private final Clue game;

    Map<Character, Map<RevealContext, List<String>>> alertsData = new HashMap<>();
    List<String> upcomingAlerts = new ArrayList<>();

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
        upcomingAlerts.add(guess);
        guess = null;

        for(Character character : alertsData.keySet()) {

            for (Iterator<RevealContext> i = alertsData.get(character).keySet().iterator(); i.hasNext();) {
                RevealContext revealContext = i.next();

                if(revealContext.getDisplayTurn() != sender.getTurn() ||
                        alertsData.get(character).get(revealContext) == null ||
                        alertsData.get(character).get(revealContext).size() == 0)
                    continue;

                upcomingAlerts.add(
                        String.format(
                                (game.getPlayer().equals(character)) ?
                                        revealContext.getPlayerHeader() : revealContext.getComputerHeader(),
                                sender.getCharacter().getName()
                        )
                );
                upcomingAlerts.addAll(alertsData.get(character).get(revealContext));
                upcomingAlerts.add("\n");
                i.remove();
            }
        }

        if (game.getNumMoves() > 1 || upcomingAlerts.size() == 0)
            return;

        InfoDialogue guessDialogue = new InfoDialogue("Clue");

        upcomingAlerts.stream()
                .filter(Objects::nonNull)
                .forEach(alert -> guessDialogue.getController().getInfoPane().getInfos().add(new Info(alert)));
        upcomingAlerts.clear();

        guessDialogue.setSize();
        guessDialogue.show();
        guessDialogue.toBack();
        game.getStage().toBack();
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
            id = RevealContext.ANY
    )
    public void crossOutPlayerCards(Character receiver, Card card) {
        if(!(receiver instanceof Player))
            return;

        if(card instanceof Room)
            game.getController().getRoomsSheet().crossOut(card.getId());
        if(card instanceof Weapon)
            game.getController().getWeaponsSheet().crossOut(card.getId());
        if(card instanceof Suspect)
            game.getController().getSuspectsSheet().crossOut(card.getId());
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.PROVIDED
    )
    public void receiveFromStart(Character receiver, Card card) {
        if(!(receiver instanceof Player))
            return;

        storeAlert(
                receiver,
                RevealContext.PROVIDED,
                String.format(RevealContext.PROVIDED.getPlayerBody(), card.getName())
        );
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.ON_GUESS
    )
    public void receiveFromGuess(Character receiver, Character sender, Card card) {
        String alert = (receiver instanceof Player) ?
                String.format(
                        RevealContext.ON_GUESS.getPlayerBody(),
                        sender.getCharacter().getName(),
                        card.getName()) :
                String.format(
                        RevealContext.ON_GUESS.getComputerBody(),
                        sender.getCharacter().getName(),
                        receiver.getCharacter().getName());
        storeAlert(receiver, RevealContext.ON_GUESS, alert);
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.LOST_GAME
    )
    public void receiveFromLoss(Character receiver, Character sender, Card card) {
        if(!(receiver instanceof Player))
            return;

        storeAlert(
                receiver,
                RevealContext.PROVIDED,
                String.format(RevealContext.PROVIDED.getPlayerBody(), card.getName())
        );
    }

    private void storeAlert(Character receiver, RevealContext context, String... alerts) {
        List<String> data = alertsData.get(receiver).get(context);
        if(data == null)
            data = new ArrayList<>();
        data.addAll(Arrays.asList(alerts));
    }

}
