package mines.zinno.clue.enums.io;

import java.net.URL;

public enum FXMLURL {
    
    CLUE(Object.class.getResource("/fxml/Clue.fxml")),
    STATUS(Object.class.getResource("/fxml/Status.fxml"))
    
    ;

    private URL url;
    
    FXMLURL(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}
