package dev.racel.session;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Getter;

import java.util.HashMap;
import java.util.Optional;

public class SessionManager {
    @Getter
    HashMap<String, Session> sessions;

    public SessionManager() {
        sessions = new HashMap<>();
    }

    public Optional<Session> getSessionByUuid(String uuid) {
        return Optional.ofNullable(sessions.get(uuid));
    }

    public void createSession(SocketIOClient client) {
        var session = new Session(client);
        this.sessions.put(client.getSessionId().toString(), session);
    }

    public void removeSessionByUuid(String uuid) {
        sessions.remove(uuid);
    }
}
