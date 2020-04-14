package mines.zinno.clue.util.handler.exception;

import mines.zinno.clue.util.handler.data.SenderData;

public class HandleNotFound extends RuntimeException {

    public HandleNotFound(SenderData senderData) {
        super(
                String.format(
                        "The handle with type: %s and id: %s could not be found.\n" +
                                "Does it exist? Has its identifying annotation been added?",
                        senderData.handlerCaller,
                        senderData.providedID
                )
        );
    }

}
