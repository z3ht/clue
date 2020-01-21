package mines.zinno.clue.shapes.character.exceptions;

public class ImpossibleMove extends RuntimeException {

    private static final String MESSAGE = "Unable to move to that place";
    
    public ImpossibleMove() {
        super(MESSAGE);
    }
}
