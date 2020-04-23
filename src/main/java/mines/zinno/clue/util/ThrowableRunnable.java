package mines.zinno.clue.util;

/**
 * The {@link ThrowableRunnable}<{@link T}> works like the {@link Runnable} but throws an exception of type {@link T}
 *
 * @param <T> Exception type
 */
@FunctionalInterface
public interface ThrowableRunnable<T extends Exception> {

    void run() throws T;

}
