package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.event.GroupMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class SetSelectedGroupEventHandler implements OrbitEventHandler<GroupMessage> {

    @Override
    public String getName() {
        return "setSelectedGroup";
    }

    @Override
    public void handle(Session session, GroupMessage data) {
        var user = session.getOrbitUser().get();
        var group = DbConfig.getInstance().getGroupDAO().getGroupByName(data.getGroupName()).get();

        if (!data.getPassword().equals(group.getPassword())) {
            session.sendChat("Group password is wrong.");
            return;
        }

        user.setSelected_group(data.getGroupName());
        Logger.info("User {} selected a new group {}", user.getName(), user.getSelected_group());
    }
}
