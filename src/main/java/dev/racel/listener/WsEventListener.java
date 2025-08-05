package dev.racel.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.racel.entity.WsMessage;
import dev.racel.listener.handler.OrbitEventHandlerRegistry;
import dev.racel.listener.handler.impl.verify.ClientInfoEventHandler;
import dev.racel.listener.handler.impl.verify.IsVerifiedEventHandler;
import dev.racel.listener.handler.impl.verify.VerifyEventHandler;
import dev.racel.session.SessionManager;
import org.tinylog.Logger;

import java.util.List;

public class WsEventListener implements DataListener<String> {
    private final SessionManager sessionManager;
    private final OrbitEventHandlerRegistry registry;
    private final ObjectMapper objectMapper;

    private final List<String> verifyWhitelist = List.of(
            "clnti",
            "isVerified",
            "verify"
    );

    public WsEventListener(OrbitEventHandlerRegistry registry, ObjectMapper objectMapper, SessionManager sessionManager) {
        this.registry = registry;
        this.objectMapper = objectMapper;
        this.sessionManager = sessionManager;
    }

    @Override
    public void onData(SocketIOClient client, String data, AckRequest ackSender) {
        WsMessage wsMsg = parseWsMessage(data);
        if (wsMsg == null) return;

        String eventName = wsMsg.getName();
        OrbitEventHandlerRegistry.HandlerEntry<?> entry = registry.getHandlerEntry(eventName);
        if (entry == null) {
            Logger.warn("Event {} is not registered: {}", eventName, data);
            return;
        }

        Object eventData;
        if(entry.getDataType() == String.class){
            eventData = wsMsg.getValues().toString();
        }else {
            eventData = parseEventData(wsMsg.getValues(), entry.getDataType(), eventName);
        }
        if (eventData == null) return;

        executeHandler(entry, eventName, eventData, client);
    }

    private WsMessage parseWsMessage(String data) {
        try {
            return objectMapper.readValue(data, WsMessage.class);
        } catch (JsonProcessingException e) {
            Logger.error( "Failed to parse event message: {}", data);
            return null;
        }
    }

    private Object parseEventData(Object values, Class<?> dataType, String eventName) {
        try {
            return objectMapper.convertValue(values, dataType);
        }catch (IllegalArgumentException e) {
            Logger.error( e, "Failed to parse event {} data: {}", eventName, values);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private void executeHandler(OrbitEventHandlerRegistry.HandlerEntry<?> entry,
                                String eventName, Object eventData,
                                SocketIOClient client) {
        try {
            var typedEntry = (OrbitEventHandlerRegistry.HandlerEntry<Object>) entry;
            var session = sessionManager.getSessionByUuid(client.getSessionId().toString()).orElseThrow();

            if(!verifyWhitelist.contains(eventName) && !session.isVerified()){
                Logger.error("Unauthorized user attempted to trigger verify needed event {}", eventName);
                return;
            }

            typedEntry.getHandler().handle(session, eventData);
            Logger.debug("Event {} parsed. Data: {}", eventName, eventData);
        } catch (Exception e) {
            Logger.error(e, "Event {} failed to handle. Data: {}", eventName, eventData);
        }
    }
}
