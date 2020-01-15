package mines.zinno.clue.layouts.board;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import mines.zinno.clue.layouts.board.utils.Location;

public class Board<T extends Rectangle> extends Pane {

    protected T[][] grid;

    public final T getItemFromCoordinate(Location location) {
        return getItemFromCoordinate(location.getX(), location.getY());
    }

    public final T getItemFromCoordinate(int x, int y) {
        return grid[y][x];
    }

    public final T getItemFromPixel(Location location) {
        return getItemFromPixel(location.getX(), location.getY());
    }

    public final T getItemFromPixel(int x, int y) {
        return grid[(int) (y / (this.getHeight() / grid.length))][(int) (x / (this.getWidth() / grid[0].length))];
    }
    
    public T[][] getGrid() {
        return grid;
    }

    public void format() {
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

}
