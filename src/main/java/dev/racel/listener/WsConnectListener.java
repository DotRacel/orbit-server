package dev.racel.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import dev.racel.session.SessionManager;
import lombok.AllArgsConstructor;
import org.tinylog.Logger;

public class WsConnectListener implements ConnectListener {
    private final SessionManager sessionManager;

    public WsConnectListener(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void onConnect(SocketIOClient client) {
        Logger.info("{} connected", client.getSessionId());
        sessionManager.createSession(client);
    }
}
