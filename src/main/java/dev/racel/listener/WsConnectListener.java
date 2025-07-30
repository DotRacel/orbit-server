package dev.racel.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import org.tinylog.Logger;

public class WsConnectListener implements ConnectListener {
    @Override
    public void onConnect(SocketIOClient client) {
        Logger.info("{} connected", client.getSessionId());
    }
}
