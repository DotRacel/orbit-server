package dev.racel.listener.handler.impl;

import dev.racel.listener.handler.EventTrigger;
import dev.racel.listener.handler.OrbitEventHandler;

public class GetAllCosmeticsEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "getAllCosmetics";
    }

    @Override
    public void handle(EventTrigger trigger, Object data) {

    }
}
