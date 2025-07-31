package dev.racel.listener.handler.impl.verify;

import dev.racel.entity.event.IsVerifiedMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class IsVerifiedEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "isVerified";
    }

    @Override
    public void handle(Session session, Object data) {
        session.sendMessage("isVerified",
                new IsVerifiedMessage(session.getOrbitUser().isPresent()));
    }
}
