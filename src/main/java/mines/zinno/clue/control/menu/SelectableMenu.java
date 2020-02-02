package mines.zinno.clue.control.menu;

import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import mines.zinno.clue.enums.io.ImgURL;

import java.util.Optional;

public class SelectableMenu<T extends Enum<T>> extends MenuButton {
    
    private boolean isLocked = false;
    private ValueMenuItem<T> selectedItem;
    
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

    public Optional<T> getSelected() {
        return (selectedItem == null) ? Optional.empty() : Optional.of(selectedItem.getValue());
    }

    public void setSelectedItem(T selectedValue) {
        for(MenuItem menuItem : this.getItems()) {
            if (menuItem instanceof ValueMenuItem && ((ValueMenuItem<T>) menuItem).getValue() == selectedValue) {
                setSelectedItem((ValueMenuItem<T>) menuItem);
                return;
            }
        }
        setSelectedItem((ValueMenuItem<T>) null);
    }

    public void setSelectedItem(ValueMenuItem<T> selectedItem) {
        for(MenuItem menuItem : this.getItems())
            menuItem.setGraphic(null);
        this.selectedItem = selectedItem;
        if(selectedItem == null)
            return;

        ImageView imageView = new ImageView(ImgURL.CHECK.getUrl().toExternalForm());
        imageView.setFitWidth(15);
        imageView.setFitHeight(15);
        selectedItem.setGraphic(imageView);
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
