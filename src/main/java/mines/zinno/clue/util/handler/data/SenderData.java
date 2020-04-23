package mines.zinno.clue.util.handler.data;

import mines.zinno.clue.util.handler.Handler;

/**
 * The {@link SenderData} data class holds basic information sent in calls to {@link Handler}s
 */
public class SenderData {

    public Class<? extends Handler> handlerCaller;

    public Object providedID;

    public SenderData(Class<? extends Handler> handlerCaller, Object providedID) {
        this.handlerCaller = handlerCaller;
        this.providedID = providedID;
    }
}
