import mines.zinno.clue.layout.board.ClueBoard;
import org.junit.Before;
import org.junit.Test;

public class BoardStructure {

//    private ClueBoard board;
//    
//    /**
//     * Code run before all board tests
//     */
//    @Before
//    public void setUp() {
//        this.board = new ClueBoard();
//    }
//
//    /**
//     * Test the top left corner's {@link Place#getAdjacent()} for
//     */
//    public
//
//
//    @Test
//    public void testRooms() {
//        // Get the map of initial => room 
//        Map<Character, String> legend = board.getLegend();
//        // Ensure we read the correct number of rooms
//        assertEquals(LEGEND_SIZE, legend.size());
//        // To ensure data is correctly loaded, test retrieving a few rooms 
//        // from the hash, including the first and last in the file and a few others
//        assertEquals("Conservatory", legend.get('C'));
//        assertEquals("Ballroom", legend.get('B'));
//        assertEquals("Billiard room", legend.get('R'));
//        assertEquals("Dining room", legend.get('D'));
//        assertEquals("Walkway", legend.get('W'));
//    }
//
//    @Test
//    public void testBoardDimensions() {
//        // Ensure we have the proper number of rows and columns
//        assertEquals(NUM_ROWS, board.getNumRows());
//        assertEquals(NUM_COLUMNS, board.getNumColumns());
//    }
//
//    // Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
//    // two cells that are not a doorway.
//    // These cells are white on the planning spreadsheet
//    @Test
//    public void FourDoorDirections() {
//        BoardCell room = board.getCellAt(4, 3);
//        assertTrue(room.isDoorway());
//        assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
//        room = board.getCellAt(4, 8);
//        assertTrue(room.isDoorway());
//        assertEquals(DoorDirection.DOWN, room.getDoorDirection());
//        room = board.getCellAt(15, 18);
//        assertTrue(room.isDoorway());
//        assertEquals(DoorDirection.LEFT, room.getDoorDirection());
//        room = board.getCellAt(14, 11);
//        assertTrue(room.isDoorway());
//        assertEquals(DoorDirection.UP, room.getDoorDirection());
//        // Test that room pieces that aren't doors know it
//        room = board.getCellAt(14, 14);
//        assertFalse(room.isDoorway());
//        // Test that walkways are not doors
//        BoardCell cell = board.getCellAt(0, 6);
//        assertFalse(cell.isDoorway());
//
//    }
//
//    // Test that we have the correct number of doors
//    @Test
//    public void testNumberOfDoorways()
//    {
//        int numDoors = 0;
//        for (int row=0; row<board.getNumRows(); row++)
//            for (int col=0; col<board.getNumColumns(); col++) {
//                BoardCell cell = board.getCellAt(row, col);
//                if (cell.isDoorway())
//                    numDoors++;
//            }
//        Assert.assertEquals(16, numDoors);
//    }
//
//    // Test a few room cells to ensure the room initial is correct.
//    @Test
//    public void testRoomInitials() {
//        // Test first cell in room
//        assertEquals('C', board.getCellAt(0, 0).getInitial());
//        assertEquals('R', board.getCellAt(4, 8).getInitial());
//        assertEquals('B', board.getCellAt(9, 0).getInitial());
//        // Test last cell in room
//        assertEquals('O', board.getCellAt(21, 22).getInitial());
//        assertEquals('K', board.getCellAt(21, 0).getInitial());
//        // Test a walkway
//        assertEquals('W', board.getCellAt(0, 5).getInitial());
//        // Test the closet
//        assertEquals('X', board.getCellAt(9,13).getInitial());
//    }

}
