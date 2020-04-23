package mines.zinno.clue.control.menu;

import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mines.zinno.clue.constant.io.ImgURL;
import mines.zinno.clue.stage.dialogue.ShortDialogue;

import java.util.Optional;

/**
 * The {@link SelectableMenu} is a subclass of JavaFX's {@link MenuButton} class. The {@link SelectableMenu} can be
 * used to select and deselect {@link ValueMenuItem}s. This class uses {@link ValueMenuItem}s to store information about
 * menu items in enum values.
 * 
 * @param <T> The {@link ValueMenuItem}'s enum type
 */
public class SelectableMenu<T extends Enum<T>> extends MenuButton {
    
    private boolean isLocked = false;
    private ValueMenuItem<T> selectedItem;

    private String lockMessage = "This menu is locked";

    public SelectableMenu () {
        super();
    }

    public SelectableMenu (String text, Node graphic, ValueMenuItem<T>... items) {
        this(text, graphic, false, items);
    }

    public SelectableMenu (String text, Node graphic, boolean isLocked, ValueMenuItem<T>... items) {
        super(text, graphic, items);

        this.isLocked = isLocked;
    }

    /**
     * Get an {@link Optional} containing the provided {@link T} value or an {@link Optional#empty()} if no
     * {@link ValueMenuItem} is selected.
     * 
     * Note: The {@link ValueMenuItem} may still provide a null selected value
     */
    public Optional<T> getSelected() {
        return (selectedItem == null) ? Optional.empty() : Optional.of(selectedItem.getValue());
    }

    /**
     * Set the selected {@link ValueMenuItem} using the {@link ValueMenuItem}'s selected value
     * 
     * @param selectedValue Selected value
     */
    public void setSelectedItem(T selectedValue) {
        for(MenuItem menuItem : this.getItems()) {
            if (menuItem instanceof ValueMenuItem && ((ValueMenuItem<T>) menuItem).getValue() == selectedValue) {
                setSelectedItem((ValueMenuItem<T>) menuItem);
                return;
            }
        }
        // Set selected item to null if nothing is found
        setSelectedItem((ValueMenuItem<T>) null);
    }

    /**
     * Set the selected {@link ValueMenuItem} 
     *
     * @param selectedItem Selected {@link ValueMenuItem}
     */
    public void setSelectedItem(ValueMenuItem<T> selectedItem) {
        // Reset menu items
        for(MenuItem menuItem : this.getItems())
            menuItem.setGraphic(null);
        
        // Set selected
        this.selectedItem = selectedItem;
        if(selectedItem == null)
            return;

        // Draw selected graphic 
        ImageView imageView = new ImageView(ImgURL.CHECK.getUrl().toExternalForm());
        imageView.setFitWidth(15);
        imageView.setFitHeight(15);
        selectedItem.setGraphic(imageView);
    }

    /**
     * Returns a {@link Boolean} denoting whether or not this menu is isLocked
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Display the lock message when a isLocked menu is selected
     */
    public void displayLockMessage() {
        new ShortDialogue("Locked", this.getLockMessage()).show();
    }


    /**
     * Get the lock message
     *
     * @return {@link String}
     */
    public String getLockMessage() {
        return lockMessage;
    }

    /**
     * Set the lock message
     *
     * @param lockMessage {@link String}
     */
    public void setLockMessage(String lockMessage) {
        this.lockMessage = lockMessage;
    }

    /**
     * Set menu lock
     * 
     * @param isLocked {@link Boolean}
     */
    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
