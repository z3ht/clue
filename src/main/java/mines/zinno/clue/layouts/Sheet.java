package mines.zinno.clue.layouts;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sheet extends Pane {

    private final static int SEPARATION = 144/6;
    private final static int WIDTH = 135;
    private final static double HEIGHT_COVER = 0.2;

    public void crossOut(int value) {
        ImageView image = new ImageView(Object.class.getResource("/imgs/cross.png").toExternalForm());

        image.setFitHeight(SEPARATION * HEIGHT_COVER);
        image.setFitWidth(WIDTH);

        image.setX(0);
        image.setY(SEPARATION * (value - (-0.5 + (HEIGHT_COVER/2))));

        this.getChildren().add(image);
    }


}
