package mines.zinno.clue;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import mines.zinno.clue.constant.io.LogMessage;
import mines.zinno.clue.game.Clue;

import java.io.IOException;

public class Main extends Application {

    private final Clue clue = new Clue();

    /**
     * Called by JavaFX when the {@link Application} is ready to start
     *
     * @param stage Default stage
     */
    @Override
    public final void start(Stage stage) throws IOException {
        LogMessage.START.log();

        clue.populateStage(stage);

        LogMessage.STAGE_POPULATED.log();

        clue.addListeners(stage);
        stage.setOnShown((event) -> Platform.runLater(clue::startGame));

        LogMessage.LISTENERS_ADDED.log();

        stage.show();

        LogMessage.STAGE_SHOWN.log();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
