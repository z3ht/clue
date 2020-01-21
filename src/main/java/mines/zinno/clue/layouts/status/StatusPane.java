package mines.zinno.clue.layouts.status;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class StatusPane extends GridPane implements ListChangeListener<Pane> {
    
    public final static int WIDTH = 450;
    public final static int HEIGHT = 25;
    public final static int HGAP = 5;
    public final static int PADDING = 15;
    
    private final ObservableList<Pane> statuses;
    
    public StatusPane() {
        statuses = FXCollections.observableArrayList();

        this.setHgap(HGAP);
        this.paddingProperty().set(new Insets(PADDING));
        
        statuses.addListener(this);
    }

    public List<Pane> getStatuses() {
        return statuses;
    }

    @Override
    public void onChanged(Change c) {
        
        c.next();
        
        if(c.wasAdded()) {
            onAdd(c);
        } else {
            if(statuses.size() == 0)
                return;
            onRemove(c);
        }
    }
    
    private void onAdd(Change c) {
        this.setPrefSize(WIDTH, statuses.size() * HEIGHT);

        this.getRowConstraints().add(new RowConstraints((statuses.size())*(HEIGHT-HGAP)));
        for(int i = statuses.size()-1; i >= statuses.size() - c.getAddedSize(); i--) {
            this.add(statuses.get(i), 0, i);
        }
    }
    
    private void onRemove(Change c) {
        Pane[] newList = statuses.toArray(new Pane[0]);
        statuses.clear();
        this.getChildren().clear();
        statuses.addAll(newList);
    }
}
