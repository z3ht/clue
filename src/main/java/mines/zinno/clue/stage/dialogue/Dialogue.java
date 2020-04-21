package mines.zinno.clue.stage.dialogue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import mines.zinno.clue.constant.Alert;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.control.menu.ValueMenuItem;
import mines.zinno.clue.constant.io.LogMessage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * The {@link Dialogue} class extends JavaFX's {@link Stage} class. It makes creating a dialogue window from an FXML file
 * simple.
 * 
 * @param <T> Controller class type
 */
public class Dialogue<T> extends Stage {
    
    final static int MIN_SIZE = 155;
    private final static Dimension DEFAULT_SIZE = new Dimension(200, 200);
    
    private T controller;
    protected Parent root;

    /**
     * Create a dialogue window
     * 
     * @param name Name of the dialogue box
     * @param dialogueURL FXML URL
     */
    public Dialogue(String name, URL dialogueURL) {
        this(name, dialogueURL, DEFAULT_SIZE);
    }

    /**
     * Create a dialogue window
     *
     * @param name Name of the dialogue box
     * @param fxmlURL FXML URL
     * @param size Size of the dialogue window
     */
    public Dialogue(String name, URL fxmlURL, Dimension size) {
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
        try {
            this.root = fxmlLoader.load();

            this.controller = fxmlLoader.getController();

            this.setScene(new Scene(root, size.getWidth(), size.getHeight()));
        } catch (IOException e) {
            LogMessage.URL_NOT_FOUND.log(fxmlURL.toExternalForm());
            e.printStackTrace();
        }

        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setTitle(name);
        
        this.setOnShown(event -> addListeners());
    }

    /**
     * Recursive function that applies a consumer function to all children nodes. Used by {@link Dialogue#addListeners()}
     */
    void applyAllChildren(Parent parent, Consumer<Node> consumer) {
        if(parent == null || consumer == null)
            return;
        for(Node child : parent.getChildrenUnmodifiable()) {
            if(child instanceof Parent) {
                Parent newParent = (Parent) child;
                if(newParent.getChildrenUnmodifiable().size() != 0)
                    applyAllChildren(newParent, consumer);
            }
            consumer.accept(child);
        }
    }

    /**
     * Listeners used by all dialogues
     */
    protected void addListeners() {
        // Close dialogue window if a cancel button is clicked
        applyAllChildren(this.getScene().getRoot(), node -> {
            if(!(node instanceof Button))
                return;
            if (!((Button) node).isCancelButton())
                return;

            node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.close());
        });
        
        // Support for selecting menu items in selectable menus
        applyAllChildren(root, node -> {
            
            if(!(node instanceof SelectableMenu))
                return;

            SelectableMenu menuButton = (SelectableMenu) node;
            
            if(menuButton.isLocked()) 
                return;
            
            for(MenuItem menuItem : menuButton.getItems()) {
                if(!(menuItem instanceof ValueMenuItem))
                    continue;
                menuItem.setOnAction((event -> menuButton.setSelectedItem((ValueMenuItem) menuItem)));
            }
        });
    }

    /**
     * Get the dialogue window's controller
     */
    public T getController() {
        return controller;
    }
}
