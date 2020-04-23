package mines.zinno.clue.util.handler.exception;

import mines.zinno.clue.util.handler.Handler;

/**
 * Thrown when a handler does not support the provided context. Context is provided through
 * the {@link Handler#withContext(Object...)} method
 */
public class ContextNotSupported extends RuntimeException {

    public ContextNotSupported(){
        super("The context provided is not supported by this handler");
    }

}
