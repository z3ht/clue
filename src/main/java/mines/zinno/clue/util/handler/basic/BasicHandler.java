package mines.zinno.clue.util.handler.basic;

import mines.zinno.clue.util.handler.Handler;

public class BasicHandler extends Handler {

    public BasicHandler() {
        super(true, new ExecuteHandler(), new InsertHandler());

        super.addIdentifyingAnnotation(BasicHandle.class,
                (senderData, method) ->
                        senderData.handlerCaller == method.type() &&
                                (senderData.providedID.equals("") || senderData.providedID.equals(method.id())));
    }

}