package mines.zinno.clue.shape.character.handler.identifier;

import mines.zinno.clue.shape.character.constant.RevealContext;
import mines.zinno.clue.util.handler.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RevealHandle {

    Class<? extends Handler> type();

    RevealContext id();

}
