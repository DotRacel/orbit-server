package dev.racel.listener.handler.impl;

import dev.racel.entity.event.DummyEntity;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class GetFeaturedServersEventHandler implements OrbitEventHandler<DummyEntity> {

    @Override
    public String getName() {
        return "getFeaturedServers";
    }

    @Override
    public void handle(Session session, DummyEntity data) {

    }
}
