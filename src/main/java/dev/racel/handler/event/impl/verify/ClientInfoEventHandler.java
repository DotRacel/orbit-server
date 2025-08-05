package dev.racel.handler.event.impl.verify;

import dev.racel.config.DbConfig;
import dev.racel.dao.UserDAO;
import dev.racel.entity.message.ClientInfoMessage;
import dev.racel.entity.message.IsVerifiedMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.handler.event.impl.group.GroupGetAllEventHandler;
import dev.racel.session.Session;
import dev.racel.util.UserUtil;
import org.tinylog.Logger;

public class ClientInfoEventHandler implements OrbitEventHandler<ClientInfoMessage> {
    private final UserDAO userDAO = DbConfig.getInstance().getUserDAO();

    @Override
    public String getName() {
        return "clnti";
    }

    @Override
    public void handle(Session session, ClientInfoMessage data) {
        session.setClientInfoMessage(data);

        if (data.getPurchaseID() == null) return;
        var userOpt = userDAO.getUserByPurchaseId(data.getPurchaseID());
        if(userOpt.isEmpty()) {
            Logger.info("User {} provided an invalid purchase id {}",
                    session.getClient().getSessionId(),
                    data.getPurchaseID());
            return;
        }
        var user = userOpt.get();
        var clientInfoOpt = session.getClientInfoMessage();
        session.setOrbitUser(user);
        session.sendMessage("isVerified",
                new IsVerifiedMessage(true));
        clientInfoOpt.ifPresent(clientInfoMessage -> UserUtil.updateUserByClientInfo(user, clientInfoMessage));
        userDAO.updateUser(user);
        session.setVerified(true);

        new GroupGetAllEventHandler().handle(session, data);

        Logger.info("User {} is verified successfully. ", user.getName());
    }
}
