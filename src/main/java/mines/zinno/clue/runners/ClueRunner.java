package mines.zinno.clue.runners;

import mines.zinno.clue.Game;
import mines.zinno.clue.shapes.character.Character;
import mines.zinno.clue.shapes.character.enums.Turn;

import java.util.Collections;

public class ClueRunner implements Runnable {

    private static final int REFRESH_RATE = 100;
    
    private Game game;
    
    public ClueRunner(Game game) {
        this.game = game;
    }
    
    @Override
    public void run() {
        while(game.isPlaying()) {
            Character curCharacter = game.getCharacters().get(0);
            curCharacter.setTurn(Turn.PRE_ROLL);

            waitForNextMove();
            curCharacter.setTurn(Turn.OTHER);
            Collections.rotate(game.getCharacters(), 1);
            game.addMoves(-1);
        }
    }
    
    private void waitForNextMove() {
        while (game.getNumMoves() <= 0) {
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
