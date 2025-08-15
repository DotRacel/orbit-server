package dev.racel.handler.event.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.message.SelectedGroupMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GetSelectedGroupEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "getSelectedGroup";
    }

    @Override
    public void handle(Session session, Object data) {
        var userName = session.getOrbitUser().getName();
        var groupOpt = DbConfig.getInstance().getUserDAO().getSelectedGroupByName(userName);

        if (groupOpt.isEmpty()) {
            return;
        }

        var group = groupOpt.get();

        session.sendMessage(getName(), new SelectedGroupMessage(group));
        Logger.info("User {} fetched his selected group {}",
                userName, group);
    }
}
