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
        this(true);
    }

    public Handler(Object... handles) {
        this(false, handles);
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
    public final <X extends Handler> X get() {
        return this.get(null);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public final <X extends Handler> X get(final Class<? extends Annotation> annotationClass) {
        final ThrowableFunction<NoSuchMethodException, Handler, X> getXInstHandler = (handler) -> {
            if(handler == null)
                return null;

            final boolean curHandlerIsXInst =
                    Handler.this.getClass().getMethod("get").getReturnType() == handler.getClass();

            if(!curHandlerIsXInst) {
                return null;
            }

            if(handler.inheritHandles())
                handler.getHandles().addAll(Handler.this.getHandles());

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
            throw new HandlerNotInstalled(this.getClass().getMethod("get").getReturnType());
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

    protected void call(Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        final List<Object> makeVarArgsDankAgain = new ArrayList<>();
        if(args != null)
            makeVarArgsDankAgain.add(Arrays.asList(args));

        method.invoke(this, makeVarArgsDankAgain.toArray());
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
