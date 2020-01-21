package mines.zinno.clue.shapes.character;

import javafx.scene.shape.Circle;
import mines.zinno.clue.enums.Suspect;

public abstract class Character extends Circle {
    
    private static final int NUM_DICE = 1;
    
    private Suspect suspect;
    protected int roll;
    
    public Character(Suspect suspect) {
        this.suspect = suspect;
        
        
    }
    
    public abstract void move();

    public int roll() {
        int rollNum = 0;
        for(int i = 0; i < NUM_DICE; i++)
            rollNum += Math.random() * 6 + 1;
        this.roll = rollNum;
        return rollNum;
    }
    
    public Suspect getSuspect() {
        return suspect;
    }

    public void setSuspect(Suspect suspect) {
        this.suspect = suspect;
    }
}
