package mines.zinno.clue.control.menu;

import javafx.scene.control.MenuItem;

public class ValueMenuItem<T extends Enum<T>> extends MenuItem {
    
    private final T value;

    public ValueMenuItem(String text, T value) {
        super(text);
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
