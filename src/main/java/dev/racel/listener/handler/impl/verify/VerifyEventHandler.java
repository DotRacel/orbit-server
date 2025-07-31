package dev.racel.listener.handler.impl.verify;

import dev.racel.config.DbConfig;
import dev.racel.dao.UserDAO;
import dev.racel.entity.event.IsVerifiedMessage;
import dev.racel.entity.event.VerifyErrorMessage;
import dev.racel.entity.event.VerifyMessage;
import dev.racel.entity.event.VerifySuccessMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import dev.racel.util.UserUtil;
import org.tinylog.Logger;

public class VerifyEventHandler implements OrbitEventHandler<VerifyMessage> {
    private final UserDAO userDAO = DbConfig.getInstance().getUserDAO();
    @Override
    public String getName() {
        return "verify";
    }

    @Override
    public void handle(Session session, VerifyMessage data) {
        var userOpt = userDAO.getUserByPurchaseId(data.getOrbitProductID());
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            var clientInfoOpt = session.getClientInfo();
            session.setOrbitUser(user);
            session.sendMessage("isVerified",
                    new IsVerifiedMessage(true));
            session.sendMessage("verify", new VerifySuccessMessage("Success"));
            clientInfoOpt.ifPresent(clientInfo -> UserUtil.updateUserByClientInfo(user, clientInfo));
            userDAO.updateUser(user);
            Logger.info("User {} is verified successfully. ", user.getName());
        }else {
            Logger.error( "Purchase id not found: {}", data.getOrbitProductID());
            session.sendMessage("verify", new VerifyErrorMessage("Purchase id not found"));
        }
    }
}
