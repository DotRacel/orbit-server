package dev.racel.listener.handler.impl;

import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GetFeaturedServersEventHandler implements OrbitEventHandler<Object> {

    @Override
    public String getName() {
        return "getFeaturedServers";
    }

    @Override
    public void handle(Session session, Object data) {
        Logger.info("Getting Featured Servers Event");
    }
}
