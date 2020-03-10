package mines.zinno.clue.controller;

import mines.zinno.clue.layout.board.Board;
import mines.zinno.clue.layout.board.ClueBoard;

public abstract class GameController {
    
    /**
     * Get the {@link ClueBoard}
     *
     * @return {@link ClueBoard}
     */
    public abstract Board getBoard();
    
}
