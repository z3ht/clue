package mines.zinno.clue.util;

/**
 * {@link ThrowableFunction} works like {@link java.util.function.Function} but is capable of throwing an exception
 *
 * @param <T>   Exception type
 * @param <U>   Input type
 * @param <V>   Output type
 */
@FunctionalInterface
public interface ThrowableFunction<T extends Exception, U, V> {
    V apply(U u) throws T;
}
