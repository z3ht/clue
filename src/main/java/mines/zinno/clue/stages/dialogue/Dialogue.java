package mines.zinno.clue.stages.dialogue;

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
import mines.zinno.clue.enums.LogMessage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;

public class Dialogue<T> extends Stage {
    
    final static int MIN_SIZE = 125;
    private final static Dimension DEFAULT_SIZE = new Dimension(200, 200);
    
    private T controller;
    private Parent root;
    
    public Dialogue(String name, URL dialogueURL) {
        this(name, dialogueURL, DEFAULT_SIZE);
    }
    
    public Dialogue(String name, URL dialogueURL, Dimension size) {
        FXMLLoader fxmlLoader = new FXMLLoader(dialogueURL);
        try {
            this.root = fxmlLoader.load();

            this.controller = fxmlLoader.getController();

            this.setScene(new Scene(root, size.width, size.getHeight()));
        } catch (IOException e) {
            LogMessage.URL_NOT_FOUND.log(dialogueURL.toExternalForm());
            e.printStackTrace();
        }
        
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setTitle(name);
        
        this.setOnShown(event -> addListeners());
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
        applyAllChildren(this.getScene().getRoot(), node -> {
            if(!(node instanceof Button))
                return;
            if (!((Button) node).isCancelButton())
                return;

            node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.close());
        });
        
        applyAllChildren(root, node -> {
            if(!(node instanceof Hyperlink))
                return;
            Hyperlink link = (Hyperlink) node;
            link.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    Desktop.getDesktop().browse(new URI(link.getText()));
                } catch (IOException | URISyntaxException e) {
                    LogMessage.HYPERLINK_NOT_FOUND.log(link);
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

    public T getController() {
        return controller;
    }

    public Parent getRoot() {
        return root;
    }
}
