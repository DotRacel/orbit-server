package dev.racel.session;

import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import dev.racel.config.WsConfig;
import dev.racel.entity.OrbitUser;
import dev.racel.entity.message.ChatMessage;
import dev.racel.entity.message.ClientInfoMessage;
import dev.racel.entity.WsMessage;
import lombok.Data;
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

    public Optional<ClientInfoMessage> getClientInfoMessage() {
        return Optional.ofNullable(clientInfoMessage);
    }

    public Optional<OrbitUser> getOrbitUser() {
        return Optional.ofNullable(orbitUser);
    }

    public void sendMessage(String eventName, Object data) {
        var msg = new WsMessage(eventName, data);
        client.sendEvent("event", getJsonFromObject(msg));
    }

    public void sendChat(String text) {
        sendMessage("doChat", new ChatMessage("&7(&9OrbitClient&7) " + text));
    }

    public String getJsonFromObject(Object obj) {
        try {
            return WsConfig.mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Logger.error(e, "Failed to deserialize object");
        }
        return null;
    }

    public void sendGroupMessage(String eventName, Object data) {
        var rooms = client.getAllRooms();
        if(rooms.isEmpty()) return;

        rooms.forEach(room -> {
            WsConfig.getInstance().getServer().getRoomOperations(room).sendEvent(
                    "event",
                    getJsonFromObject(new WsMessage(eventName, data)));
        });
    }

    public void sendGroupChat(String text) {
        sendGroupMessage("doChat", new  ChatMessage("&7(&9OrbitClient&7) " + text));
    }
}
