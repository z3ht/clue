package mines.zinno.clue.shape.character.handler.identifier;

import mines.zinno.clue.util.handler.Handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link GuessHandle} annotation identifies handles called when a guess is made
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GuessHandle {

    Class<? extends Handler> type();

}
