package mines.zinno.clue.listener;

import mines.zinno.clue.shape.character.Character;

/**
 * This {@link Listener} is called when a move is complete
 * 
 * @param <T> Information type
 */
public interface OnMoveListener<T extends Character> extends Listener<T> {}
