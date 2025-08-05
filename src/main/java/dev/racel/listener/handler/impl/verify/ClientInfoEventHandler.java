package dev.racel.listener.handler.impl.verify;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.dao.UserDAO;
import dev.racel.entity.Group;
import dev.racel.entity.event.ClientInfo;
import dev.racel.entity.event.IsVerifiedMessage;
import dev.racel.entity.event.SelectedGroupMembersMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.listener.handler.impl.group.GroupGetAllEventHandler;
import dev.racel.session.Session;
import dev.racel.util.UserUtil;
import org.tinylog.Logger;

public class ClientInfoEventHandler implements OrbitEventHandler<ClientInfo> {
    private final UserDAO userDAO = DbConfig.getInstance().getUserDAO();

    @Override
    public String getName() {
        return "clnti";
    }

    @Override
    public void handle(Session session, ClientInfo data) {
        session.setClientInfo(data);

        if (data.getPurchaseID() == null) return;
        var userOpt = userDAO.getUserByPurchaseId(data.getPurchaseID());
        if(userOpt.isEmpty()) {
            Logger.info("User {} provided an invalid purchase id {}",
                    session.getClient().getSessionId(),
                    data.getPurchaseID());
            return;
        }
        var user = userOpt.get();
        var clientInfoOpt = session.getClientInfo();
        session.setOrbitUser(user);
        session.sendMessage("isVerified",
                new IsVerifiedMessage(true));
        clientInfoOpt.ifPresent(clientInfo -> UserUtil.updateUserByClientInfo(user, clientInfo));
        userDAO.updateUser(user);

        new GroupGetAllEventHandler().handle(session, data);

        Logger.info("User {} is verified successfully. ", user.getName());
    }
}
