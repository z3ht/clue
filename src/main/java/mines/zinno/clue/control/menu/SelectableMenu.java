package mines.zinno.clue.control.menu;

import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import mines.zinno.clue.Game;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.logging.Level;

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

    public ValueMenuItem<T> getSelectedItem() {
        return selectedItem;
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
        this.selectedItem = null;
        if(selectedItem == null)
            return;
        try {
            ImageView imageView = new ImageView(Object.class.getResource("/imgs/check.png").toURI().toURL().toString());
            imageView.setFitWidth(15);
            imageView.setFitHeight(15);
            selectedItem.setGraphic(imageView);
            System.out.println(selectedItem.getGraphic());
        } catch (MalformedURLException | URISyntaxException e) {
            Game.getLOGGER().log(Level.WARNING, "The check.png file could not be found");
            e.printStackTrace();
        }
        this.selectedItem = selectedItem;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
