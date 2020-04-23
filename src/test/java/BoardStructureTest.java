import mines.zinno.clue.constant.Room;
import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.ClueBoard;
import mines.zinno.clue.layout.board.validator.IsRectangleValidator;
import mines.zinno.clue.layout.board.validator.NoBadDoorsValidator;
import mines.zinno.clue.layout.board.validator.SubMaxSizeMapValidator;
import mines.zinno.clue.shape.place.DoorPlace;
import mines.zinno.clue.shape.place.Place;
import mines.zinno.clue.shape.place.RoomPlace;
import mines.zinno.clue.shape.place.TeleportPlace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Code ensuring adjacent places work properly as well as telporting. It helps to read through these with the
 * pdf map open in the ext-resources folder.
 * 
 * Satisfies {@link mines.zinno.clue.Assignments#C12A2}, {@link mines.zinno.clue.Assignments#C13A1},
 * {@link mines.zinno.clue.Assignments#C16A1}, {@link mines.zinno.clue.Assignments#C16A2} requirements
 */
public class BoardStructureTest {

    private ClueBoard board;

    /**
     * Code run before all board tests
     */
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
    }
    
    @Test
    public void testNumDoors() {
        // Gets number of doors on map
        int numDoors = 0;
        for(Place[] places : board.getGrid()) {
            for(Place place : places) {
                if(place instanceof DoorPlace)
                    numDoors += 1;
            }
        }
        Assert.assertEquals(
                "The board contains 20 doors but they were not all parsed correctly",
                numDoors,
                20
        );
    }
    
    @Test
    public void testTeleporterTeleport() {
        // Ensures TP place teleports
        TeleportPlace tp = (TeleportPlace) board.getItemFromCoordinate(0, 3);
        
        Assert.assertEquals(
                "Teleported to wrong location", 
                ((RoomPlace) board.getItemFromCoordinate(tp.teleportTo())).getRoom(),
                Room.KITCHEN);
    }
    
    @Test
    public void testNorthEast() {
        // Test top right diagonal board piece for proper values
        Place place = board.getItemFromCoordinate(0, 0);

        Place[] adj = place.getAdjacent();

        Assert.assertNull(adj[0]);
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(1, 0));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(0, 1));
        Assert.assertNull(adj[3]);
        Assert.assertNull(adj[4]);
    }
    
    @Test
    public void SouthWest() {
        // Test bottom left board piece for proper values
        Place place = board.getItemFromCoordinate(23, 24);

        Place[] adj = place.getAdjacent();

        Assert.assertEquals(adj[0], board.getItemFromCoordinate(23, 23));
        Assert.assertNull(adj[1]);
        Assert.assertNull(adj[2]);
        Assert.assertNull(adj[3]);
        Assert.assertNull(adj[4]);
    }
    
    @Test
    public void testRoomAdjacentDoor() {
        // Test a room that is adjacent to a door
        // This one is weird because the room does not have the door as its adjacent piece but instead the
        //      place outside of the door. This works as it should if you think about it but is counter intuitive.
        Place place = board.getItemFromCoordinate(6, 2);

        Place[] adj = place.getAdjacent();

        Assert.assertEquals(adj[0], board.getItemFromCoordinate(6, 1));
        Assert.assertNull(adj[1]);
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(6, 4));
        Assert.assertEquals(adj[3], board.getItemFromCoordinate(5, 2));
        Assert.assertNull(adj[4]);
    }
    
    @Test
    public void testNoDoorAdjacent() {
        // Test a piece that does not have a door adjacent to it
        Place place = board.getItemFromCoordinate(7, 5);

        Place[] adj = place.getAdjacent();
        
        Assert.assertEquals(adj[0], board.getItemFromCoordinate(7, 4));
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(8, 5));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(7, 6));
        Assert.assertEquals(adj[3], board.getItemFromCoordinate(6, 5));
        Assert.assertNull(adj[4]);
    }
    
    @Test
    public void testDoorAdjacent() {
        // Test a door's adjacent values
        Place place = board.getItemFromCoordinate(6, 3);
        
        Place[] adj = place.getAdjacent();
        
        Assert.assertEquals(adj[0], board.getItemFromCoordinate(6, 2));
        Assert.assertNull(adj[1]);
        Assert.assertNull(adj[2]);
        Assert.assertNull(adj[3]);
        Assert.assertEquals(adj[4], board.getItemFromCoordinate(Room.STUDY.getCenter()));
    }
    
    @Test
    public void testRoomNextToPathAdjacent() {
        // Test a room's adjacent values when it is next to a path
        Place place = board.getItemFromCoordinate(1, 4);

        Place[] adj = place.getAdjacent();

        Assert.assertNull(adj[0]);
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(2, 4));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(1, 5));
        Assert.assertNull(adj[3]);
    }
    
    @Test
    public void testPathNextToRoomAdjacent() {
        // Test a path's adjacent values when it is next to a door
        Place place = board.getItemFromCoordinate(1, 4);

        Place[] adj = place.getAdjacent();

        Assert.assertNull(adj[0]);
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(2, 4));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(1, 5));
        Assert.assertNull(adj[3]);
        Assert.assertNull(adj[4]);
    }
    
    @Test
    public void testPathNextToDoorAdjacent_Valid() {
        // Test a path's adjacent values when it is next to a door and the door faces it
        Place place = board.getItemFromCoordinate(1, 3);

        Place[] adj = place.getAdjacent();

        Assert.assertEquals(adj[0], board.getItemFromCoordinate(1, 2));
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(2, 3));
        Assert.assertNull(adj[2]);
        Assert.assertEquals(adj[3], board.getItemFromCoordinate(0, 3));
        Assert.assertNull(adj[4]);
    }

    @Test
    public void testPathNextToDoorAdjacent_Invalid() {
        // Test a path's adjacent values when it is next to a door but the door does not face it
        Place place = board.getItemFromCoordinate(7, 3);

        Place[] adj = place.getAdjacent();

        Assert.assertEquals(adj[0], board.getItemFromCoordinate(7, 2));
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(8, 3));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(7, 4));
        Assert.assertNull(adj[3]);
        Assert.assertNull(adj[4]);
    }
    
}
