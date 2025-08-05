package dev.racel.handler.event.impl.cosmetics;

import dev.racel.entity.PlayerUUID;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class GetPlayerCosmeticsEventHandler implements OrbitEventHandler<PlayerUUID> {
    @Override
    public String getName() {
        return "getPlayerCosmetics";
    }

    @Override
    public void handle(Session session, PlayerUUID data) {

    }
}
