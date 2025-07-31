package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.event.AllGroupsMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class GroupGetAllEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "groupGetAll";
    }

    @Override
    public void handle(Session session, Object data) {
        var groups = DbConfig.getInstance().getGroupDAO().getUserGroupNames(session.getOrbitUser().get().getName());
        session.sendMessage(getName(), new AllGroupsMessage(groups));
    }
}
