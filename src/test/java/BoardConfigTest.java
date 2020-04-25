import mines.zinno.clue.Main;
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
 * Satisfies {@link mines.zinno.clue.Assignments#C13A2} requirements
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
        // Add all validators to ensure they do not trigger a false positive
        board.addMapValidator(new IsRectangleValidator());
        board.addMapValidator(new NoBadDoorsValidator());
        board.addMapValidator(new SubMaxSizeMapValidator());
        
        try {
            // Set the map. The validators validate the map here
            board.setMap(Main.class.getResource("/board.csv").toString());
            
            // Initialize
            board.initialize();
            
            // Assert correct size
            Assert.assertEquals(25, board.getGrid().length);
            Assert.assertEquals(24, board.getGrid()[0].length);
        } catch (BadMapFormatException e) {
            e.printStackTrace();

            Assert.fail();
        }
        
    }
    
    @Test
    public void testSetMap_TooLong() {
        // Ensures maps are not too long
        board.addMapValidator(new SubMaxSizeMapValidator());

        try {
            board.setMap(Main.class.getResource("/badmaps/tootall.csv").toString());

            // Fail if validator did not catch this map's error
            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_TooTall() {
        // Ensures maps are not too tall
        board.addMapValidator(new SubMaxSizeMapValidator());

        try {
            board.setMap(Main.class.getResource("/badmaps/toolong.csv").toString());

            // Fail if validator did not catch this map's error
            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_RemoteDoor() {
        // Ensures maps do not have stand alone doors
        board.addMapValidator(new NoBadDoorsValidator());

        try {
            board.setMap(Main.class.getResource("/badmaps/remotedoor.csv").toString());

            // Fail if validator did not catch this map's error
            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_InnerDoor() {
        // Ensures maps do not have doors inside rooms
        board.addMapValidator(new NoBadDoorsValidator());

        try {
            board.setMap(Main.class.getResource("/badmaps/insidedoor.csv").toString());

            // Fail if validator did not catch this map's error
            Assert.fail();
        } catch (BadMapFormatException e) {}
    }
    
    @Test
    public void testSetMap_NotRectangular() {
        // Ensures maps are rectangular
        board.addMapValidator(new IsRectangleValidator());

        try {
            board.setMap(Main.class.getResource("/badmaps/nonrectangular.csv").toString());

            // Fail if validator did not catch this map's error
            Assert.fail();
        } catch (BadMapFormatException e) {}
    }

}
