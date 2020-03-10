package mines.zinno.clue.enums;

/**
 * The {@link Card} interface is implemented by all Card enums. In the Clue game, this includes the
 * {@link Weapon}, {@link Room}, and {@link Suspect} enums. This interface provides structure
 * to cards.
 */
public interface Card {

    /**
     * {@link Card} name in {@link String} format
     */
    String getName();

    /**
     * Get the {@link Card} id
     */
    int getId();
    
}
