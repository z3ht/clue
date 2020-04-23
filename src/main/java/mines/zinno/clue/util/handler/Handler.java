package mines.zinno.clue.util.handler;

import com.sun.istack.internal.NotNull;
import mines.zinno.clue.util.ThrowableFunction;
import mines.zinno.clue.util.handler.data.SenderData;
import mines.zinno.clue.util.handler.exception.HandlerNotInstalled;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * The {@link Handler} utility allows for reflective calls to handles identified by their annotations. It provides
 * supreme organization and allows for simple storage of persistent data received by handles. WARNING: This utility
 * is not thread-safe.
 */
public class Handler {

    public static UUID ALL = UUID.randomUUID();

    protected Object[] context;

    private final Set<Handler> installedHandlers = new HashSet<>();
    private final Set<Object> handles = new HashSet<>();

    private final Map<Class<? extends Annotation>, BiPredicate<SenderData, Annotation>> identifyingAnnotations = new HashMap<>();

    private final boolean inheritData;

    // Default call context
    private Consumer<List<Object>> callContext = (curArgs) -> {
        if (context != null)
            curArgs.add(context);
    };

    public Handler() {
        this.inheritData = true;
    }

    public Handler(Object... handles) {
        this.inheritData = false;
        this.getHandles().addAll(Arrays.asList(handles));
    }

    public Handler(boolean inheritData, Object... handles) {
        this.inheritData = inheritData;
        this.getHandles().addAll(Arrays.asList(handles));
    }

    public Handler withContext(Object... context) {
        this.context = context;
        return this;
    }

    /**
     * Get an installed handler
     *
     * @param handlerClass Handler class
     * @param <X> Handler class type (inferred)
     * @return Handler class
     */
    @NotNull
    public final <X extends Handler> X get(final Class<X> handlerClass) {
        return this.get(handlerClass, null);
    }

    /**
     * Get an installed handler and send only the provided annotation class
     *
     * @param annotationClass Annotation class
     * @param handlerClass Handler class
     * @param <X> Handler class type (inferred)
     * @return Handler class
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final <X extends Handler> X get(final Class<X> handlerClass,
                                           final Class<? extends Annotation> annotationClass) {
        // Returns the handler with proper values set if it is of the provided handler class type
        final ThrowableFunction<NoSuchMethodException, Handler, X> getXInstHandler = (handler) -> {
            if(handler == null)
                return null;

            if(!(handlerClass == handler.getClass())) {
                return null;
            }

            if(handler.inheritData()) {
                handler.getHandles().addAll(Handler.this.getHandles());
                handler.setCallContext(Handler.this::addContextToCall);
            }


            if(Handler.this.context != null)
                handler.withContext(context);

            Handler.this.identifyingAnnotations.putAll(handler.identifyingAnnotations);
            handler.identifyingAnnotations.clear();
            for(Class<? extends Annotation> annotationType : Handler.this.identifyingAnnotations.keySet()) {
                if(annotationClass != null && annotationClass != annotationType)
                    continue;

                handler.identifyingAnnotations.put(annotationType, Handler.this.identifyingAnnotations.get(annotationType));
            }
            return (X) handler;
        };

        // Return the handler if it exists, or throw HandlerNotInstalled exception
        try {
            X returnVal = getXInstHandler.apply(this);
            if(returnVal != null)
                return returnVal;
            for(Handler installedHandler : installedHandlers) {
                returnVal = getXInstHandler.apply(installedHandler);
                if(returnVal != null)
                    return returnVal;
            }
            throw new HandlerNotInstalled(handlerClass);
        } catch (NoSuchMethodException e) {
            // Never reached (no recovery if reached)
            throw new RuntimeException(e);
        }
    }

    /**
     * Get handles (methods) that match the provided sender data
     */
    protected final Set<Method> getMethodHandles(SenderData senderData) {
        Set<Method> qualifyingMethods = new HashSet<>();
        for(Object handle : handles) {
            for(Method method : handle.getClass().getMethods()) {
                Annotation[] methodAnnotations = method.getAnnotations();
                if(methodAnnotations == null)
                    continue;
                boolean isQualifying = false;
                for(Annotation annotation : methodAnnotations) {
                    if(isQualifying)
                        break;
                    for(Class<? extends Annotation> identifyingAnnotation : identifyingAnnotations.keySet()) {
                        if(annotation == null || annotation.annotationType() != identifyingAnnotation)
                            continue;
                        if(!identifyingAnnotations.get(identifyingAnnotation).test(senderData, annotation))
                            continue;

                        qualifyingMethods.add(method);
                        isQualifying = true;
                        break;
                    }
                }
            }
        }
        return qualifyingMethods;
    }

    /**
     * Call the provided method with the given args
     */
    protected void call(Method method, Object... args) throws IllegalAccessException, InvocationTargetException {
        boolean isCalled = false;
        for(Object[] argCombo : getCombos(args)) {
            final List<Object> makeVarArgsDankAgain = new ArrayList<>();
            addContextToCall(makeVarArgsDankAgain);
            if(argCombo != null)
                makeVarArgsDankAgain.addAll(Arrays.asList(argCombo));

            for(Object handle : this.getHandles())
                try {
                    method.invoke(handle, makeVarArgsDankAgain.toArray());
                    isCalled = true;
                    break;
                } catch (IllegalArgumentException ignored) {}
        }
        if(!isCalled)
            throw new IllegalArgumentException(String.format("Failed to call %s method with provided context+args", method.getName()));

    }

    /**
     * Get possible arg combinations
     */
    protected final Set<Object[]> getCombos(Object[] args) {
        Set<Object[]> argCombos = new LinkedHashSet<>();
        if(args == null || args.length == 0)
            return argCombos;

        argCombos.add(new Object[0]);
        argCombos.add(args);
        for(int i = 0; i < args.length; i++) {

            Object lastArg = args[args.length - 1];
            for (int j = args.length - 1; j > 0; j--)
                args[j] = args[j-1];
            args[0] = lastArg;

            for(int j = args.length - 1; j >= 1; j--) {
                argCombos.add(Arrays.copyOfRange(args, 0, j));
            }
        }

        return argCombos;
    }

    /**
     * Add context to the args of the upcoming call
     */
    protected void addContextToCall(List<Object> curArgs) {
        callContext.accept(curArgs);
    }

    /**
     * Set the context of the upcoming call
     */
    protected void setCallContext(Consumer<List<Object>> callContext) {
        this.callContext = callContext;
    }

    /**
     * Install a handler
     */
    public Handler install(Handler handler) {
        this.installedHandlers.add(handler);
        return this;
    }

    /**
     * Uninstall a handler
     */
    public Handler uninstall(Handler handler) {
        this.installedHandlers.remove(handler);
        return this;
    }

    /**
     * Install an identifying annotation
     *
     * @param annotationClass Identifying annotation class
     * @param identifier {@link BiPredicate} providing the {@link SenderData} and identifying annotation
     *                   expecting a {@link Boolean} denoting whether or not the annotation is the target
     * @param <X> Identifying annotation type (inferred)
     */
    @SuppressWarnings("unchecked")
    public <X extends Annotation> void addIdentifyingAnnotation(Class<X> annotationClass,
            BiPredicate<SenderData, X> identifier) {
        this.identifyingAnnotations.put(
                annotationClass, (senderData, annotation) -> identifier.test(senderData, (X) annotation)
        );
    }

    /**
     * Delete an identifying annotation
     */
    public void delIdentifyingAnnotation(Class<? extends Annotation> annotationClass) {
        this.identifyingAnnotations.remove(annotationClass);
    }

    /**
     * Get handles
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> getHandles() {
        return this.handles;
    }

    /**
     * Get whether or not this handler should inherit data from it's parent handler
     */
    public boolean inheritData() {
        return inheritData;
    }

}
