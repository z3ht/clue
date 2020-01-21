package mines.zinno.clue.layouts.board;

import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.TxtStream;
import mines.zinno.clue.layouts.board.enums.DirectionKey;
import mines.zinno.clue.layouts.board.utils.Location;
import mines.zinno.clue.layouts.board.enums.PlaceKey;
import mines.zinno.clue.shapes.place.*;

public class ClueBoard extends Board<Place> {

    public ClueBoard() {
        initializeGrid();
        calcAdjacent();
    }

    private void initializeGrid() {
        String[] map = TxtStream.PARSE.apply(TxtStream.BOARD.getInputStream());

        super.grid = new Place[map.length][map[0].length()/2];

        int y = 0;
        for(String line : map) {
            int x = -1;
            for(int i = 0; i < line.length(); i += 2) {
                x+=1;
                switch(PlaceKey.getPlace(line.charAt(i))) {
                    case PATH:
                        super.grid[y][x] = new Place();
                        break;
                    case UNREACHABLE:
                        super.grid[y][x] = new Place(DirectionKey.ALL, false, 1);
                        break;
                    case START:
                        super.grid[y][x] = new StartPlace();
                        break;
                }

                if(PlaceKey.getPlace(line.charAt(i)) != PlaceKey.OTHER)
                    continue;
                else if(line.charAt(i) > 64 && line.charAt(i) < 91) {
                    super.grid[y][x] = new TeleportPlace(DirectionKey.getDirection(line.charAt(i+1)), Room.getRoom(line.charAt(i)));
                } else if (line.charAt(i) > 96 && line.charAt(i) < 123) {
                    if(line.charAt(i+1) == DirectionKey.HORIZONTAL_DOOR.getKey() || line.charAt(i+1) == DirectionKey.VERTICAL_DOOR.getKey())
                        super.grid[y][x] = new DoorPlace(DirectionKey.getDirection(line.charAt(i+1)), Room.getRoom(line.charAt(i)));
                    else
                        super.grid[y][x] = new RoomPlace(DirectionKey.getDirection(line.charAt(i+1)), Room.getRoom(line.charAt(i)));
                        
                }
            }
            y+=1;
        }
    }

    private void calcAdjacent() {
        for(int y = 0; y < super.grid.length; y++) {
            for(int x = 0; x < super.grid[y].length; x++) {

                Location[] shifts = new Location[] {new Location(0,-1), new Location(1, 0), new Location(0, 1), new Location(-1, 0)};
                Place[] adjacents = new Place[5];

                if(grid[y][x] instanceof Teleportable) {
                    adjacents[4] = super.getItemFromCoordinate(((Teleportable) grid[y][x]).teleportTo());
                }

                int i = -1;
                for(Location shift : shifts) {
                    i++;
                    
                    if(!grid[y][x].getDirection().isOpen(i))
                        continue;
                    
                    try {
                        Place adj = super.getItemFromCoordinate(x + shift.getX(), y + shift.getY());
                        
                        if(adj == null)
                            continue;

                        if(!adj.isReachable())
                            continue;

                        if(grid[y][x] instanceof TeleportPlace && !(adj instanceof RoomPlace))
                            continue;

                        if(adj instanceof DoorPlace && grid[y][x] instanceof RoomPlace)
                            adj = super.getItemFromCoordinate(x + shift.getX()*2, y + shift.getY()*2);

                        adjacents[i] = adj;
                    } catch (IndexOutOfBoundsException e) {}
                }
                
                grid[y][x].setAdjacent(adjacents);
            }
        }
    }

}
