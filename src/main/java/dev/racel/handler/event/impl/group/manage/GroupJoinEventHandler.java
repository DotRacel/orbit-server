package dev.racel.handler.event.impl.group.manage;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupRole;
import dev.racel.entity.message.GroupMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class GroupJoinEventHandler implements OrbitEventHandler<GroupMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "groupJoin";
    }

    @Override
    public void handle(Session session, GroupMessage data) {
        var user = session.getOrbitUser().get();
        var groupOpt = groupDAO.getGroupByName(data.getGroupName());

        if(groupOpt.isEmpty()) {
            Logger.info("User {} attempts to join a not existing group {}",
                    user.getName(), data.getGroupName());
            return;
        }

        var group = groupOpt.get();

        if(data.getPassword() == null || !data.getPassword().equals(group.getPassword())) {
            Logger.info("User {} provided wrong password to join a group.");
            session.sendChat("Password mismatch");
            return;
        }

        if(groupDAO.isUserInGroup(group.getId(), user.getName())) {
            session.sendChat("You are already in the group!");
            return;
        }

        groupDAO.addGroupMember(group.getId(), user.getName(), GroupRole.MEMBER.toString());

        Logger.info("User {} joined group {}", session.getOrbitUser().get().getName(), data.getGroupName());
    }
}
