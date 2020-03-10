package mines.zinno.clue.constant.io;

import java.net.URL;

/**
 * The {@link FXMLURL} enum holds links to FXML files
 */
public enum FXMLURL {
    
    CLUE(Object.class.getResource("/fxml/Clue.fxml")),
    STATUS(Object.class.getResource("/fxml/Status.fxml")),
    SETTINGS(Object.class.getResource("/fxml/Settings.fxml")),
    HELP(Object.class.getResource("/fxml/Help.fxml")),
    GUESS(Object.class.getResource("/fxml/Guess.fxml"))
    ;

    private URL url;
    
    FXMLURL(URL url) {
        this.url = url;
    }

    /**
     * Get FXML {@link URL}
     */
    public URL getUrl() {
        return url;
    }
}
