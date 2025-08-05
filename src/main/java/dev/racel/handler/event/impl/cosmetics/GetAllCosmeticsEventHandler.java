package dev.racel.handler.event.impl.cosmetics;

import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class GetAllCosmeticsEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "getAllCosmetics";
    }

    @Override
    public void handle(Session session, Object data) {

    }
}
