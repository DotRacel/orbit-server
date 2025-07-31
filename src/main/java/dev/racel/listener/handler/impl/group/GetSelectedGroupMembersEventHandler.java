package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.Group;
import dev.racel.entity.event.SelectedGroupMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class GetSelectedGroupMembersEventHandler implements OrbitEventHandler<Object> {
    private GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "getSelectedGroupMembers";
    }

    @Override
    public void handle(Session session, Object data) {
        String groupName = DbConfig.getInstance().getUserDAO().getSelectedGroupByName(session.getOrbitUser().get().getName());
        if(groupName == null) return;
        Group group = groupDAO.getGroupByName(groupName).get();
        var members = groupDAO.getGroupMembers(group.getId());
    }
}
