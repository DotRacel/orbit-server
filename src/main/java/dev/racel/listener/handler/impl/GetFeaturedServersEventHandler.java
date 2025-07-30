package dev.racel.listener.handler.impl;

import dev.racel.entity.DummyEntity;
import dev.racel.listener.handler.EventTrigger;
import dev.racel.listener.handler.OrbitEventHandler;

public class GetFeaturedServersEventHandler implements OrbitEventHandler<DummyEntity> {

    @Override
    public String getName() {
        return "getFeaturedServers";
    }

    @Override
    public void handle(EventTrigger trigger, DummyEntity data) {

    }
}
