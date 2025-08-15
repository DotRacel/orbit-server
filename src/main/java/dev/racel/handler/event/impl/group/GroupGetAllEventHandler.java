package dev.racel.handler.event.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupPermission;
import dev.racel.entity.GroupRole;
import dev.racel.entity.GroupWaypoint;
import dev.racel.entity.message.GroupInfoMessage;
import dev.racel.entity.message.GroupsMessage;
import dev.racel.entity.message.WaypointMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupGetAllEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "groupGetAll";
    }

    @Override
    public void handle(Session session, Object data) {
        var user = session.getOrbitUser();
        var groups = DbConfig.getInstance().getGroupDAO().getUserGroupNames(user.getName());
        GroupsMessage groupsMessage = new GroupsMessage(new ArrayList<>());
        groups.forEach(group -> {
            groupsMessage.getGroups().add(buildMessage(group));
        });
        session.sendMessage(getName(), groupsMessage);
        Logger.info("User {} requested for all groups", user.getName());
    }

    GroupInfoMessage buildMessage(String groupName) {
        GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();
        var group = groupDAO.getGroupByName(groupName).orElseThrow();
        var memberList = groupDAO.getGroupMembers(group.getId());
        Map<Long, String> memberNames = new HashMap<>();
        Map<Long, GroupRole> groupMembers = new HashMap<>();
        for (var member : memberList) {
            long key = member.hashCode();
            memberNames.put(key, member);
            groupMembers.put(key, GroupRole.valueOf(groupDAO.getGroupRoleNameByMemberName(group.getId(), member)));
        }
        Map<GroupRole, List<GroupPermission>> rolePermission = GroupRole.getPredefinedPermissions();
        Map<String, String> groupSchematics = groupDAO.getGroupSchematics(group.getId());
        Map<String, WaypointMessage> waypoints = new HashMap<>();
        var waypointList = groupDAO.getGroupWaypoints(groupName);
        waypointList.forEach(waypoint -> {
            waypoints.put(waypoint.getId(), new WaypointMessage(
                    groupName,
                    waypoint.getName(),
                    waypoint.getX(),
                    waypoint.getY(),
                    waypoint.getZ(),
                    waypoint.getServerIP()
            ));
        });
        List<String> logs = new ArrayList<>();
        return new GroupInfoMessage(
                groupName, memberNames, groupMembers, rolePermission, groupSchematics, waypoints, logs
        );
    }
}
