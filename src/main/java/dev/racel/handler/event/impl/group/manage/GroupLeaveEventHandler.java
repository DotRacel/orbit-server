package dev.racel.handler.event.impl.group.manage;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupRole;
import dev.racel.entity.message.GroupLeaveMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GroupLeaveEventHandler implements OrbitEventHandler<GroupLeaveMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();
    
    @Override
    public String getName() {
        return "groupLeave";
    }

    @Override
    public void handle(Session session, GroupLeaveMessage data) {
        var userName = session.getOrbitUser().getName();
        var groupOpt = groupDAO.getGroupByName(data.getGroupName());

        if (groupOpt.isEmpty()) {
            session.sendChat("That group doesn't exist!");
            return;
        }

        var group = groupOpt.get();

        if (!groupDAO.isUserInGroup(group.getId(), userName)) {
            session.sendChat("You are not in that group!");
            return;
        }

        var role = groupDAO.getGroupRoleNameByMemberName(group.getId(), userName);
        if (GroupRole.valueOf(role) == GroupRole.OWNER) {
            session.sendChat("As a owner, you can only disband your group!");
            return;
        }

        groupDAO.removeGroupMember(group.getId(), userName);
        session.sendChat("You left the group.");
        Logger.info("User {} left group {}", userName, group.getGroupName());
    }
}
