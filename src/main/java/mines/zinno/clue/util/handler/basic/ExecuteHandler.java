package mines.zinno.clue.util.handler.basic;

import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.handler.data.SenderData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecuteHandler extends Handler {

    public void execute(Object id, Object... args) {

        Method handle = super.getHandle(new SenderData(this.getClass(), id));

        try {
            handle.invoke(this, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
