package mines.zinno.clue.enums;

import java.net.URL;

public enum ImgURL {
    
    CROSS(Object.class.getResource("/imgs/cross.png")),
    CHECK(Object.class.getResource("/imgs/check.png")),
    ;
    
    private URL url;

    ImgURL(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
    
}
