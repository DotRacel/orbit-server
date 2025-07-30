package dev.racel.listener.handler;

public interface OrbitEventHandler<T> {
    String getName();
    void handle(EventTrigger trigger, T data);
}