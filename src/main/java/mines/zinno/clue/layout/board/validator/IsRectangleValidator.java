package mines.zinno.clue.layout.board.validator;

/**
 * The {@link IsRectangleValidator} validates a map is a rectangle
 */
public class IsRectangleValidator implements MapValidator {


    @Override
    public Boolean apply(Character[][][] characters) {
        for(int y = 1; y < characters.length; y++) {
            if(characters[y].length != characters[y-1].length)
                return false;
        }
        return true;
    }
}
