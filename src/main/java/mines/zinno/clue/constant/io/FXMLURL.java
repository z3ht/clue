package mines.zinno.clue.constant.io;

import mines.zinno.clue.Main;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The {@link FXMLURL} enum holds links to FXML files
 */
public enum FXMLURL {
    
    CLUE("Clue", Main.class.getResource("/fxml/Clue.fxml")),
    SHORT_DIALOGUE("Info", Main.class.getResource("/fxml/ShortDialogue.fxml")),
    SCROLLABLE_DIALOGUE("Info", Main.class.getResource("/fxml/ScrollableDialogue.fxml")),
    SETTINGS("Settings", Main.class.getResource("/fxml/Settings.fxml")),
    HELP("Help", Main.class.getResource("/fxml/Help.fxml")),
    GUESS("Guess", Main.class.getResource("/fxml/Guess.fxml")),
    CUSTOM_BOARD("Custom Board", Main.class.getResource("/fxml/CustomBoard.fxml")),
    BOARD("Board", Main.class.getResource("/board.csv"));
    ;

    /**
     * The PARSE {@link Function}<{@link InputStream}, {@link String}> converts text {@link InputStream}s to
     * String arrays split at newline characters
     */
    public final static Function<InputStream, String[]> PARSE = (stream) -> {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String rawMap = s.hasNext() ? s.next() : "";
        return rawMap.split("\\r?\\n");
    };

    private final String name;
    private final URL url;
    
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
