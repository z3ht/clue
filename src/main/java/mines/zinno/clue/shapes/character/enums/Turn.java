package mines.zinno.clue.shapes.character.enums;

public enum Turn {
    PRE_ROLL("You have already rolled"),
    POST_ROLL("You must roll before moving"),
    POST_MOVE("You must move before guessing.\nThen press 'continue' or 'skip' to progress"),
    OTHER("It is not your turn to roll");

    private String badTurnMessage;
    
    Turn(String badTurnMessage) {
        this.badTurnMessage = badTurnMessage;
    }
    
    public String getBadTurnMessage() {
        return badTurnMessage;
    }
}
