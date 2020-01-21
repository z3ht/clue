package mines.zinno.clue.stages.dialogue;

import mines.zinno.clue.controllers.StatusController;

public class StatusDialogue extends Dialogue<StatusController> {
    
    public StatusDialogue(String name) {
        super(name, Object.class.getResource("/fxml/Status.fxml"));
    }
    
    public void setSize() {
        this.setWidth((int) this.getController().getStatusPane().getPrefWidth());
        this.setHeight(125 + this.getController().getStatusPane().getPrefHeight());
    }
    
}
