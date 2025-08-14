package dev.racel.handler.event.impl.group.manage;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupPermission;
import dev.racel.entity.GroupRole;
import dev.racel.entity.message.MemberManageMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class DemoteMemberEventHandler implements OrbitEventHandler<MemberManageMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "groupDemoteMember";
    }

    @Override
    public void handle(Session session, MemberManageMessage data) {
        var user = session.getOrbitUser().getName();
        var group = groupDAO.getGroupByName(data.getGroupName()).orElseThrow();

        var role = groupDAO.getGroupRoleNameByMemberName(group.getId(), user);
        if (!groupDAO.hasGroupRolePermission(group.getId(),
                role,
                GroupPermission.KICK.toString())) {
            session.sendChat("You don't have the permission to demote.");
            return;
        }

        var targetOpt = groupDAO.getMemberNameByHash(group.getId(), String.valueOf(data.getTargetUserID()));

        if(targetOpt.isEmpty()) {
           session.sendChat("Member you demote doesn't exist.");
           return;
        }
        var targetName =  targetOpt.get();

        if (targetName.equals(user)) {
            session.sendChat("You cannot demote yourself.");
            return;
        }

        var targetRoleName = groupDAO.getGroupRoleNameByMemberName(group.getId(), targetName);
        var targetRole = GroupRole.valueOf(targetRoleName);

        if(targetRole.getPriority() == 1) {
            session.sendChat("Member cannot be demoted anymore.");
            return;
        }

        var nextRole = GroupRole.getByPriority(targetRole.getPriority() - 1);

        assert nextRole != null;
        groupDAO.updateGroupMemberRole(group.getId(),
                targetName,
                nextRole.toString());

        session.sendGroupChat(targetName + " has been demoted to " + targetRoleName);
    }
}
