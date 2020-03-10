package mines.zinno.clue.listeners;

/**
 * Basic Listener interface
 * 
 * @param <T> Information type
 */
public interface Listener<T> {

    /**
     * Called when an update is made
     * 
     * @param t Information provided on update
     */
    void update(T t);
    
}
