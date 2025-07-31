package dev.racel.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import dev.racel.session.SessionManager;
import org.tinylog.Logger;

public class WsDisconnectListener implements DisconnectListener {
    private SessionManager sessionManager;

    public WsDisconnectListener(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void onDisconnect(SocketIOClient client) {
        Logger.info("Client {} disconnected", client.getSessionId());
        sessionManager.removeSessionByUuid(client.getSessionId().toString());
    }
}
