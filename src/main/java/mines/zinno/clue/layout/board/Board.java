package mines.zinno.clue.layout.board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.constant.io.FileStream;
import mines.zinno.clue.exception.BadMapFormatException;
import mines.zinno.clue.layout.board.util.Location;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * The {@link Board} class holds all board necessary board information
 * 
 * @param <T> Board Cell type
 */
public abstract class Board<T extends Rectangle> extends Pane {

    protected T[][] grid;
    
    protected Character[][][] rawMap;
    protected String bgImg;
    
    private Set<Function<Character[][][], Boolean>> mapValidators = new HashSet<>();

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
        this.getChildren().clear();
        
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
    
    public abstract void draw();
    
    public void setMap(String mapLoc) throws BadMapFormatException {
        setMap(mapLoc, null);
    }
    
    public void setMap(String mapLoc, String bgImg) throws BadMapFormatException {
        Character[][][] map = createMap(mapLoc);
        for(Function<Character[][][], Boolean> mapValidator : mapValidators) {
            if(!(mapValidator.apply(map)))
                throw new BadMapFormatException("The map could not be validated");
        }
        this.rawMap = map;
        if(bgImg != null && !(bgImg.equals("")))
            this.bgImg = bgImg;
    }
    
    public Character[][][] createMap(String mapLoc) throws BadMapFormatException {
        if(mapLoc == null || mapLoc.equals(""))
            return null;
        
        File locFile = new File(mapLoc);
        
        try {
            String[] rawMap = FileStream.PARSE.apply(new FileInputStream(locFile));
            
            for(String line : rawMap)
                System.out.println(line);
            
            Character[][][] map = new Character[rawMap.length][rawMap[0].length()/2][2];

            int y = -1;
            for(String line : rawMap) {
                y+= 1;
                for(int x = 0; x < line.length()/2; x++) {
                    map[y][x][0] = line.charAt(x*2);
                    map[y][x][1] = line.charAt((x*2)+1);
                }
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadMapFormatException(String.format("The map file could not be read\nIs '%s' the right location?", locFile.getAbsolutePath()));
        }
    }

    public void addMapValidator(Function<Character[][][], Boolean> mapValidator) {
        this.mapValidators.add(mapValidator);
    }
    
    public void delMapValidator(Function<Character[][][], Boolean> mapValidator) {
        this.mapValidators.remove(mapValidator);
    }
}
