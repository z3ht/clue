import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mines.zinno.clue.constant.Action;
import mines.zinno.clue.constant.io.LogMessage;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.runner.ClueRunner;
import mines.zinno.clue.shape.character.Character;
import org.junit.Assert;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class ClueApplicationTest extends ApplicationTest {

    protected final Clue clue = new Clue();

    /**
     * Called by JavaFX when the {@link ApplicationTest} is ready to start
     *
     * @param stage Default stage
     */
    @Override
    public final void start(Stage stage) throws IOException {
        clue.populateStage(stage);
        clue.addListeners(stage);
        stage.setOnShown((event) -> Platform.runLater(clue::startGame));
        stage.show();
    }


    protected void roll() {
        clickOn("#roll");

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Platform.runLater(() ->{
            clue.getWelcomeDialogue().close();

            // Wait for runner thread to update
            executor.schedule(() -> {
                // Get info label text
                Label infoLabel = GuiTest.find("#info-label");

                // Ensure it equals the roll information
                Character curCharacter = clue.getCharacters().get(0);
                Assert.assertEquals(Action.ROLL_NUM.getText(curCharacter.getCharacter().getName(), curCharacter.getRollNum()), infoLabel.getText());
            }, (int) (ClueRunner.REFRESH_RATE*1.2), TimeUnit.MILLISECONDS);
        });
    }

    protected void move() {
        // Move to easiest position from start
        clickOn(clue.getController().getBoard().getItemFromCoordinate(16, 1));
    }

}
