package mines.zinno.clue.layout.board;

import mines.zinno.clue.constant.Room;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.constant.io.FileStream;
import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.constant.DirectionKey;
import mines.zinno.clue.util.Location;
import mines.zinno.clue.layout.board.constant.PlaceKey;
import mines.zinno.clue.shape.place.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * {@link ClueBoard} extends the {@link Board} class using {@link Place} as it's generic cell type. This class initializes
 * the board with values from board.csv using the {@link PlaceKey} and {@link DirectionKey} enums which help to convert
 * characters into places.
 * 
 * Satisfies {@link mines.zinno.clue.Assignments#C12A2} requirements
 */
public class ClueBoard extends Board<Place> {
    
    private final static Character[][][] DEFAULT_MAP;
    
    static {
        // Create the default map
        String[] defRawMap = FileStream.PARSE.apply(FileStream.BOARD.getInputStream());
        
        DEFAULT_MAP = new Character[defRawMap.length][defRawMap[0].length()/2][2];
        int y = -1;
        for(String line : defRawMap) {
            y+= 1;
            for(int x = 0; x < line.length()/2; x++) {
                DEFAULT_MAP[y][x][0] = line.charAt(x*2);
                DEFAULT_MAP[y][x][1] = line.charAt((x*2)+1);
            }
        }
    }

    public void initialize() {
        initializeGrid();
        calcAdjacent();
    }
    
    @Override
    public void draw() {
        initialize();
        format();
        
        if(bgImg != null || (rawMap == null || rawMap == DEFAULT_MAP))
            drawImage();
        else
            drawCustom();
    }

    /**
     * Draw the default board image
     */
    private void drawImage() {
        String image = ImgURL.BOARD.getUrl().toExternalForm();
        
        if(bgImg != null) {
            image = bgImg;
        }
        
        this.getParent().setStyle(String.format("-fx-background-image: url(%s);", image));
    }

    /**
     * Draw a custom board image
     */
    private void drawCustom() {
        this.getParent().setStyle("");
        for(Place[] places : grid) {
            for(Place place : places) {
                place.display();
            }
        }
    }

    /**
     * Initialize the board's grid
     */
    protected void initializeGrid() {        
        if(rawMap == null)
            rawMap = DEFAULT_MAP;

        this.grid = new Place[rawMap.length][rawMap[0].length];
        
        for(int y = 0; y < rawMap.length; y++) {
            for(int x = 0; x < rawMap[y].length; x++)   {
                // Create new Place instance from grid character key value
                switch(PlaceKey.getPlaceKey(rawMap[y][x][0])) {
                    case PATH:
                        super.grid[y][x] = new BasicPlace();
                        break;
                    case UNREACHABLE:
                        super.grid[y][x] = new Place(DirectionKey.ALL, false, 1);
                        break;
                    case START:
                        super.grid[y][x] = new StartPlace();
                        break;
                }

                // Continue if grid place already filled
                if(PlaceKey.getPlaceKey(rawMap[y][x][0]) != PlaceKey.OTHER)
                    continue;

                // Is uppercase (teleporter)
                if(rawMap[y][x][0] > 64 && rawMap[y][x][0] < 91) {
                    super.grid[y][x] = new TeleportPlace(
                            DirectionKey.getDirection(rawMap[y][x][1]), 
                            Room.getRoom(rawMap[y][x][0])
                    );
                }
                // Is lowercase (room/door)
                else if (rawMap[y][x][0] > 96 && rawMap[y][x][0] < 123) {
                    // Is door
                    if(rawMap[y][x][1] != DirectionKey.ALL.getKey())
                        super.grid[y][x] = new DoorPlace(
                                DirectionKey.getDirection(rawMap[y][x][1]), 
                                Room.getRoom(rawMap[y][x][0])
                        );
                    // Is room
                    else
                        super.grid[y][x] = new RoomPlace(Room.getRoom(rawMap[y][x][0]));
                }
            }
        }
    }

    /**
     * Calculate adjacent places
     */
    protected void calcAdjacent() {
        for(int y = 0; y < super.grid.length; y++) {
            for(int x = 0; x < super.grid[y].length; x++) {
                
                // Possible directions (NESW)
                Location[] shifts = new Location[] {
                        new Location(0,-1), 
                        new Location(1, 0), 
                        new Location(0, 1), 
                        new Location(-1, 0)
                };

                // Possible adjacent locations
                Place[] adjacents = new Place[5];

                // Adds teleport location to adjacents for teleportable places
                if(grid[y][x] instanceof Teleportable) {
                    adjacents[4] = super.getItemFromCoordinate(((Teleportable) grid[y][x]).teleportTo());
                }

                int i = -1;
                for(Location shift : shifts) {
                    i++;

                    // Check if it is theoretically possible to move to adjacent location
                    if(!grid[y][x].getDirection().isOpen(i))
                        continue;
                    
                    // Create adjacent location if it exists
                    try {
                        Place adj = super.getItemFromCoordinate(x + shift.getX(), y + shift.getY());
                        
                        if(adj == null)
                            continue;

                        if(!adj.isReachable())
                            continue;

                        // Continue if RoomPlace does not connect with RoomPlace
                        if(grid[y][x] instanceof RoomPlace && !(adj instanceof RoomPlace))
                            continue;
                        
                        // Continue if BasicPlace does not connect with BasicPlace or DoorPlace
                        if(grid[y][x] instanceof BasicPlace && !(adj instanceof BasicPlace || adj instanceof DoorPlace))
                            continue;

                        // Add the place outside of door instead of the door itself (also add no other basicPlace)
                        if(grid[y][x] instanceof RoomPlace &&
                                adj instanceof DoorPlace &&
                                adj.getDirection().isOpen(i % 4))
                            adj = super.getItemFromCoordinate(x + shift.getX() * 2, y + shift.getY() * 2);


                        // Continue if a door is adjacent but points in the wrong direction
                        if(adj instanceof DoorPlace && !adj.getDirection().isOpen((i + 2) % 4)) {
                            continue;
                        }

                        adjacents[i] = adj;
                    } catch (IndexOutOfBoundsException ignored) {}
                }
                
                // Add adjacent places or null
                grid[y][x].setAdjacent(adjacents);
            }
        }
    }

    @Override
    protected Character[][][] createMap(String mapLoc) throws BadMapFormatException {
        if(mapLoc == null || mapLoc.equals(""))
            return null;

        File locFile = new File(mapLoc);

        try {
            String[] rawMap = FileStream.PARSE.apply(new FileInputStream(locFile));

            Character[][][] map = new Character[rawMap.length][rawMap[0].length()/2][2];

            int y = -1;
            for(String rawLine : rawMap) {
                StringBuilder line = new StringBuilder(rawLine);
                
                y+= 1;
                int x = 0;
                while(x < line.length()/2) {
                    if(line.charAt(x*2) == ',')
                        line.deleteCharAt(x*2);
                    
                    map[y][x][0] = line.charAt(x*2);

                    if(line.charAt((x*2)+1) == ',')
                        map[y][x][1] = DirectionKey.ALL.getKey();
                    else
                        map[y][x][1] = line.charAt((x*2)+1);
                    x++;
                }
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadMapFormatException(String.format("The map file could not be read\nIs '%s' the right location?", locFile.getAbsolutePath()));
        }
    }

}
