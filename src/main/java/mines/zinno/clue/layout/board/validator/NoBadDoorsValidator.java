package mines.zinno.clue.layout.board.validator;

import mines.zinno.clue.layout.board.constant.DirectionKey;
import mines.zinno.clue.layout.board.constant.PlaceKey;
import mines.zinno.clue.layout.board.util.Location;

/**
 * The {@link NoBadDoorsValidator} validates a map has no doors inside rooms or doors totally separated from a room
 */
public class NoBadDoorsValidator implements MapValidator {
    

    @Override
    public Boolean apply(Character[][][] characters) {
        
        for(int y = 0; y < characters.length; y++) {
            for(int x = 0; x < characters[y].length; x++) {

                // Continue if not a door
                if(characters[y][x][1] != DirectionKey.HORIZONTAL_DOOR.getKey() && characters[y][x][1] != DirectionKey.VERTICAL_DOOR.getKey())
                    continue;
                
                // Possible directions (NESW)
                Location[] shifts = new Location[] {
                        new Location(0,-1),
                        new Location(1, 0),
                        new Location(0, 1),
                        new Location(-1, 0)
                };

                // Counts surrounding rooms
                int roomPlaceCount = 0;
                for(Location shift : shifts) {
                    Character val = characters[y+shift.getY()][x+shift.getX()][0];
                    
                   try {
                       if(val != PlaceKey.PATH.getKey() &&
                            val != PlaceKey.START.getKey() &&
                            val != PlaceKey.UNREACHABLE.getKey()) {
                           roomPlaceCount += 1;
                       }
                       
                   } catch (IndexOutOfBoundsException e) {}
                    
                }
                
                // False if no rooms are all rooms
                if(roomPlaceCount == 0 || roomPlaceCount == 4)
                    return false;
                
            }
        }
        return true;
    }
}
