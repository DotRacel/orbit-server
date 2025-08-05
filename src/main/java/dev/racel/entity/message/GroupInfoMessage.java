package dev.racel.entity.message;

import dev.racel.entity.GroupPermission;
import dev.racel.entity.GroupRole;
import dev.racel.entity.GroupWaypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupInfoMessage {
    String name;
    Map<Long, String> memberNames;
    Map<Long, GroupRole> groupMembers;
    Map<GroupRole, List<GroupPermission>> rolePermission;
    Map<String, String> groupSchematics;
    Map<String, GroupWaypoint> waypoints;
    List<String> logs;
}
