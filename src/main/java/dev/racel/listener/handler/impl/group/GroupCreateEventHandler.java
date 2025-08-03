package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.Group;
import dev.racel.entity.GroupPermissions;
import dev.racel.entity.GroupRoles;
import dev.racel.entity.event.GroupMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

import static dev.racel.entity.GroupRoles.*;

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
        group = groupDAO.getGroupByName(group.getGroupName()).get();
        groupDAO.addGroupRole(group.getId(), ADMIN.getName());
        groupDAO.addGroupRole(group.getId(), MODERATOR.getName());
        groupDAO.addGroupRole(group.getId(), MEMBER.getName());

        for(GroupPermissions perm : GroupRoles.getAdminPermissions()) {
            groupDAO.addGroupRolePermission(group.getId(), ADMIN.getName(), perm.getName());
        }
        for(GroupPermissions perm : GroupRoles.getModeratorPermissions()) {
            groupDAO.addGroupRolePermission(group.getId(), MODERATOR.getName(), perm.getName());
        }
        for(GroupPermissions perm : GroupRoles.getMemberPermissions()) {
            groupDAO.addGroupRolePermission(group.getId(), MEMBER.getName(), perm.getName());
        }

        groupDAO.addGroupMember(group.getId(), user.getName(), ADMIN.getName());

        Logger.info("User {} created a group {} with password {}",
                user.getName(),
                group.getGroupName(),
                group.getPassword());
    }
}
