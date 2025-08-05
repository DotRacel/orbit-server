package dev.racel.handler.event.impl;

import dev.racel.handler.event.OrbitEventHandler;
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
