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

@SuppressWarnings("unused")
public class Handler {

    public static UUID ALL = UUID.randomUUID();

    protected Object[] context;

    private final Set<Handler> installedHandlers = new HashSet<>();
    private final Set<Object> handles = new HashSet<>();

    private final Map<Class<? extends Annotation>, BiPredicate<SenderData, Annotation>>
            identifyingAnnotations = new HashMap<>();

    private Class<? extends Annotation> requiredAnnotationClass;
    private final boolean inheritHandles;

    public Handler() {
        this.inheritHandles = true;
    }

    public Handler(Object... handles) {
        this.inheritHandles = false;
        this.getHandles().addAll(Arrays.asList(handles));
    }

    public Handler(boolean inheritHandles, Object... handles) {
        this.inheritHandles = inheritHandles;
        this.getHandles().addAll(Arrays.asList(handles));
    }

    public Handler withContext(Object... context) {
        this.context = context;
        return this;
    }

    @NotNull
    public final <X extends Handler> X get(final Class<X> handlerClass) {
        return this.get(handlerClass, null);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public final <X extends Handler> X get(final Class<X> handlerClass,
                                           final Class<? extends Annotation> annotationClass) {
        final ThrowableFunction<NoSuchMethodException, Handler, X> getXInstHandler = (handler) -> {
            if(handler == null)
                return null;

            if(!(handlerClass == handler.getClass())) {
                return null;
            }

            if(handler.inheritHandles())
                handler.getHandles().addAll(Handler.this.getHandles());

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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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
                        if(annotation == null || annotation.getClass() != identifyingAnnotation)
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

    protected void call(Method method, Object... args) throws IllegalAccessException, InvocationTargetException {
        boolean isCalled = false;
        for(Object[] argCombo : getCombos(args)) {
            try {
                final List<Object> makeVarArgsDankAgain = new ArrayList<>();
                addContextToCall(makeVarArgsDankAgain);
                if(argCombo != null)
                    makeVarArgsDankAgain.add(Arrays.asList(argCombo));

                method.invoke(this, makeVarArgsDankAgain.toArray());
                isCalled = true;
                break;
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof IllegalArgumentException) {
                    continue;
                }
                throw e;
            }
        }
        if(!isCalled)
            throw new IllegalArgumentException();
    }

    private Set<Object[]> getCombos(Object[] args) {
        Set<Object[]> argCombos = new HashSet<>();
        if(args == null || args.length == 0)
            return argCombos;

        argCombos.add(args);
        for(int i = 0; i < args.length; i++) {

            Object lastArg = args[args.length - 1];
            for (int j = 1; j < args.length; j++)
                args[j] = args[j-1];
            args[0] = lastArg;

            for(int j = args.length - 1; j > 0; j--) {
                argCombos.add(Arrays.copyOfRange(args, 0, j));
            }
        }

        return argCombos;
    }

    protected void addContextToCall(List<Object> curArgs) {
        curArgs.add((context == null) ? new Object[0] : context);
    }

    public Handler install(Handler handler) {
        this.installedHandlers.add(handler);
        return this;
    }

    public Handler uninstall(Handler handler) {
        this.installedHandlers.remove(handler);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <X extends Annotation> void addIdentifyingAnnotation(Class<X> annotationClass,
            BiPredicate<SenderData, X> identifier) {
        this.identifyingAnnotations.put(
                annotationClass, (senderData, annotation) -> identifier.test(senderData, (X) annotation)
        );
    }

    public void delIdentifyingAnnotation(Class<? extends Annotation> annotationClass) {
        this.identifyingAnnotations.remove(annotationClass);
    }

    public Set<Object> getHandles() {
        return this.handles;
    }

    public boolean inheritHandles() {
        return inheritHandles;
    }

}
