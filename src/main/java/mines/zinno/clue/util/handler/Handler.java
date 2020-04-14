package mines.zinno.clue.util.handler;

import mines.zinno.clue.util.ThrowableFunction;
import mines.zinno.clue.util.handler.data.SenderData;
import mines.zinno.clue.util.handler.exception.HandleNotFound;
import mines.zinno.clue.util.handler.exception.HandlerNotInstalled;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiPredicate;

@SuppressWarnings("unused")
public class Handler {

    protected Object[] context;

    private final Set<Handler> installedHandlers = new HashSet<>();
    private Set<Object> handles = new HashSet<>();

    private final Map<Class<? extends Annotation>, BiPredicate<SenderData, Annotation>>
            identifyingAnnotations = new HashMap<>();

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

    public void withContext(Object... context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public final <X extends Handler> X get() {
        final ThrowableFunction<NoSuchMethodException, Handler, X> getXInstHandler = (handler) -> {
            if(handler == null)
                return null;

            final boolean curHandlerIsXInst =
                    Handler.this.getClass().getMethod("get").getReturnType() == handler.getClass();

            if(!curHandlerIsXInst) {
                return null;
            }

            if(handler.inheritHandles())
                handler.setHandles(Handler.this.getHandles());

            handler.withContext(context);
            handler.identifyingAnnotations.putAll(Handler.this.identifyingAnnotations);
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
            // Never reached
            e.printStackTrace();
            return null;
        }
    }

    protected Method getHandle(SenderData senderData) {
        for(Object handle : handles) {
            for(Method method : handle.getClass().getMethods()) {
                Annotation[] methodAnnotations = method.getAnnotations();
                if(methodAnnotations == null)
                    continue;
                for(Annotation annotation : methodAnnotations) {
                    for(Class<? extends Annotation> identifyingAnnotation : identifyingAnnotations.keySet()) {
                        if(annotation == null || annotation.getClass() != identifyingAnnotation)
                            continue;
                        if(identifyingAnnotations.get(identifyingAnnotation).test(senderData, annotation))
                            return method;
                    }
                }
            }
        }
        throw new HandleNotFound(senderData);
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

    public void setHandles(Set<Object> handles) {
        this.handles = handles;
    }

    public boolean inheritHandles() {
        return inheritHandles;
    }

}
