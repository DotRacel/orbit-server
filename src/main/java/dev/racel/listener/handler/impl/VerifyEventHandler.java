package dev.racel.listener.handler.impl;

import dev.racel.entity.VerifyMessage;
import dev.racel.listener.handler.EventTrigger;
import dev.racel.listener.handler.OrbitEventHandler;

public class VerifyEventHandler implements OrbitEventHandler<VerifyMessage> {
    @Override
    public String getName() {
        return "verify";
    }

    @Override
    public void handle(EventTrigger trigger, VerifyMessage data) {

    }
}
