package mines.zinno.clue.layouts;

import javafx.scene.layout.Region;

/**
 * The PredictCheck class is a custom check box extending the {@link Region} class.
 *
 * When clicked, the "marked-check" value is added to the instance's list of style classes
 */
public class PredictCheck extends Region {

    private final static String CHECK_STYLE = "check";
    private final static String MARKED_CHECK_STYLE = "marked-check";

    private boolean isMarked;

    public PredictCheck() {
        this.setOnMouseClicked(event -> {
            PredictCheck predictCheck = (PredictCheck) event.getSource();

            if(predictCheck.isDisabled())
                return;

            predictCheck.setMarked(!predictCheck.isMarked());}
        );

        this.getStyleClass().add(CHECK_STYLE);
    }

    public void mark() {
        this.getStyleClass().add(MARKED_CHECK_STYLE);
        isMarked = true;
    }

    public void unmark() {
        this.getStyleClass().remove(MARKED_CHECK_STYLE);
        isMarked = false;
    }

    public void setMarked(boolean shouldMark) {
        if(shouldMark)
            mark();
        else
            unmark();
    }

    public boolean isMarked() {
        return isMarked;
    }


}
