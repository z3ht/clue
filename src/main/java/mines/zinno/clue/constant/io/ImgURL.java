package mines.zinno.clue.constant.io;

import mines.zinno.clue.Main;

import java.net.URL;

/**
 * The {@link ImgURL} enum holds links to images
 */
public enum ImgURL {
    
    CROSS(Main.class.getResource("/imgs/cross.png")),
    CHECK(Main.class.getResource("/imgs/check.png")),
    BOARD(Main.class.getResource("/imgs/board.jpeg")),
    BASIC_PLACE(Main.class.getResource("/imgs/basicplace.png")),
    PLACE(Main.class.getResource("/imgs/place.png")),
    ROOM_PLACE(Main.class.getResource("/imgs/roomplace.png")),
    DOOR_PLACE(Main.class.getResource("/imgs/door.png")),
    TELEPORT_PLACE(Main.class.getResource("/imgs/teleportplace.png"))
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
