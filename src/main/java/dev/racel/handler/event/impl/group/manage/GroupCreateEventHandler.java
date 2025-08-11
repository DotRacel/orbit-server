package dev.racel.handler.event.impl.group.manage;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.Group;
import dev.racel.entity.GroupRole;
import dev.racel.entity.message.GroupMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.handler.event.impl.group.GroupGetAllEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

import static dev.racel.entity.GroupRole.*;

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

        Group finalGroup = group;
        Group finalGroup1 = group;
        GroupRole.getPredefinedPermissions().forEach((role, perms) -> {
            groupDAO.addGroupRole(finalGroup1.getId(), role.toString());
            perms.forEach(perm -> {
                groupDAO.addGroupRolePermission(finalGroup.getId(), role.toString(), perm.toString());
            });
        });

        groupDAO.addGroupMember(group.getId(),
                user.getName(),
                OWNER.toString(),
                String.valueOf(user.getName().hashCode()));

        new GroupGetAllEventHandler().handle(session, null);

        Logger.info("User {} created a group {} with password {}",
                user.getName(),
                group.getGroupName(),
                group.getPassword());
    }
}
