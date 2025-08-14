package dev.racel.session;

import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import dev.racel.config.WsConfig;
import dev.racel.entity.OrbitUser;
import dev.racel.entity.message.ChatMessage;
import dev.racel.entity.message.ClientInfoMessage;
import dev.racel.entity.message.WsMessage;
import dev.racel.util.JsonUtil;
import lombok.Data;
import lombok.Getter;
import org.tinylog.Logger;

import java.util.Optional;

@Data
public class Session {
    public Session(SocketIOClient client) {
        this.client = client;
    }
    SocketIOClient client;
    OrbitUser orbitUser;
    ClientInfoMessage clientInfoMessage;
    boolean verified;

    public void sendMessage(String eventName, Object data) {
        var msg = new WsMessage(eventName, data);
        assert data != null;
        client.sendEvent("event", JsonUtil.getJsonFromObject(msg));
    }

    public void sendChat(String text) {
        sendMessage("doChat", new ChatMessage("&7(&9OrbitClient&7) " + text));
    }

    public void sendGroupMessage(String eventName, Object data) {
        assert data != null;
        var rooms = client.getAllRooms();
        if(rooms.isEmpty()) return;

        rooms.forEach(room -> {
            WsConfig.getInstance().getServer().getRoomOperations(room).sendEvent(
                    "event",
                    JsonUtil.getJsonFromObject(new WsMessage(eventName, data)));
        });
    }

    public void sendGroupChat(String text) {
        sendGroupMessage("doChat", new  ChatMessage("&7(&9OrbitClient&7) " + text));
    }
}
