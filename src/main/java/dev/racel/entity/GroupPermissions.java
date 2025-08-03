package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupPermissions {
    MANAGE_PERMISSIONS("Manage Perms", "Manage the group permissions."),
    PROMOTE("Promote", "Promote a group member."),
    DEMOTE("Demote", "Demote a group member."),
    KICK("Kick", "Kick a member from the group."),
    DISBAND("Disband", "Disband the group."),
    VIEW_LOGS("View Logs", "View the group logs."),
    CLEAR_LOGS("Clear Logs", "Clear the logs"),
    UPLOAD_SCHEMATICS("Upload Schem", "Upload a schematic to the group."),
    REMOVE_SCHEMATICS("Remove Schem", "Remove a uploaded group schematic."),
    UPLOAD_WAYPOINTS("Upload Waypoint", "Upload a waypoint to the group."),
    REMOVE_WAYPOINTS("Remove Waypoint", "Remove an uploaded group waypoint."),
    CHAT("Chat", "Use the group chat."),
    PINGS("Ping", "Use the group ping system."),
    F_RALLY("F Rally", "Send rally points to group members.");

    final String name;
    final String desc;
}
