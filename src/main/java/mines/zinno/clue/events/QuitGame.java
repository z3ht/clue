package mines.zinno.clue.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class QuitGame extends Event {
   
    public QuitGame(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public QuitGame(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
