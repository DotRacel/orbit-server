package dev.racel.handler.event.impl.group.manage;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupRole;
import dev.racel.entity.message.GroupLeaveMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GroupDisbandEventHandler implements OrbitEventHandler<GroupLeaveMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "groupDisband";
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
        if (GroupRole.valueOf(role) != GroupRole.OWNER) {
            session.sendChat("You cannot disband the group since you aren't the owner.");
            return;
        }

        groupDAO.removeGroup(group.getId());

        session.sendChat("The group has been disbanded!");
        Logger.info("User {} disbanded group {}", userName, group.getGroupName());
    }
}
