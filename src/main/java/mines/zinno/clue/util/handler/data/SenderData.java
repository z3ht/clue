package mines.zinno.clue.util.handler.data;

import mines.zinno.clue.util.handler.Handler;

public class SenderData {

    public Class<? extends Handler> handlerCaller;

    public Object providedID;

    public SenderData(Class<? extends Handler> handlerCaller, Object providedID) {
        this.handlerCaller = handlerCaller;
        this.providedID = providedID;
    }
}
