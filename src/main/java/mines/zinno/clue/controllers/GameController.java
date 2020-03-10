package mines.zinno.clue.controllers;

import mines.zinno.clue.layouts.board.Board;
import mines.zinno.clue.layouts.board.ClueBoard;

public abstract class GameController {
    
    /**
     * Get the {@link ClueBoard}
     *
     * @return {@link ClueBoard}
     */
    public abstract Board getBoard();
    
}
