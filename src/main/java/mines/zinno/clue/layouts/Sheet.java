package mines.zinno.clue.layouts;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import mines.zinno.clue.enums.Room;
import mines.zinno.clue.enums.Suspect;
import mines.zinno.clue.enums.Weapon;
import mines.zinno.clue.enums.io.ImgURL;

/**
 * The {@link Sheet} class extends the {@link Pane} class. It holds guess information for each suspect/place/weapon.
 */
public class Sheet extends Pane {

    /**
     * Distance between each sheet value
     */
    private final static int SEPARATION = 144/6;

    /**
     * Width of sheet
     */
    private final static int WIDTH = 135;

    /**
     * Coverable sheet value space
     */
    private final static double HEIGHT_COVER = 0.2;

    /**
     * Crosses out the sheet value at the specified ID. This method is typically called
     * when a guess is made and someone returns a value. It is more permanent than checking a box.
     * 
     * @param value Sheet value ID (Check  {@link Room#getId()}/{@link Weapon#getId()}/{@link Suspect#getId()})
     */
    public void crossOut(int value) {
        ImageView image = new ImageView(ImgURL.CROSS.getUrl().toExternalForm());

        image.setFitHeight(SEPARATION * HEIGHT_COVER);
        image.setFitWidth(WIDTH);

        image.setX(0);
        // Set the y value to the center of the value location minus the half the cover amount in pixel form
        // Essentially centering the image around the sheet value
        image.setY(SEPARATION * (value - (-0.5 + (HEIGHT_COVER/2))));

        this.getChildren().add(image);
    }


}
