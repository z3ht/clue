import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import mines.zinno.clue.constant.*;
import mines.zinno.clue.runner.ClueRunner;
import mines.zinno.clue.shape.character.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The {@link GuessTest} tests guess logic. It does not include multiple tests for incorrect guesses or accusations
 * because the code works by converting every item to a card so they are treated the same
 *
 * Satisfies {@link mines.zinno.clue.Assignments#C19A} requirements
 */
public class GuessTest extends ClueApplicationTest {

    private Player player;

    @Before
    public void setUp() throws Exception {
        // Click the guess button
        clickOn("#guess");

        // Save player
        this.player = (Player) this.clue.getCharacters().get(0);
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testOpenGuessDialogue_Success() {
        // Ensure guess dialogue is showing
        Assert.assertTrue(clue.getController().getGuessDialogue().isShowing());
    }

    @Test
    public void testGuessSend_Success() {
        roll();
        move();

        // Move to the study (optional)
        player.moveTo(clue.getController().getBoard().getItemFromCoordinate(clue.getLocation().getCenter()), true);

        // Get cards before guess
        List<Card> cards = new ArrayList<>(player.getCards());

        // Make guess
        player.guess(clue.getMurderer(), clue.getLocation(), clue.getWeapon());

        // Remove cards from before the guess from the cards after the guess
        player.getCards().removeIf(cards::contains);

        // Ensure no new cards have been added
        Assert.assertEquals(0, player.getCards().size());
    }

    @Test
    public void testGuessSend_RefuteSuspect() throws InterruptedException {
        testGuess_Refute((clue.getMurderer() == Suspect.COL_MUSTARD || player.getProvidedCards().contains(Suspect.COL_MUSTARD)) ? Suspect.MISS_SCARLETT : Suspect.COL_MUSTARD,
                clue.getLocation(), clue.getWeapon());
    }

    @Test
    public void testGuessSend_RefuteRoom() throws InterruptedException {
        testGuess_Refute(clue.getMurderer(),
                (clue.getLocation() == Room.STUDY || player.getProvidedCards().contains(Room.STUDY)) ? Room.BALL_ROOM : Room.STUDY, clue.getWeapon());
    }

    @Test
    public void testGuessSend_RefuteWeapon() throws InterruptedException {
        testGuess_Refute(clue.getMurderer(), clue.getLocation(),
                (clue.getWeapon() == Weapon.KNIFE || player.getProvidedCards().contains(Weapon.KNIFE)) ? Weapon.WRENCH : Weapon.KNIFE);
    }

    @Test
    public void testAccusationSend_Success() throws InterruptedException {
        roll();
        move();

        // Move to the exit (to make an accusation)
        player.moveTo(clue.getController().getBoard().getItemFromCoordinate(Room.EXIT.getCenter()), true);

        // Get cards before accusation
        List<Card> cards = new ArrayList<>(player.getCards());

        // Make accusation
        player.guess(clue.getMurderer(), clue.getLocation(), clue.getWeapon());

        // Remove
        player.getCards().removeIf(cards::contains);

        // Ensure no new cards
        Assert.assertEquals(0, player.getCards().size());

        // Check to make sure player still exists in game characters
        Assert.assertEquals(0, clue.getCharacters().indexOf(player));

        // Wait for runner thread to update then make sure game has stopped
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> Assert.assertFalse(clue.isPlaying()), (int) (ClueRunner.REFRESH_RATE*1.2), TimeUnit.MILLISECONDS);
        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        Assert.assertFalse(clue.isPlaying());
    }

    @Test
    public void testAccusationSend_RefuteSuspect() throws InterruptedException {
        testAccusation_Refute((clue.getMurderer() == Suspect.COL_MUSTARD || player.getProvidedCards().contains(Suspect.COL_MUSTARD)) ? Suspect.MISS_SCARLETT : Suspect.COL_MUSTARD,
                                clue.getLocation(), clue.getWeapon());
    }

    @Test
    public void testAccusationSend_RefuteRoom() throws InterruptedException {
        testAccusation_Refute(clue.getMurderer(), (clue.getLocation() == Room.STUDY || player.getProvidedCards().contains(Room.STUDY)) ? Room.BALL_ROOM : Room.STUDY,
                clue.getWeapon());
    }

    @Test
    public void testAccusationSend_RefuteWeapon() throws InterruptedException {
        testAccusation_Refute(clue.getMurderer(), clue.getLocation(),
                (clue.getWeapon() == Weapon.KNIFE || player.getProvidedCards().contains(Weapon.KNIFE)) ? Weapon.WRENCH : Weapon.KNIFE);
    }

    private void testGuess_Refute(Suspect suspect, Room room, Weapon weapon) throws InterruptedException {
        roll();
        move();

        // Move to the study (optional)
        player.moveTo(clue.getController().getBoard().getItemFromCoordinate(Room.STUDY.getCenter()), true);

        // Get cards before guess
        List<Card> cards = new ArrayList<>(player.getCards());

        // Make guess

        player.guess(suspect, room, weapon);

        // Remove cards from before the guess from the cards after the guess
        player.getCards().removeIf(cards::contains);
        Assert.assertEquals(1, player.getCards().size());

        // Ensure provided card is the guessed card
        Assert.assertTrue(player.getCards().get(0) == suspect || player.getCards().get(0) == room ||
                player.getCards().get(0) == weapon);
    }

    private void testAccusation_Refute(Suspect suspect, Room room, Weapon weapon) throws InterruptedException {
        roll();
        move();

        // Move to the exit (to make an accusation)
        player.moveTo(clue.getController().getBoard().getItemFromCoordinate(Room.EXIT.getCenter()), true);

        // Get cards before accusation
        List<Card> cards = new ArrayList<>(player.getCards());

        player.guess(suspect, room, weapon);

        // Remove
        player.getCards().removeIf(cards::contains);
        Assert.assertEquals(1, player.getCards().size());

        // Ensure provided card is the guessed card
        Assert.assertTrue(player.getCards().get(0) == suspect || player.getCards().get(0) == room ||
                                        player.getCards().get(0) == weapon);

        // Check that the player no longer exists in the game characters
        Assert.assertEquals(-1, clue.getCharacters().indexOf(player));

        // Wait for runner thread to update then make sure game is still going
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> Assert.assertTrue(clue.isPlaying()), (int) (ClueRunner.REFRESH_RATE*1.2), TimeUnit.MILLISECONDS);
        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        Assert.assertTrue(clue.isPlaying());
    }

}
