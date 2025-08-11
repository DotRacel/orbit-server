package dev.racel.handler.event.impl.group.ping;

import dev.racel.entity.message.PingLocationMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class SendPingLocationEventHandler implements OrbitEventHandler<PingLocationMessage> {
    @Override
    public String getName() {
        return "sendPingLocation";
    }

    @Override
    public void handle(Session session, PingLocationMessage data) {
        session.sendGroupMessage("pingLoc", data);
    }
}
