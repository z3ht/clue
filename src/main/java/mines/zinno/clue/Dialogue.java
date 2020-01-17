package mines.zinno.clue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import mines.zinno.clue.Game;
import mines.zinno.clue.control.menu.SelectableMenu;
import mines.zinno.clue.control.menu.ValueMenuItem;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;
import java.util.logging.Level;

public class Dialogue<T> {
    
    private String name; 
    private URL dialogueURL;
    private Parent root;
    private T controller;
    private Dimension size;
    private Stage stage;
    
    public Dialogue(String name, URL dialogueURL, Dimension size) {
        this(name, dialogueURL, size, new Stage());
    }
    
    public Dialogue(String name, URL dialogueURL, Dimension size, Stage stage) {
        this.name = name;
        this.dialogueURL = dialogueURL;
        this.size = size;
        this.stage = stage;
        
        initialize();
        this.stage.setOnShown(event -> addListeners());
    }
    
    private void initialize() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(dialogueURL);

            this.root = fxmlLoader.load();

            this.controller = fxmlLoader.getController();

            Scene scene = new Scene(root, size.getWidth(), size.getHeight());

            stage.setTitle(name);
            stage.setResizable(false);
            stage.setScene(scene);
        } catch (IOException e) {
            Game.getLOGGER().warning("Failed to load FXML file at: " + dialogueURL.toExternalForm());
            e.printStackTrace();
        }
    }
    
    private void applyAllChildren(Parent parent, Consumer<Node> consumer) {
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
    
    protected void addListeners() {
        applyAllChildren(root, node -> {
            if(!(node instanceof Button))
                return;
            if (!((Button) node).isCancelButton())
                return;

            node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stage.close());
        });
        
        applyAllChildren(root, node -> {
            if(!(node instanceof Hyperlink))
                return;
            Hyperlink link = (Hyperlink) node;
            link.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    Desktop.getDesktop().browse(new URI(link.getText()));
                } catch (IOException | URISyntaxException e) {
                    Game.getLOGGER().log(Level.WARNING, "Hyperlink could not be opened");
                }
            });
        });
        
        applyAllChildren(root, node -> {
            
            if(!(node instanceof SelectableMenu))
                return;

            SelectableMenu menuButton = (SelectableMenu) node;
            
            if(menuButton.isLocked())
                return;
            
            for(MenuItem menuItem : menuButton.getItems()) {
                if(!(menuItem instanceof ValueMenuItem))
                    continue;
                menuItem.setOnAction(event -> menuButton.setSelectedItem((ValueMenuItem) menuItem));
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public T getController() {
        return controller;
    }
    
}
