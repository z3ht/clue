package mines.zinno.clue.enums.io;

import mines.zinno.clue.layouts.board.ClueBoard;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Function;

public enum TxtStream {
    
    BOARD(ClueBoard.class.getResourceAsStream("/board.txt"));
    
    public final static Function<InputStream, String[]> PARSE = (stream) -> {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String rawMap = s.hasNext() ? s.next() : "";
        return rawMap.split("\\r?\\n");
    };
    
    private InputStream inputStream;

    TxtStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
