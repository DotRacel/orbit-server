package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.Group;
import dev.racel.entity.event.GroupMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GroupCreateEventHandler implements OrbitEventHandler<GroupMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "groupCreate";
    }

    @Override
    public void handle(Session session, GroupMessage data) {
        if (groupDAO.getGroupByName(data.getGroupName()).isPresent()) {
            session.sendChat("Group you create is already existing.");
            return;
        }

        var user = session.getOrbitUser().get();

        Group group = new Group(0, data.getGroupName(), data.getPassword());
        groupDAO.createGroup(group);
        //TODO: Create default roles, add default member...

        Logger.info("User {} created a group {} with password {}",
                user.getName(),
                group.getGroupName(),
                group.getPassword());
    }
}
