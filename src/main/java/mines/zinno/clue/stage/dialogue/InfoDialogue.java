package mines.zinno.clue.stage.dialogue;

import mines.zinno.clue.controller.StatusController;
import mines.zinno.clue.constant.io.FXMLURL;

/**
 * The {@link InfoDialogue} displays {@link mines.zinno.clue.layout.status.InfoPane}s
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
