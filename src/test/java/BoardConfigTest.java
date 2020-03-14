import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.ClueBoard;
import mines.zinno.clue.layout.board.validator.IsRectangleValidator;
import mines.zinno.clue.layout.board.validator.NoBadDoorsValidator;
import mines.zinno.clue.layout.board.validator.SubMaxSizeMapValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests to verify maps are loaded in and validated properly.
 * 
 * Satisfies {@link mines.zinno.clue.Assignments#C12A2} and {@link mines.zinno.clue.Assignments#C13A2}
 * requirements
 */
public class BoardConfigTest {

    private ClueBoard board;
    
    /**
     * Code run before all board tests
     */
    @Before
    public void setUp() {
        this.board = new ClueBoard();
    }

    @Test
    public void testSetMap_Valid() {
        board.addMapValidator(new IsRectangleValidator());
        board.addMapValidator(new NoBadDoorsValidator());
        board.addMapValidator(new SubMaxSizeMapValidator());
        
        try {
            board.setMap(Object.class.getResource("/board.csv").getPath());
        } catch (BadMapFormatException e) {
            e.printStackTrace();

            Assert.fail();
        }
        
    }
    
    @Test
    public void testSetMap_TooLong() {
        board.addMapValidator(new SubMaxSizeMapValidator());

        try {
            board.setMap(Object.class.getResource("/badmaps/tootall.csv").getPath());

            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_TooTall() {
        board.addMapValidator(new SubMaxSizeMapValidator());

        try {
            board.setMap(Object.class.getResource("/badmaps/toolong.csv").getPath());

            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_RemoteDoor() {
        board.addMapValidator(new NoBadDoorsValidator());

        try {
            board.setMap(Object.class.getResource("/badmaps/remotedoor.csv").getPath());

            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_InnerDoor() {
        board.addMapValidator(new NoBadDoorsValidator());

        try {
            board.setMap(Object.class.getResource("/badmaps/insidedoor.csv").getPath());

            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_NotRectangular() {
        board.addMapValidator(new IsRectangleValidator());

        try {
            board.setMap(Object.class.getResource("/badmaps/nonrectangular.csv").getPath());

            Assert.fail();
        } catch (BadMapFormatException e) {}
    }

}
