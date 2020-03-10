package mines.zinno.clue.shape.place;

/**
 * The {@link BasicPlace} is a subclass of the {@link Place} class. It is used by all static places in the
 * {@link mines.zinno.clue.layout.board.ClueBoard} including: {@link StartPlace}s and basic paths. These
 * places are basic/static because they are functionally identical throughout the entire board. A {@link StartPlace} at
 * the top of the board has the same attributes as a {@link StartPlace} at the bottom of the board.
 */
public class BasicPlace extends Place {}
