package mines.zinno.clue.exception;

/**
 * The {@link BadMapFormatException} is called when a map is provided that can not
 * be parsed or validated correctly.
 */
public class BadMapFormatException extends Exception {
    
    private static final String name = "Invalid Board Format";
    
    public BadMapFormatException(String message) {
        super(message);
    }

    /**
     * Get the name of the exception in {@link String} format
     */
    public static String getName() {
        return name;
    }
    
}
