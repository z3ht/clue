package mines.zinno.clue.stages.dialogue;

import mines.zinno.clue.controllers.StatusController;
import mines.zinno.clue.enums.io.FXMLURL;

/**
 * The {@link InfoDialogue} displays {@link mines.zinno.clue.layouts.status.InfoPane}s
 */
public class InfoDialogue extends Dialogue<StatusController> {

    /**
     * Create a default {@link InfoDialogue}
     * 
     * @param name The name of the dialogue
     */
    public InfoDialogue(String name) {
        super(name, FXMLURL.STATUS.getUrl());
        
        this.setAlwaysOnTop(true);
    }

    /**
     * Set the size of the dialogue window
     */
    public void setSize() {
        this.setWidth((int) this.getController().getInfoPane().getPrefWidth());
        this.setHeight(MIN_SIZE + this.getController().getInfoPane().getPrefHeight());
    }
    
}
