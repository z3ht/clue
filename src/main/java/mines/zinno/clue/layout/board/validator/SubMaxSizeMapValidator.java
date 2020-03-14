package mines.zinno.clue.layout.board.validator;

import java.awt.*;

/**
 * The {@link SubMaxSizeMapValidator} validates a map is below a specified maximum size
 */
public class SubMaxSizeMapValidator implements MapValidator {
    
    private static final Dimension MAX_SIZE = new Dimension(35, 35);

    @Override
    public Boolean apply(Character[][][] map) {
        return map.length < MAX_SIZE.height && map[0].length < MAX_SIZE.width;
    }
}
