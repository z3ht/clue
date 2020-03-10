package mines.zinno.clue.stage.dialogue;

import mines.zinno.clue.layout.status.Info;


public class BasicInfoDialogue extends InfoDialogue {
    
    /**
     * Create a default {@link InfoDialogue}
     *
     * @param name The name of the dialogue
     * @param text The text on the dialogue
     */
    public BasicInfoDialogue(String name, String text) {
        super(name);
        this.getController().getInfoPane().getInfos().add(new Info(text));
        this.setSize();
    }
}
