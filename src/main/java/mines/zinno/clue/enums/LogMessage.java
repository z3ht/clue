package mines.zinno.clue.enums;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum LogMessage {
    
    START(Level.INFO, "Clue Game Started"),
    STAGE_POPULATED(Level.INFO, "Stage populated"),
    LISTENERS_ADDED(Level.INFO, "Listeners added"),
    STAGE_SHOWN(Level.INFO, "Stage shown"),
    URL_NOT_FOUND(Level.WARNING, "The dialogue could not be found at URL: %s"),
    HYPERLINK_NOT_FOUND(Level.WARNING, "Unable to open hyperlink at: %s"),
    ;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private Level level;
    private String message;

    LogMessage(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
    
    public void log(Object... args) {
        LOGGER.log(this.getLevel(), String.format(this.getMessage(), args));
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}
