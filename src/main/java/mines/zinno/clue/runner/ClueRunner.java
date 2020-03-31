package mines.zinno.clue.runner;

import javafx.application.Platform;
import mines.zinno.clue.controller.ClueController;
import mines.zinno.clue.game.Clue;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.constant.Turn;
import mines.zinno.clue.shape.place.Place;

import java.util.Collections;
import java.util.function.Supplier;

/**
 * The {@link ClueRunner} class implements the {@link Runnable} interface. It holds game code that will be run on a
 * separate thread than the GUI.
 */
public class ClueRunner implements Runnable {

    private static final int REFRESH_RATE = 100;
    
    private BoardGame<ClueController> boardGame;
    
    public ClueRunner(Clue game) {
        this.boardGame = game;
    }

    /**
     * Called through the {@link Runnable} interface when beginning a new thread. Holds essential game logic that must
     * be run on a separate thread than the GUI.
     */
    @Override
    public void run() {
        while(boardGame.isPlaying()) {
            Character curCharacter = boardGame.getCharacters().get(0);
            curCharacter.beginTurn();

            wait(() -> boardGame.getNumMoves() <= 0);
            curCharacter.endTurn();
            Collections.rotate(boardGame.getCharacters(), 1);
            boardGame.addMoves(-1);
        }
    }

    /**
     * Pauses thread until waitWhile is no longer true or the game has ended
     */
    private void wait(Supplier<Boolean> waitWhile) {
        while (waitWhile.get() && boardGame.isPlaying()) {
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
