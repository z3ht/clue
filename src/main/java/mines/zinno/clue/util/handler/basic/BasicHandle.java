package mines.zinno.clue.util.handler.basic;

import mines.zinno.clue.util.handler.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BasicHandle {
    
    Class<? extends Handler> type();

    String id() default "";
    
}
