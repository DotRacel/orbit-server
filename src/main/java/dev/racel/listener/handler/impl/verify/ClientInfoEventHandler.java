package dev.racel.listener.handler.impl.verify;

import dev.racel.config.DbConfig;
import dev.racel.dao.UserDAO;
import dev.racel.entity.event.ClientInfo;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class ClientInfoEventHandler implements OrbitEventHandler<ClientInfo> {
    private final UserDAO userDAO = DbConfig.getInstance().getUserDAO();

    @Override
    public String getName() {
        return "clnti";
    }

    @Override
    public void handle(Session session, ClientInfo data) {
        session.setClientInfo(data);
    }
}
