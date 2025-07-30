package dev.racel.listener.handler.impl;

import dev.racel.entity.PlayerUUID;
import dev.racel.listener.handler.EventTrigger;
import dev.racel.listener.handler.OrbitEventHandler;

public class GetPlayerCosmeticsEventHandler implements OrbitEventHandler<PlayerUUID> {
    @Override
    public String getName() {
        return "getPlayerCosmetics";
    }

    @Override
    public void handle(EventTrigger trigger, PlayerUUID data) {

    }
}
