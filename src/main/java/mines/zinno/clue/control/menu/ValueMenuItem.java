package mines.zinno.clue.control.menu;

import javafx.scene.control.MenuItem;

/**
 * The {@link ValueMenuItem} is a subclass of JavaFX's {@link MenuItem}. It stores information in the form of an enum
 * of type {@link T} for the menu item and is used by the {@link SelectableMenu} class.
 * 
 * @param <T> Enum type
 */
public class ValueMenuItem<T extends Enum<T>> extends MenuItem {
    
    private final T value;

    public ValueMenuItem(String text, T value) {
        super(text);
        this.value = value;
    }

    /**
     * Get the {@link T} value
     */
    public T getValue() {
        return value;
    }
}
