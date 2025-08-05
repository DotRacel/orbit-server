package dev.racel.handler.event.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.message.SelectedGroupMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class GetSelectedGroupEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "getSelectedGroup";
    }

    @Override
    public void handle(Session session, Object data) {
        String groupName = DbConfig.getInstance().getUserDAO().getSelectedGroupByName(session.getOrbitUser().get().getName()).orElse(null);
        session.sendMessage(getName(), new SelectedGroupMessage(groupName));
    }
}
