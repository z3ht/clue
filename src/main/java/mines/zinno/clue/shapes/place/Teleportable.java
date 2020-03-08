package mines.zinno.clue.shapes.place;

import mines.zinno.clue.layouts.board.utils.Location;

/**
 * The {@link Teleportable} interface is used by {@link Place}s on the {@link mines.zinno.clue.layouts.board.ClueBoard}
 * that are capable of teleporting players to places on the map that are not adjacent to the current place.
 */
public interface Teleportable {

    Location teleportTo();

}
