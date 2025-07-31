package dev.racel.session;

import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.racel.config.WsConfig;
import dev.racel.entity.OrbitUser;
import dev.racel.entity.event.ChatMessage;
import dev.racel.entity.event.ClientInfo;
import dev.racel.entity.event.WsMessage;
import dev.racel.entity.event.WsRawMessage;
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
    ClientInfo clientInfo;

    public Optional<ClientInfo> getClientInfo() {
        return Optional.ofNullable(clientInfo);
    }

    public Optional<OrbitUser> getOrbitUser() {
        return Optional.ofNullable(orbitUser);
    }

    public void sendMessage(String eventName, Object data) {
        var msg = new WsMessage(eventName,
                WsConfig.mapper.convertValue(data, ObjectNode.class));
        try {
            client.sendEvent("event", WsConfig.mapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
            Logger.error( "Failed to send event: {}", e.getMessage());
        }
    }

    public void sendMessage(String eventName, String data) {
        var msg = new WsRawMessage(eventName, data);
        try {
            client.sendEvent("event", WsConfig.mapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
            Logger.error( "Failed to send event: {}", e.getMessage());
        }
    }

    public void sendChat(String text) {
        sendMessage("doChat", new ChatMessage(text));
    }
}
