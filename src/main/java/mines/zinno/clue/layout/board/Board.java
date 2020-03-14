package mines.zinno.clue.layout.board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.util.Location;
import mines.zinno.clue.layout.board.validator.MapValidator;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Board} class holds all board necessary board information
 * 
 * @param <T> Board Cell type
 */
public abstract class Board<T extends Rectangle> extends Pane {

    protected T[][] grid;
    
    protected Character[][][] rawMap;
    protected String bgImg;
    
    private Set<MapValidator> mapValidators = new HashSet<>();

    /**
     * Retrieve a board cell using it's coordinate
     * 
     * @param location coordinate
     * @return Board cell
     */
    public final T getItemFromCoordinate(Location location) {
        return getItemFromCoordinate(location.getX(), location.getY());
    }

    /**
     * Retrieve a board cell using it's coordinate
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return Board cell
     */
    public final T getItemFromCoordinate(int x, int y) {
        return grid[y][x];
    }

    /**
     * Retrieve a board cell using it's pixel location
     *
     * @param location pixel location
     * @return Board cell
     */
    public final T getItemFromPixel(Location location) {
        return getItemFromPixel(location.getX(), location.getY());
    }

    /**
     * Retrieve a board cell using it's pixel location
     *
     * @param x x pixel
     * @param y y pixel
     * @return Board cell
     */
    public final T getItemFromPixel(int x, int y) {
        return grid[(int) (y / (this.getHeight() / grid.length))][(int) (x / (this.getWidth() / grid[0].length))];
    }

    /**
     * Get the raw board
     * 
     * @return Grid
     */
    public T[][] getGrid() {
        return grid;
    }

    /**
     * Format the board's cells into proper sizes. This method should be called after the board's
     * size is set or changed.
     */
    public void format() {
        // Clear children from previous format call
        this.getChildren().clear();
        
        // Return if grid is null (can not be formatted)
        if(grid == null)
            return;
        
        
        for(int y = 0; y < grid.length; y++) {
            if(grid[y] == null)
                continue;
            for(int x = 0; x < grid[y].length; x++) {
                if(grid[y][x] == null)
                    continue;
                grid[y][x].setHeight(this.getHeight()/grid.length);
                grid[y][x].setWidth(this.getWidth()/grid[0].length);
                grid[y][x].setX(grid[y][x].getWidth()*x);
                grid[y][x].setY(grid[y][x].getHeight()*y);
                this.getChildren().add(grid[y][x]);
            }
        }
    }

    /**
     * Draw a board
     */
    public abstract void draw();

    /**
     * Set the board's map. Satisfies {@link mines.zinno.clue.Assignments#C12A1} requirement
     * 
     * @param mapLoc Map file location (Supports .txt and .csv files)
     * @throws BadMapFormatException Thrown when a map is not correctly formatted
     */
    public void setMap(String mapLoc) throws BadMapFormatException {
        setMap(mapLoc, null);
    }

    /**
     * Set the board's map. Satisfies {@link mines.zinno.clue.Assignments#C12A1} requirement
     *
     * @param mapLoc Map file location (Supports .txt and .csv files)
     * @param bgImg Background image file location
     * @throws BadMapFormatException Thrown when a map is not correctly formatted
     */
    public void setMap(String mapLoc, String bgImg) throws BadMapFormatException {
        Character[][][] map = createMap(mapLoc);
        
        // Validate map
        if(map != null) {
            // Map must have an x and y value
            if(map[0] == null)
                throw new BadMapFormatException("The map could not be validated");
            
            // Ensure there are no commas
            // Commas indicate a csv file was parsed incorrectly
            for(int y = 0; y < map.length; y++) {
                for(int x = 0; x < map[y].length; x++) {
                    for(int i = 0; i < map[y][x].length; i++) {
                        if (map[y][x][i] == null || map[y][x][i] == ',')
                            throw new BadMapFormatException("The map could not be validated");
                    }
                }
            }
            
            // Validate using validators
            for(MapValidator mapValidator : mapValidators) {
                if(!(mapValidator.apply(map)))
                    throw new BadMapFormatException("The map could not be validated");
            }
        }
        this.rawMap = map;
        
        if(bgImg != null && !(bgImg.equals("")))
            this.bgImg = bgImg;
    }

    /**
     * Create a map given the map's file location
     * 
     * @param mapLoc Map file location
     *               
     * @return {@link Character[][][]} map
     * @throws BadMapFormatException Thrown when a map is not formatted correctly
     */
    protected abstract Character[][][] createMap(String mapLoc) throws BadMapFormatException;

    public void addMapValidator(MapValidator mapValidator) {
        this.mapValidators.add(mapValidator);
    }
    
    public void delMapValidator(MapValidator mapValidator) {
        this.mapValidators.remove(mapValidator);
    }
}
