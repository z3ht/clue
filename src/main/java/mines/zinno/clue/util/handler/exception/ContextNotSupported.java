package mines.zinno.clue.util.handler.exception;

public class ContextNotSupported extends RuntimeException {

    public ContextNotSupported(){
        super("The context provided is not supported by this handler");
    }

}
