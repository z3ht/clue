package mines.zinno.clue.shape.place;

import mines.zinno.clue.layout.board.util.Location;

/**
 * The {@link Teleportable} interface is used by {@link Place}s on the {@link mines.zinno.clue.layout.board.ClueBoard}
 * that are capable of teleporting players to places on the map that are not adjacent to the current place.
 */
public interface Teleportable {

    Location teleportTo();

}
