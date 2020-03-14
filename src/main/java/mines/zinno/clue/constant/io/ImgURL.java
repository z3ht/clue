package mines.zinno.clue.constant.io;

import java.net.URL;

/**
 * The {@link ImgURL} enum holds links to images
 */
public enum ImgURL {
    
    CROSS(Object.class.getResource("/imgs/cross.png")),
    CHECK(Object.class.getResource("/imgs/check.png")),
    BOARD(Object.class.getResource("/imgs/board.jpeg")),
    BASIC_PLACE(Object.class.getResource("/imgs/basicplace.png")),
    PLACE(Object.class.getResource("/imgs/place.png")),
    ROOM_PLACE(Object.class.getResource("/imgs/roomplace.png")),
    DOOR_PLACE(Object.class.getResource("/imgs/door.png")),
    TELEPORT_PLACE(Object.class.getResource("/imgs/teleportplace.png"))
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
