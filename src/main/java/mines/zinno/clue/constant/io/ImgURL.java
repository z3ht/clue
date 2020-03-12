package mines.zinno.clue.constant.io;

import java.net.URL;

/**
 * The {@link ImgURL} enum holds links to images
 */
public enum ImgURL {
    
    CROSS(Object.class.getResource("/imgs/cross.png")),
    CHECK(Object.class.getResource("/imgs/check.png")),
    BOARD(Object.class.getResource("/imgs/board.jpeg"))
    ;
    
    private URL url;

    ImgURL(URL url) {
        this.url = url;
    }

    /**
     * Get img {@link URL}
     */
    public URL getUrl() {
        return url;
    }
    
}
