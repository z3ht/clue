package mines.zinno.clue.dialogues;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mines.zinno.clue.Game;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Dialogue<T> {
    
    private String name; 
    private URL dialogueURL;
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
    }
    
    private void initialize() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(dialogueURL);

            Parent root = fxmlLoader.load();

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

    public Stage getStage() {
        return stage;
    }

    public T getController() {
        return controller;
    }
    
    
    
}
