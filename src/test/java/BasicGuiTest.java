import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import mines.zinno.clue.constant.Alert;
import mines.zinno.clue.shape.character.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;

/**
 * The {@link BasicGuiTest} tests basic {@link mines.zinno.clue.game.Clue} gui functionality.
 *
 * Satisfies the {@link mines.zinno.clue.Assignments#C18A} requirements
 */
public class BasicGuiTest extends ClueApplicationTest {

    @Before
    public void setUp() throws Exception {
        // Nothing needed here yet
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testCreateCards_Success() {
        // Ensure accusation cards have been created and pulled out from the deck
        Assert.assertNotNull(clue.getLocation());
        Assert.assertNotNull(clue.getMurderer());
        Assert.assertNotNull(clue.getWeapon());
    }

    @Test
    public void testDistributeCards_Success() {
        Assert.assertNotNull(clue.getCharacters());

        // Ensure first person is the player
        Assert.assertTrue(clue.getCharacters().get(0) instanceof Player);

        // Ensure cards are distributed evenly among players
        int numCards = clue.getCharacters().get(0).getProvidedCards().size();
        Assert.assertTrue(clue.getCharacters().stream().allMatch((character ->
                Math.abs(character.getCards().size() - numCards) <= 1)));
    }

    @Test
    public void testRoll_Success() {
        roll();
    }

    @Test
    public void testMove_Success() {
        roll();
        move();
    }



}