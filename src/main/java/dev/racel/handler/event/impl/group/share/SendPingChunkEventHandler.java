package dev.racel.handler.event.impl.group.share;

import dev.racel.entity.message.PingChunkMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class SendPingChunkEventHandler implements OrbitEventHandler<PingChunkMessage> {
    @Override
    public String getName() {
        return "sendPingChunk";
    }

    @Override
    public void handle(Session session, PingChunkMessage data) {
        session.sendGroupMessage("pingChunk", data);
    }
}
