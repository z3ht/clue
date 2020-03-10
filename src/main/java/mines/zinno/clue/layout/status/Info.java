package mines.zinno.clue.layout.status;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The {@link Info} class is a subclass of JavaFX's {@link Pane} class. It holds alert and status messages that will be
 * used by {@link InfoPane} to provide a full picture of what is happening in the game.
 */
public class Info extends Pane {
    
    private static String DEFAULT_STYLE_CLASS = "alert";

    /**
     * Create a custom alert
     * 
     * @param text Alert text
     */
    public Info(String text) {
        Label label = generateLabel(text, DEFAULT_STYLE_CLASS);
        this.getChildren().add(label);
    }

    /**
     * Create an alert from an enum value
     * 
     * @param type Enum value
     * @param args Wildcard values
     */
    public Info(Enum<?> type, Object... args) {
        Label label = generateLabel(String.format(type.toString(), args), type.getClass().getSimpleName().toLowerCase());
        this.getChildren().add(label);
    }

    /**
     * Generate alert label
     */
    protected Label generateLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.setPrefWidth(InfoPane.WIDTH - InfoPane.PADDING*2);
        label.wrapTextProperty().set(true);
        label.getStyleClass().add(styleClass);
        return label;
    }
}
