package mines.zinno.clue.controller;

import mines.zinno.clue.layout.board.Board;
import mines.zinno.clue.layout.board.ClueBoard;

/**
 * The {@link GameController} is the controller extended by all board game controllers. It holds the {@link Board}
 */
public abstract class GameController {
    
    /**
     * Get the {@link ClueBoard}
     *
     * @return {@link ClueBoard}
     */
    public abstract Board getBoard();
    
}
