package mines.zinno.clue.shape.character.handler;

import mines.zinno.clue.shape.character.listener.OnTurnListener;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.handler.basic.BasicHandler;
import mines.zinno.clue.util.handler.basic.ExecuteHandler;
import mines.zinno.clue.util.handler.exception.ContextNotSupported;

import java.util.List;

@SuppressWarnings("unused")
public class GuessHandler extends BasicHandler implements OnTurnListener<Character> {

    public GuessHandler() {}

    public GuessHandler(Object... handles) {
        super(handles);
    }

    public GuessHandler(boolean inheritHandles, Object... handles) {
        super(inheritHandles, handles);
    }

    @Override
    public Handler withContext(Object... context) {
        throw new ContextNotSupported();
    }

    public Handler withContext() {
        return this;
    }

    @Override
    protected void addContextToCall(List<Object> curArgs) {}

    @Override
    public void update(Character character) {
        this.get(ExecuteHandler.class).execute(Handler.ALL, character);
    }
}
