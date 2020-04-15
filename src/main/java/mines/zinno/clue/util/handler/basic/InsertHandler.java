package mines.zinno.clue.util.handler.basic;

import mines.zinno.clue.util.handler.Handler;
import mines.zinno.clue.util.handler.data.SenderData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InsertHandler extends Handler {

    public void insert(Object id, Object... args) {

        for(Method handle : super.getMethodHandles(new SenderData(this.getClass(), id))) {
            try {
                super.call(handle, args);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
