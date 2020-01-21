package mines.zinno.clue.layouts.status.enums;

//TODO add font parsing enum to allow for in line bolding and coloring

public enum Action {
    
    GUESS("%s guessed %s committed the murder in the %s using the %s"),
    ANONYMOUS_CLUE("\t- %s showed %s a card"),
    CLUE("%s revealed %s to you")
    ;
    
    private String text;
    
    Action(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return getText();
    }

    public String getText() {
        return text;
    }
}
