package dev.racel.dao;

import dev.racel.entity.Group;
import dev.racel.mapper.GroupMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface GroupDAO {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS groups (
            id INT AUTO_INCREMENT PRIMARY KEY,
            group_name VARCHAR(50) UNIQUE NOT NULL,
            password varchar(100) NOT NULL);
""")
    void createGroupsTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_roles (
            group_id INT NOT NULL,
            role_name varchar(50) NOT NULL);
""")
    void createGroupRolesTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_members (
            group_id INT NOT NULL,
            user_name varchar(100) NOT NULL,
            role_name varchar(50) NOT NULL,
            FOREIGN KEY (group_id) REFERENCES groups(id),
            FOREIGN KEY (user_name) REFERENCES users(name),
            FOREIGN KEY (role_name) REFERENCES group_roles(role_name));
""")
    void createGroupMembersTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_role_permissions (
            group_id INT NOT NULL,
            role_name varchar(50) NOT NULL,
            permission varchar(20) NOT NULL,
            FOREIGN KEY (group_id) REFERENCES groups(id),
            FOREIGN KEY (role_name) REFERENCES group_roles(role_name));
""")
    void createGroupRolePermissionsTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_logs (
            id INT AUTO_INCREMENT PRIMARY KEY,
            group_id INT NOT NULL,
            message TEXT NOT NULL,
            FOREIGN KEY (group_id) REFERENCES groups(id));
""")
    void createGroupLogsTable();

    @SqlQuery("""
        SELECT * FROM groups WHERE group_name = ?
""")
    @RegisterRowMapper(GroupMapper.class)
    Optional<Group> getGroupByName(String name);

    @SqlUpdate("""
        INSERT INTO groups(group_name, password) VALUES(:group.groupName, :group.password);
""")
    void createGroup(@BindBean("group") Group group);

    @SqlUpdate("""
        INSERT INTO group_roles(group_id, role_name) VALUES(?, ?)
""")
    void addGroupRole(String groupId, String roleName);

    @SqlUpdate("INSERT INTO group_role_permissions(group_id, role_name, permission) VALUES(?, ?, ?)")
    void addGroupRolePermission(int groupId, String roleName, String permission);

    @SqlUpdate("DELETE FROM group_role_permissions WHERE group_id = ? AND role_name = ? AND permission = ?")
    void removeGroupRolePermission(int groupId, String roleName, String permission);

    @SqlQuery("SELECT 1 FROM group_role_permissions WHERE group_id = ? AND role_name = ? AND permission = ?")
    boolean hasGroupRolePermission(int groupId, String roleName, String permission);

    @SqlQuery("SELECT permission FROM group_role_permissions WHERE group_id = ? AND role_name = ?")
    List<String> getGroupRolePermissions(int groupId, String roleName);

    @SqlUpdate("""
        DELETE FROM group_logs WHERE group_id = ?;
        DELETE FROM group_role_permissions WHERE group_id = ?;
        DELETE FROM group_members WHERE group_id = ?;
        DELETE FROM group_roles WHERE group_id = ?;
        DELETE FROM groups WHERE id = ?;
    """)
    void removeGroup(int groupId);

    @SqlUpdate("INSERT INTO group_logs(group_id, message) VALUES(?, ?)")
    void addGroupLog(int groupId, String message);

    @SqlQuery("SELECT message FROM group_logs WHERE group_id = ?")
    List<String> getGroupLogs(int groupId);

    @SqlQuery("SELECT user_name FROM group_members WHERE group_id=?")
    List<String> getGroupMembers(int groupId);

    @SqlQuery("""
        SELECT g.group_name
        FROM groups g
        JOIN group_members m ON g.id = m.group_id
        WHERE m.user_name = ?
    """)
    List<String> getUserGroupNames(String userName);
}
