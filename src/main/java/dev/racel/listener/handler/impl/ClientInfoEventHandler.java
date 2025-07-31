package dev.racel.listener.handler.impl;

import dev.racel.entity.event.ClientInfo;
import dev.racel.listener.handler.EventTrigger;
import dev.racel.listener.handler.OrbitEventHandler;

public class ClientInfoEventHandler implements OrbitEventHandler<ClientInfo> {
    @Override
    public String getName() {
        return "clnti";
    }

    @Override
    public void handle(EventTrigger trigger, ClientInfo data) {

    }
}
