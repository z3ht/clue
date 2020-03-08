package mines.zinno.clue.listeners;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.Game;
import mines.zinno.clue.controllers.GuessController;
import mines.zinno.clue.layouts.status.Status;
import mines.zinno.clue.layouts.status.enums.Alert;
import mines.zinno.clue.shapes.character.Player;
import mines.zinno.clue.shapes.character.enums.Turn;
import mines.zinno.clue.stages.dialogue.StatusDialogue;

public class OnGuessConfirm implements EventHandler<MouseEvent> {

    private Game game;

    private Player player;
    private GuessController guessController;

    public OnGuessConfirm(Game game) {
        this.game = game;

        this.player = game.getPlayer();
        this.guessController = game.getController().getGuessDialogue().getController();
    }

    @Override
    public void handle(MouseEvent event) {

        // Ensure player has moved if moves are available
        if(player.getTurn() != Turn.POST_MOVE && !player.calcPosMoves().isEmpty()) {
            StatusDialogue statusDialogue = new StatusDialogue(Alert.WELCOME.getName());
            statusDialogue.getController().getStatusPane().getStatuses().add(new Status(Alert.OUT_OF_TURN));
            statusDialogue.setSize();
            statusDialogue.show();
        }

        


    }

}
