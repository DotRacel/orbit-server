package dev.racel.listener.handler;

import dev.racel.session.Session;

public interface OrbitEventHandler<T> {
    String getName();
    void handle(Session session, T data);
}