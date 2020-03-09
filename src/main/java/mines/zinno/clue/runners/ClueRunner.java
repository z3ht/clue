package mines.zinno.clue.runners;

import mines.zinno.clue.Game;
import mines.zinno.clue.shapes.character.Character;
import mines.zinno.clue.shapes.character.enums.Turn;

import java.util.Collections;

/**
 * The {@link ClueRunner} class implements the {@link Runnable} interface. It holds game code that will be run on a
 * separate thread than the GUI.
 */
public class ClueRunner implements Runnable {

    private static final int REFRESH_RATE = 100;
    
    private Game game;
    
    public ClueRunner(Game game) {
        this.game = game;
    }

    /**
     * Called through the {@link Runnable} interface when beginning a new thread. Holds essential game logic that must
     * be run on a separate thread than the GUI.
     */
    @Override
    public void run() {
        while(game.isPlaying()) {
            Character curCharacter = game.getCharacters().get(0);
            curCharacter.beginTurn();

            waitForNextMove();
            curCharacter.setTurn(Turn.OTHER);
            Collections.rotate(game.getCharacters(), 1);
            game.addMoves(-1);
        }
    }
    
    private void waitForNextMove() {
        while (game.getNumMoves() <= 0 && game.isPlaying()) {
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
