package mines.zinno.clue.shape.character.handler;

import javafx.application.Platform;
import mines.zinno.clue.shape.character.listener.OnTurnListener;
import mines.zinno.clue.shape.character.Character;
import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.handler.basic.BasicHandler;
import mines.zinno.clue.util.handler.basic.ExecuteHandler;
import mines.zinno.clue.util.handler.exception.ContextNotSupported;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The {@link mines.zinno.clue.shape.character.handler.GuessHandler} class extends the {@link BasicHandler} class and
 * implements the {@link OnTurnListener} interface. It holds all guess related handles characters reference in the game
 * of Clue
 */
@SuppressWarnings("unused")
public class GuessHandler extends BasicHandler implements OnTurnListener<Character> {

    public GuessHandler() {}

    public GuessHandler(Object... handles) {
        super(handles);
    }

    public GuessHandler(boolean inheritHandles, Object... handles) {
        super(inheritHandles, handles);
    }

    // Synchronize updates with the Platform thread (helps with displaying dialogues and making sure all updates
    // that should be sent will be sent)
    private boolean isUpdating;

    @Override
    public Handler withContext(Object... context) {
        throw new ContextNotSupported();
    }

    /**
     * This method should be called instead of the {@link Handler#withContext(Object...)} method as it represents
     * the context this handler supports (no context)
     * @return this {@link Handler}
     */
    public Handler withContext() {
        return this;
    }

    @Override
    protected void addContextToCall(List<Object> curArgs) {}

    @Override
    public void update(Character character) {
        // Delay allows all slower insert handles to insert into the map data before displaying
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> this.get(ExecuteHandler.class).execute(Handler.ALL, character), 100, TimeUnit.MILLISECONDS);
    }
}
