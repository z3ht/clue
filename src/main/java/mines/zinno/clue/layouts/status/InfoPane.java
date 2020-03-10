package mines.zinno.clue.layouts.status;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import mines.zinno.clue.stages.dialogue.InfoDialogue;

import java.util.List;

/**
 * The {@link InfoPane} class is a subclass of JavaFX's {@link GridPane} class. It is used to provide players with a
 * full picture of what is happening in the game. The {@link InfoPane} holds and displays multiple {@link Info} values.
 * {@link InfoPane}s are displayed using the {@link InfoDialogue} class.
 */
public class InfoPane extends GridPane implements ListChangeListener<Info> {
    
    public final static int WIDTH = 450;
    public final static int HEIGHT = 25;
    public final static int HGAP = 5;
    public final static int PADDING = 15;
    
    private ObservableList<Info> infos;

    /**
     * Create a info pane
     */
    public InfoPane() {
        infos = FXCollections.observableArrayList();

        this.setHgap(HGAP);
        this.paddingProperty().set(new Insets(PADDING));
        
        infos.addListener(this);
    }

    /**
     * Get all {@link Info} currently held in this {@link InfoPane}
     * 
     * @return {@link List}<{@link Info}> contained within this {@link InfoPane}
     */
    public List<Info> getInfos() {
        return infos;
    }

    /**
     * Called by JavaFX's ObservableList listener when a change in the {@link List}<{@link Info}> is detected. Displays
     * all changes to the live {@link InfoPane} as they occur.
     * 
     * @param c Change
     */
    @Override
    public void onChanged(Change c) {
        // Move to the current change
        c.next();
        
        if(c.wasAdded()) {
            onAdd(c);
        } else {
            if(infos.size() == 0)
                return;
            onRemove(c);
        }
    }
    
    private void onAdd(Change c) {
        this.setPrefSize(WIDTH, infos.size() * HEIGHT);

        this.getRowConstraints().add(new RowConstraints((infos.size())*(HEIGHT-HGAP)));
        for(int i = infos.size()-1; i >= infos.size() - c.getAddedSize(); i--) {
            this.add(infos.get(i), 0, i);
        }
    }
    
    private void onRemove(Change c) {
        Info[] newList = infos.toArray(new Info[0]);
        infos.clear();
        this.getChildren().clear();
        infos.addAll(newList);
    }
}
