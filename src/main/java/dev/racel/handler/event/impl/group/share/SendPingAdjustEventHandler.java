package dev.racel.handler.event.impl.group.share;

import dev.racel.entity.message.PingAdjustMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class SendPingAdjustEventHandler implements OrbitEventHandler<PingAdjustMessage> {
    @Override
    public String getName() {
        return "sendPingAdjust";
    }

    @Override
    public void handle(Session session, PingAdjustMessage data) {
        session.sendGroupMessage("pingAdjust", data);
    }
}
