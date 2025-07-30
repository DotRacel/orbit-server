package dev.racel.listener.handler.impl;

import dev.racel.listener.handler.EventTrigger;
import dev.racel.listener.handler.OrbitEventHandler;

public class IsVerifiedEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "isVerified";
    }

    @Override
    public void handle(EventTrigger trigger, Object data) {

    }
}
