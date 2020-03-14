package mines.zinno.clue.constant.io;

import mines.zinno.clue.layout.board.ClueBoard;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The {@link FileStream} enum converts {@link InputStream}s to {@link java.util.List><{@link String}>
 */
public enum FileStream {
    
    BOARD(ClueBoard.class.getResourceAsStream("/board.csv"));

    /**
     * The PARSE {@link Function}<{@link InputStream}, {@link String}> converts text {@link InputStream}s to
     * String arrays split at newline characters
     */
    public final static Function<InputStream, String[]> PARSE = (stream) -> {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String rawMap = s.hasNext() ? s.next() : "";
        return rawMap.split("\\r?\\n");
    };
    
    private InputStream inputStream;

    FileStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Get {@link InputStream}
     * @return {@link InputStream}
     */
    public InputStream getInputStream() {
        return inputStream;
    }
}
