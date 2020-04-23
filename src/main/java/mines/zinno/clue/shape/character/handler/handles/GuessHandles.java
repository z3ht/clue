package mines.zinno.clue.shape.character.handler.handles;

import javafx.application.Platform;
import mines.zinno.clue.constant.Card;
import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.Suspect;
import mines.zinno.clue.constant.Weapon;
import mines.zinno.clue.constant.io.LogMessage;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Player;
import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.character.handler.identifier.GuessHandle;
import mines.zinno.clue.shape.character.handler.identifier.RevealHandle;
import mines.zinno.clue.stage.dialogue.ScrollableDialogue;
import mines.zinno.clue.util.handler.basic.BasicHandle;
import mines.zinno.clue.util.handler.basic.ExecuteHandler;
import mines.zinno.clue.util.handler.basic.InsertHandler;

import java.util.*;
import java.util.logging.Level;

/**
 * The {@link GuessHandles} class holds all handles used when Clue characters are making guesses. These methods are
 * called reflectively from the {@link mines.zinno.clue.util.handler.Handler} utility using their annotations.
 */
@SuppressWarnings({"unused"})
public class GuessHandles {

    private final Clue game;

    Map<Character, Map<RevealContext, List<String>>> alertsData = new HashMap<>();
    List<String> upcomingAlerts = new ArrayList<>();

    private final static Turn GUESS_TURN = Turn.POST_GUESS;
    private final static String GUESS_FORMAT = "%s %s the murderer %s %s in the %s using the %s";

    private List<String> guesses = new ArrayList<>();

    public GuessHandles(Clue game) {
        this.game = game;
    }

    @GuessHandle(type = ExecuteHandler.class)
    @RevealHandle(
            type = ExecuteHandler.class,
            id = RevealContext.ANY
    )
    public void execute(Character sender) {
        sync(() -> {
            guesses.forEach((guess) -> {
                upcomingAlerts.add(guess);
                upcomingAlerts.add("\n");
            });
            guesses.clear();

            for(Character character : alertsData.keySet()) {

                for (Iterator<RevealContext> i = alertsData.get(character).keySet().iterator(); i.hasNext();) {

                    RevealContext revealContext = i.next();

                    if(alertsData.get(character).get(revealContext) == null ||
                            alertsData.get(character).get(revealContext).size() == 0)
                        continue;

                    String header = (game.getPlayer().equals(character)) ?
                            revealContext.getPlayerHeader() : revealContext.getComputerHeader();

                    if(header != null) {
                        upcomingAlerts.add(String.format(header, sender.getCharacter().getName()));
                    }

                    upcomingAlerts.addAll(alertsData.get(character).get(revealContext));

                    upcomingAlerts.add("\n");

                    i.remove();
                }
            }

            upcomingAlerts.removeIf(Objects::isNull);
            if (game.getNumMoves() > 0 || upcomingAlerts.size() == 0)
                return;

            List<String> curAlerts = new ArrayList<>(upcomingAlerts);
            upcomingAlerts.clear();

            Platform.runLater(() -> {
                ScrollableDialogue guessDialogue = new ScrollableDialogue("Status");

                curAlerts.forEach(
                        alert -> guessDialogue.getController().getInfoLabel().setText(
                                guessDialogue.getController().getInfoLabel().getText() + alert + "\n"
                        )
                );

                guessDialogue.show();
            });

            LogMessage.log(Level.INFO, "Alerts displayed");
        });
    }

    @GuessHandle(type = InsertHandler.class)
    public void guess(Character sender, Suspect suspect, boolean isAccusation, Room room, Weapon weapon) {
        sync(() -> {
            String guess = String.format(GUESS_FORMAT,
                    (sender instanceof Player) ? "You" : sender.getCharacter().getName(),
                    (isAccusation) ? "accused" : "guessed",
                    (isAccusation) ? "of being" : "was",
                    suspect.getName(),
                    room.getName(),
                    weapon.getName()
            );

            guesses.add(guess);

            LogMessage.log(Level.INFO, guess);
        });
    }

    @GuessHandle(
            type = InsertHandler.class
    )
    public void teleportGuessedCharacter(Character sender, Suspect suspect, boolean isAccusation) {
        if(isAccusation)
            return;

        game.getCharacters().stream()
                .filter(character -> character.getCharacter() == suspect)
                .forEach(character -> {
                    character.moveTo(sender.getCurPlace(), true);
                });
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.ANY
    )
    public void crossOutPlayerCards(Character receiver, Card card) {
        if(!(receiver instanceof Player))
            return;

        Platform.runLater(() -> {
            if(card instanceof Room)
                game.getController().getRoomsSheet().crossOut(card.getId());
            if(card instanceof Weapon)
                game.getController().getWeaponsSheet().crossOut(card.getId());
            if(card instanceof Suspect)
                game.getController().getSuspectsSheet().crossOut(card.getId());
        });
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.ANY
    )
    public void addToCards(Character receiver, Card card) {
        receiver.getCards().add(card);
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.PROVIDED
    )
    public void receiveFromStart(Character receiver, Card card) {
        sync(() -> {
            if(!(receiver instanceof Player))
                return;

            storeAlert(
                    receiver,
                    RevealContext.PROVIDED,
                    String.format(RevealContext.PROVIDED.getPlayerBody(), card.getName())
            );
        });
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.ON_GUESS
    )
    public void receiveFromGuess(Character sender, Character receiver, Card card) {
        sync(() -> {
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

            LogMessage.log(Level.INFO, String.format("%s gave %s the %s card", sender.getCharacter().getName(), receiver.getCharacter().getName(), card.getName()));
        });
    }

    @BasicHandle(
            type = InsertHandler.class,
            id = "nothing"
    )
    public void noDisapproval(Character receiver) {
        sync(() -> {
            storeAlert(receiver, RevealContext.ON_GUESS,
                    String.format("Nobody could disprove %s's guess", receiver.getCharacter().getName()));
        });
    }

    @RevealHandle(
            type = InsertHandler.class,
            id = RevealContext.LOST_GAME
    )
    public void receiveFromLoss(Character receiver, Card card) {
        sync(() -> {
            if(!(receiver instanceof Player))
                return;

            storeAlert(
                    receiver,
                    RevealContext.LOST_GAME,
                    String.format(RevealContext.LOST_GAME.getPlayerBody(), card.getName())
            );
        });
    }

    private void storeAlert(Character receiver, RevealContext context, String... alerts) {
        alertsData.computeIfAbsent(receiver, k -> new HashMap<>());
        alertsData.get(receiver).computeIfAbsent(context, k -> new ArrayList<>());
        alertsData.get(receiver).get(context).addAll(Arrays.asList(alerts));
    }

    /**
     * This is my mega ghetto synchronization tool. It still does not guarantee order but will prevent concurrent
     * modification exceptions
     */
    private synchronized void sync(Runnable throwableRunnable) {
        throwableRunnable.run();
    }

}
