package mines.zinno.clue.util.handler.exception;

public class HandlerNotInstalled extends RuntimeException {

    public HandlerNotInstalled(Class<?> clazz) {
        super(String.format("A %s has not been installed on this handler", clazz.getName()));
    }

}
