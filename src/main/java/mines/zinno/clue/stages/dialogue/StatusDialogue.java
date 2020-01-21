package mines.zinno.clue.stages.dialogue;

import mines.zinno.clue.controllers.StatusController;
import mines.zinno.clue.enums.FXMLURL;

public class StatusDialogue extends Dialogue<StatusController> {
    
    public StatusDialogue(String name) {
        super(name, FXMLURL.STATUS.getUrl());
    }
    
    public void setSize() {
        this.setWidth((int) this.getController().getStatusPane().getPrefWidth());
        this.setHeight(MIN_SIZE + this.getController().getStatusPane().getPrefHeight());
    }
    
}
