package mines.zinno.clue.layouts.board;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.io.TxtStream;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;
import mines.zinno.clue.layouts.board.enums.PlaceKey;
import mines.zinno.clue.shapes.place.*;

/**
 * {@link ClueBoard} extends the {@link Board} class using {@link Place} as it's generic cell type. This class initializes
 * the board with values from board.txt using the {@link PlaceKey} and {@link DirectionKey} enums which help to convert
 * characters into places.
 */
public class ClueBoard extends Board<Place> {

    public ClueBoard() {
        initializeGrid();
        calcAdjacent();
    }

    private void initializeGrid() {
        String[] rawMap = TxtStream.PARSE.apply(TxtStream.BOARD.getInputStream());

        super.grid = new Place[rawMap.length][rawMap[0].length()/2];

        int y = -1;
        for(String line : rawMap) {
            y+=1;
            
            int x = -1;
            for(int i = 0; i < line.length(); i += 2) {
                x+=1;

                // Create new Place instance from grid character key value
                switch(PlaceKey.getPlaceKey(line.charAt(i))) {
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
                if(PlaceKey.getPlaceKey(line.charAt(i)) != PlaceKey.OTHER)
                    continue;

                // Is uppercase (teleporter)
                if(line.charAt(i) > 64 && line.charAt(i) < 91) {
                    super.grid[y][x] = new TeleportPlace(DirectionKey.getDirection(line.charAt(i+1)), Room.getRoom(line.charAt(i)));
                }
                // Is lowercase (room/door)
                else if (line.charAt(i) > 96 && line.charAt(i) < 123) {
                    // Is door
                    if(line.charAt(i+1) != DirectionKey.ALL.getKey())
                        super.grid[y][x] = new DoorPlace(DirectionKey.getDirection(line.charAt(i+1)), Room.getRoom(line.charAt(i)));
                    // Is room
                    else
                        super.grid[y][x] = new RoomPlace(Room.getRoom(line.charAt(i)));
                }
            }
        }
    }

    private void calcAdjacent() {
        for(int y = 0; y < super.grid.length; y++) {
            for(int x = 0; x < super.grid[y].length; x++) {

                // Possible directions (NESW)
                Location[] shifts = new Location[] {new Location(0,-1), new Location(1, 0), new Location(0, 1), new Location(-1, 0)};

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
                        
                        // Continue if a door is adjacent but points in the wrong direction
                        if(adj instanceof DoorPlace && !adj.getDirection().isOpen((i + 2) % 4)) {
                            continue;
                        }

                        adjacents[i] = adj;
                    } catch (IndexOutOfBoundsException e) {}
                }
                
                // Add adjacent places or null
                grid[y][x].setAdjacent(adjacents);
            }
        }
    }

}
