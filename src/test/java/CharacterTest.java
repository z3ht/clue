import mines.zinno.clue.constant.Suspect;
import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.ClueBoard;
import mines.zinno.clue.layout.board.validator.IsRectangleValidator;
import mines.zinno.clue.layout.board.validator.NoBadDoorsValidator;
import mines.zinno.clue.layout.board.validator.SubMaxSizeMapValidator;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.shape.character.Computer;
import mines.zinno.clue.shape.place.Place;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Code ensuring character code works properly. Specifically, movement and turn functionality
 * 
 * Satisfies {@link mines.zinno.clue.Assignments#C16A2} requirements
 */
public class CharacterTest {

    private Character character;
    private ClueBoard board;

    @Before
    public void setUp() {
        this.board = new ClueBoard();

        board.addMapValidator(new IsRectangleValidator());
        board.addMapValidator(new NoBadDoorsValidator());
        board.addMapValidator(new SubMaxSizeMapValidator());

        try {
            board.setMap(Object.class.getResource("/board.csv").getPath());
        } catch (BadMapFormatException e) {
            e.printStackTrace();

            Assert.fail();
        }

        board.initialize();
        
        // Create a character
        this.character = new Computer(null, Suspect.COL_MUSTARD, null);
    }
    
    @Test
    public void testPossibleMove_Basic() {
        // Test most basic possible move for character
        Set<Place> posMoves = character.calcPosMoves(board.getItemFromCoordinate(0, 5), 1);
        
        Assert.assertTrue(posMoves.contains(board.getItemFromCoordinate(1, 5)));
    }

    @Test
    public void testPossibleMove_LongDistance() {
        // Test long distance possible move for character
        Set<Place> posMoves = character.calcPosMoves(board.getItemFromCoordinate(0, 5), 6);

        Assert.assertTrue(posMoves.contains(board.getItemFromCoordinate(6, 5)));
    }

    @Test
    public void testPossibleMove_AcrossRoom() {
        // Test a move that goes through a room to get to a place
        Set<Place> posMoves = character.calcPosMoves(board.getItemFromCoordinate(11, 7), 2);

        Assert.assertTrue(posMoves.contains(board.getItemFromCoordinate(8, 4)));
    }
    
    @Test
    public void testPossibleMove_AcrossTeleporter() {
        // Test a move that goes through a room and into a teleporter to another room
        Set<Place> posMoves = character.calcPosMoves(board.getItemFromCoordinate(6, 4), 2);

        Assert.assertTrue(posMoves.contains(board.getItemFromCoordinate(22, 22)));
    }
    
}
