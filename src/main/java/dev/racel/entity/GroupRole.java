package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

import static dev.racel.entity.GroupPermission.*;

@AllArgsConstructor
@Getter
public enum GroupRole {
    OWNER("Owner", 4),
    ADMIN("Admin", 3),
    MODERATOR("Moderator", 2),
    MEMBER("Member", 1);

    final String name;
    final int priority;

    public static GroupRole getByPriority(int priority) {
        priority %= 5;
        for (GroupRole groupRole : values()) {
            if (groupRole.priority == priority) {
                return groupRole;
            }
        }
        return null;
    }

    public static Map<GroupRole, List<GroupPermission>> getPredefinedPermissions() {
        return Map.of(
                OWNER, List.of(
                        PROMOTE, DEMOTE, KICK, DISBAND, VIEW_LOGS, CLEAR_LOGS,
                        UPLOAD_SCHEMATICS, REMOVE_SCHEMATICS, UPLOAD_WAYPOINTS, REMOVE_WAYPOINTS,
                        CHAT, PINGS, F_RALLY),
                ADMIN, List.of(
                        PROMOTE, DEMOTE, KICK, CLEAR_LOGS, UPLOAD_SCHEMATICS, REMOVE_SCHEMATICS,
                        UPLOAD_WAYPOINTS, REMOVE_WAYPOINTS
                ),
                MODERATOR, List.of(
                        KICK,
                        UPLOAD_SCHEMATICS, REMOVE_SCHEMATICS),
                MEMBER, List.of(
                        UPLOAD_SCHEMATICS
                )
        );
    }
}
