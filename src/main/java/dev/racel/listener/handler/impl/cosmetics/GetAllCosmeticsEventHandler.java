package dev.racel.listener.handler.impl.cosmetics;

import dev.racel.listener.handler.OrbitEventHandler;
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
