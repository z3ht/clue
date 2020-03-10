package mines.zinno.clue.runner;

import mines.zinno.clue.game.Clue;
import mines.zinno.clue.game.BoardGame;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.constant.Turn;

import java.util.Collections;

/**
 * The {@link ClueRunner} class implements the {@link Runnable} interface. It holds game code that will be run on a
 * separate thread than the GUI.
 */
public class ClueRunner implements Runnable {

    private static final int REFRESH_RATE = 100;
    
    private BoardGame boardGame;
    
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
            Character curCharacter = (Character) boardGame.getCharacters().get(0);
            curCharacter.beginTurn();

            wait(curCharacter.getTurn() != Turn.POST_MOVE);
            wait(boardGame.getNumMoves() <= 0);
            curCharacter.setTurn(Turn.OTHER);
            Collections.rotate(boardGame.getCharacters(), 1);
            boardGame.addMoves(-1);
        }
    }

    /**
     * Pauses thread until isWait is no longer true
     */
    private void wait(boolean isWait) {
        while (isWait && boardGame.isPlaying()) {
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
