package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.Group;
import dev.racel.entity.event.SelectedGroupMembersMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GetSelectedGroupMembersEventHandler implements OrbitEventHandler<Object> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "getSelectedGroupMembers";
    }

    @Override
    public void handle(Session session, Object data) {
        var groupNameOpt = DbConfig.getInstance().getUserDAO().getSelectedGroupByName(session.getOrbitUser().get().getName());

        if(groupNameOpt.isEmpty()) {
            Logger.info("User {} hasn't selected a group yet but requested for the group {}'s member list");
            session.sendChat("You are not in any group yet");
            return;
        }
        
        var groupName = groupNameOpt.get();
        Group group = groupDAO.getGroupByName(groupName).get();
        var members = groupDAO.getGroupMembersUuid(group.getId());
        session.sendMessage(getName(), new SelectedGroupMembersMessage(groupName, members));
    }
}
