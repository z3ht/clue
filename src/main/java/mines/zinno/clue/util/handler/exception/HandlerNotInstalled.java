package mines.zinno.clue.util.handler.exception;

import mines.zinno.clue.util.handler.Handler;

/**
 * Thrown when a handler referenced is not installed. Handlers can be installed using the {@link Handler#install(Handler)}
 * method
 */
public class HandlerNotInstalled extends RuntimeException {

    public HandlerNotInstalled(Class<?> clazz) {
        super(String.format("A %s has not been installed on this handler", clazz.getName()));
    }

}
