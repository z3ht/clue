package mines.zinno.clue.constant.io;

import java.net.URL;

/**
 * The {@link FXMLURL} enum holds links to FXML files
 */
public enum FXMLURL {
    
    CLUE("Clue", Object.class.getResource("/fxml/Clue.fxml")),
    STATUS("Status", Object.class.getResource("/fxml/Status.fxml")),
    SETTINGS("Settings", Object.class.getResource("/fxml/Settings.fxml")),
    HELP("Help", Object.class.getResource("/fxml/Help.fxml")),
    GUESS("Guess", Object.class.getResource("/fxml/Guess.fxml")),
    CUSTOM_BOARD("Custom Board", Object.class.getResource("/fxml/CustomBoard.fxml"))
    ;

    private String name;
    private URL url;
    
    FXMLURL(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    /**
     * Get Name
     */
    public String getName() {
        return name;
    }

    /**
     * Get FXML {@link URL}
     */
    public URL getUrl() {
        return url;
    }
}
