package dev.racel.handler.event.impl.verify;

import dev.racel.entity.message.IsVerifiedMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class IsVerifiedEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "isVerified";
    }

    @Override
    public void handle(Session session, Object data) {
        session.sendMessage("isVerified",
                new IsVerifiedMessage(session.isVerified()));

        if(!session.isVerified()){
            session.sendMessage("rclnti", "");
        }
    }
}
