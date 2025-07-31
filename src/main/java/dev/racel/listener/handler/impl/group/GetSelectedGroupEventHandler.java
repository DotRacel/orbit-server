package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.event.SelectedGroupMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class GetSelectedGroupEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "getSelectedGroup";
    }

    @Override
    public void handle(Session session, Object data) {
        String groupName = DbConfig.getInstance().getUserDAO().getSelectedGroupByName(session.getOrbitUser().get().getName());
        session.sendMessage(getName(), new SelectedGroupMessage(groupName));
    }
}
