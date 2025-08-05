package dev.racel.handler.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.tinylog.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrbitEventHandlerRegistry {
    private final Map<String, HandlerEntry<?>> handlerMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Class<?>> GENERIC_TYPE_CACHE = new ConcurrentHashMap<>();

    public <T> void register(OrbitEventHandler<T> handler) {
        String eventName = handler.getName();
        if (handlerMap.containsKey(eventName)) {
            Logger.error(String.format("Duplicate event name '%s'", eventName));
            return;
        }
        Class<T> dataType = resolveGenericType(handler);
        handlerMap.put(eventName, new HandlerEntry<>(handler, dataType));
    }

    public HandlerEntry<?> getHandlerEntry(String eventName) {
        return handlerMap.get(eventName);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> resolveGenericType(OrbitEventHandler<T> handler) {
        Class<?> handlerClass = handler.getClass();
        Class<?> cachedType = GENERIC_TYPE_CACHE.get(handlerClass);
        if (cachedType != null) {
            return (Class<T>) cachedType;
        }

        Class<?> currentClass = handlerClass;
        while (currentClass != null) {
            for (Type interfaceType : currentClass.getGenericInterfaces()) {
                if (interfaceType instanceof ParameterizedType parameterizedType) {
                    if (parameterizedType.getRawType() == OrbitEventHandler.class) {
                        Type actualType = parameterizedType.getActualTypeArguments()[0];
                        if (actualType instanceof Class<?>) {
                            Class<T> dataType = (Class<T>) actualType;
                            GENERIC_TYPE_CACHE.put(handlerClass, dataType);
                            return dataType;
                        } else {
                            Logger.error("Handler<T> which T is not a class type: {}", actualType.getTypeName());
                            throw new IllegalStateException();
                        }
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        Logger.error("Handle didn't implement interface {}", handlerClass.getName());
        throw new IllegalStateException();
    }

    @Data
    @AllArgsConstructor
    public static class HandlerEntry<T> {
        private OrbitEventHandler<T> handler;
        private Class<T> dataType;
    }
}
