package mines.zinno.clue.layout.board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.layout.board.util.Location;

/**
 * The {@link Board} class holds all board necessary board information
 * 
 * @param <T> Board Cell type
 */
public abstract class Board<T extends Rectangle> extends Pane {

    protected T[][] grid;
    
    protected char[][][] rawMap;
    protected String bgImg;

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
    
    public void setMap(char[][][] rawMap) {
        setMap(rawMap, null);
    }
    
    public void setMap(char[][][] rawMap, String bgImg) {
        this.rawMap = rawMap;
        this.bgImg = bgImg;
    }

}
