package mines.zinno.clue.util.handler.basic;

import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.handler.data.SenderData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The {@link ExecuteHandler} is a simple {@link Handler} that can be installed to perform execution tasks
 */
public class ExecuteHandler extends Handler {

    public void execute(Object id, Object... args) {

        for(Method handle : super.getMethodHandles(new SenderData(this.getClass(), id))) {
            try {
                super.call(handle, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

}
