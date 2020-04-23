package mines.zinno.clue.util.handler.basic;

import mines.zinno.clue.util.handler.Handler;

/**
 * The {@link BasicHandler} class extends the {@link Handler} class. It is a very basic handler that supports
 * the {@link BasicHandle} annotation by default.
 */
public class BasicHandler extends Handler {

    public BasicHandler() {
        super();

        initialize();
    }

    public BasicHandler(Object... handles) {
        super(handles);

        initialize();
    }

    public BasicHandler(boolean inheritHandles, Object... handles) {
        super(inheritHandles, handles);

        initialize();
    }

    private void initialize() {
        super.install(new ExecuteHandler());
        super.install(new InsertHandler());
        super.addIdentifyingAnnotation(BasicHandle.class,
                (senderData, method) ->
                        senderData.handlerCaller == method.type() &&
                                ("".equals(senderData.providedID) ||
                                        Handler.ALL.equals(senderData.providedID) ||
                                        method.id().equals(senderData.providedID)));
    }

}