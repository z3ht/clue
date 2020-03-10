package mines.zinno.clue.layout;

import javafx.scene.layout.Region;

/**
 * The {@link PredictCheck} class extends the {@link Region} class.
 *
 * When clicked, the {@value MARKED_CHECK_STYLE} value is added to the referenced instance's list of style classes.
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

    /**
     * Add {@value MARKED_CHECK_STYLE} value to the list of style classes
     */
    public void mark() {
        this.getStyleClass().add(MARKED_CHECK_STYLE);
        isMarked = true;
    }

    /**
     * Remove {@value MARKED_CHECK_STYLE} value to the list of style classes
     */
    public void unmark() {
        this.getStyleClass().remove(MARKED_CHECK_STYLE);
        isMarked = false;
    }

    /**
     * Add or remove the {@value MARKED_CHECK_STYLE} value to the list of style classes
     * 
     * @param shouldMark shouldMark (true = {@link PredictCheck#mark()}; false = {@link PredictCheck#unmark()})
     */
    public void setMarked(boolean shouldMark) {
        if(shouldMark)
            mark();
        else
            unmark();
    }

    /**
     * Get {@link Boolean} denoting whether or not the instance is marked
     */
    public boolean isMarked() {
        return isMarked;
    }


}
