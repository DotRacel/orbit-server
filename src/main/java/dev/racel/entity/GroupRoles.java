package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import static dev.racel.entity.GroupPermissions.*;

@AllArgsConstructor
@Getter
public enum GroupRoles {
    ADMIN("Admin"),
    MODERATOR("Moderator"),
    MEMBER("Member");

    final String name;

    public static List<GroupPermissions> getAdminPermissions() {
        return List.of(
                PROMOTE, DEMOTE,
                KICK,
                CLEAR_LOGS,
                UPLOAD_SCHEMATICS, REMOVE_SCHEMATICS,
                UPLOAD_WAYPOINTS, REMOVE_WAYPOINTS
        );
    }

    public static List<GroupPermissions> getModeratorPermissions() {
        return List.of(
                KICK,
                UPLOAD_SCHEMATICS, REMOVE_SCHEMATICS
        );
    }

    public static List<GroupPermissions> getMemberPermissions() {
        return List.of(UPLOAD_SCHEMATICS);
    }
}
