package mines.zinno.clue.exception;

public class BadMapFormatException extends Exception {
    
    private static final String name = "Invalid Board Format";
    
    public BadMapFormatException(String message) {
        super(message);
    }
    
    public static String getName() {
        return name;
    }
    
}
