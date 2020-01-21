package mines.zinno.clue.layouts.status;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Status<T extends Enum<T>> extends Pane {
    
    private T type;
    
    public Status(T type, Object... args) {
        this.type = type;
        
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

    public T getType() {
        return type;
    }
}
