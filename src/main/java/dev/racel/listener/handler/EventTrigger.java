package dev.racel.listener.handler;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventTrigger {
    SocketIOClient client;
}
