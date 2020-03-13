package mines.zinno.clue.shape.place;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import mines.zinno.clue.constant.io.ImgURL;

/**
 * The {@link BasicPlace} is a subclass of the {@link Place} class. It is used by all static places in the
 * {@link mines.zinno.clue.layout.board.ClueBoard} including: {@link StartPlace}s and basic paths. These
 * places are basic/static because they are functionally identical throughout the entire board. A {@link StartPlace} at
 * the top of the board has the same attributes as a {@link StartPlace} at the bottom of the board.
 */
public class BasicPlace extends Place {
    
    @Override
    public void display() {
        this.setFill(new ImagePattern(new Image(ImgURL.BASIC_PLACE.getUrl().toExternalForm())));
        this.setOpacity(1);
    }
    
}
