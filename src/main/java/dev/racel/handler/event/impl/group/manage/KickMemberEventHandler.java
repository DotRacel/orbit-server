package dev.racel.handler.event.impl.group.manage;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupPermission;
import dev.racel.entity.message.MemberManageMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class KickMemberEventHandler implements OrbitEventHandler<MemberManageMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "groupKickMember";
    }

    @Override
    public void handle(Session session, MemberManageMessage data) {
        var user = session.getOrbitUser().orElseThrow().getName();
        var group = groupDAO.getGroupByName(data.getGroupName()).orElseThrow();

        var role = groupDAO.getGroupRoleNameByMemberName(group.getId(), user);
        if (!groupDAO.hasGroupRolePermission(group.getId(),
                role,
                GroupPermission.KICK.toString())) {
            session.sendChat("You don't have the permission to kick.");
            return;
        }

        var toKick = groupDAO.getMemberNameByHash(group.getId(), String.valueOf(data.getTargetUserID()));
        if (toKick.isEmpty()) {
            session.sendChat("Target member doesn't exist.");
            return;
        }

        var kickeeName = toKick.get();
        if (kickeeName.equals(user)) {
            session.sendChat("You can't kick yourself.");
            return;
        }

        groupDAO.removeGroupMember(group.getId(), kickeeName);

        session.sendGroupChat(kickeeName + " was kicked by " + user);
    }
}