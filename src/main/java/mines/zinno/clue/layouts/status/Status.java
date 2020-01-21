package mines.zinno.clue.layouts.status;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Status extends Pane {
    
    public Status(String text) {
        Label label = generateLabel(text, "alert");
        this.getChildren().add(label);
    }
    
    public Status(Enum<?> type, Object... args) {
        Label label = generateLabel(String.format(type.toString(), args), type.getClass().getSimpleName().toLowerCase());
        this.getChildren().add(label);
    }

    protected Label generateLabel(String text, String styleClass) {
        Label label = new Label(text);
        label.setPrefWidth(StatusPane.WIDTH - StatusPane.PADDING*2);
        label.wrapTextProperty().set(true);
        label.getStyleClass().add(styleClass);
        return label;
    }
}
