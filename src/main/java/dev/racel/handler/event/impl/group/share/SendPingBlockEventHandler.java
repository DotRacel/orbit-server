package dev.racel.handler.event.impl.group.share;

import dev.racel.entity.message.PingBlockMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class SendPingBlockEventHandler implements OrbitEventHandler<PingBlockMessage> {
    @Override
    public String getName() {
        return "sendPingBlock";
    }

    @Override
    public void handle(Session session, PingBlockMessage data) {
        session.sendGroupMessage("pingBlock", data);
    }
}
