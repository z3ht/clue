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
        TeleportPlace tp = (TeleportPlace) board.getItemFromCoordinate(0, 3);
        
        Assert.assertEquals(
                "Teleported to wrong location", 
                ((RoomPlace) board.getItemFromCoordinate(tp.teleportTo())).getRoom(),
                Room.KITCHEN);
    }
    
    @Test
    public void testDoorAdjacent() {
        Place place = board.getItemFromCoordinate(6, 3);
        
        Place[] adj = place.getAdjacent();
        
        Assert.assertEquals(adj[0], board.getItemFromCoordinate(6, 2));
        Assert.assertNull(adj[1]);
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(6, 4));
        Assert.assertNull(adj[3]);
        Assert.assertNotNull(adj[4]);
    }
    
    @Test
    public void testRoomNextToPathAdjacent() {
        Place place = board.getItemFromCoordinate(1, 4);

        Place[] adj = place.getAdjacent();

        Assert.assertNull(adj[0]);
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(2, 4));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(1, 5));
        Assert.assertNull(adj[3]);
        Assert.assertNull(adj[4]);
    }
    
    @Test
    public void testPathNextToRoomAdjacent() {
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
        Place place = board.getItemFromCoordinate(7, 3);

        Place[] adj = place.getAdjacent();

        Assert.assertEquals(adj[0], board.getItemFromCoordinate(7, 2));
        Assert.assertEquals(adj[1], board.getItemFromCoordinate(8, 3));
        Assert.assertEquals(adj[2], board.getItemFromCoordinate(7, 4));
        Assert.assertNull(adj[3]);
        Assert.assertNull(adj[4]);
    }
    
}
