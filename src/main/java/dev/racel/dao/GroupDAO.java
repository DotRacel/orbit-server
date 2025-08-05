package dev.racel.dao;

import dev.racel.entity.Group;
import dev.racel.mapper.GroupMapper;
import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.config.ValueColumn;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GroupDAO {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS groups (
            id INTEGER PRIMARY KEY,
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
            FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
            FOREIGN KEY (user_name) REFERENCES users(name) ON DELETE CASCADE,
            FOREIGN KEY (role_name) REFERENCES group_roles(role_name) ON DELETE CASCADE);
""")
    void createGroupMembersTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_role_permissions (
            group_id INT NOT NULL,
            role_name varchar(50) NOT NULL,
            permission varchar(20) NOT NULL,
            FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
            FOREIGN KEY (role_name) REFERENCES group_roles(role_name) ON DELETE CASCADE);
""")
    void createGroupRolePermissionsTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_logs (
            id INTEGER PRIMARY KEY,
            group_id INT NOT NULL,
            message TEXT NOT NULL,
            FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE);
""")
    void createGroupLogsTable();

    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS group_schematics(
            id INTEGER PRIMARY KEY,
            group_id INT NOT NULL,
            schem_id VARCHAR(100) NOT NULL,
            schem_name VARCHAR(100) NOT NULL,
            FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
        )
""")
    void createGroupSchematicsTable();

    @SqlQuery("SELECT * FROM groups WHERE group_name = ?")
    @RegisterRowMapper(GroupMapper.class)
    Optional<Group> getGroupByName(String name);

    @SqlUpdate("INSERT INTO groups(group_name, password) VALUES(:group.groupName, :group.password);")
    void createGroup(@BindBean("group") Group group);

    @SqlUpdate("INSERT INTO group_roles(group_id, role_name) VALUES(?, ?)")
    void addGroupRole(String groupId, String roleName);

    @SqlQuery("SELECT role_name FROM group_members WHERE group_id = ? AND user_name = ?")
    String getGroupRoleNameByMemberName(int groupId, String memberName);

    @SqlUpdate("INSERT INTO group_role_permissions(group_id, role_name, permission) VALUES(?, ?, ?)")
    void addGroupRolePermission(int groupId, String roleName, String permission);

    @SqlUpdate("DELETE FROM group_role_permissions WHERE group_id = ? AND role_name = ? AND permission = ?")
    void removeGroupRolePermission(int groupId, String roleName, String permission);

    @SqlQuery("SELECT 1 FROM group_role_permissions WHERE group_id = ? AND role_name = ? AND permission = ?")
    boolean hasGroupRolePermission(int groupId, String roleName, String permission);

    @SqlQuery("SELECT permission FROM group_role_permissions WHERE group_id = ? AND role_name = ?")
    List<String> getGroupRolePermissions(int groupId, String roleName);

    @SqlUpdate("DELETE FROM group_logs WHERE group_id = ?;")
    void removeGroup(int groupId);

    @SqlUpdate("INSERT INTO group_logs(group_id, message) VALUES(?, ?)")
    void addGroupLog(int groupId, String message);

    @SqlUpdate("INSERT INTO group_roles(group_id, role_name) VALUES(?, ?)")
    void addGroupRole(int groupId, String roleName);

    @SqlUpdate("DELETE FROM group_roles WHERE group_id = ? AND role_name = ?")
    void removeGroupRole(int groupId, String roleName);

    @SqlQuery("SELECT message FROM group_logs WHERE group_id = ?")
    List<String> getGroupLogs(int groupId);

    @SqlQuery("SELECT user_name FROM group_members WHERE group_id=?")
    List<String> getGroupMembers(int groupId);

    @SqlQuery("SELECT 1 FROM group_members WHERE group_id = ? AND user_name = ?")
    boolean isUserInGroup(int groupId, String username);

    @SqlQuery("""
            SELECT last_uuid
            FROM group_members
            LEFT JOIN users ON users.name = group_members.user_name
            WHERE group_id = ?
            """)
    List<String> getGroupMembersUuid(int groupId);

    @SqlQuery("""
        SELECT g.group_name
        FROM groups g
        JOIN group_members m ON g.id = m.group_id
        WHERE m.user_name = ?
    """)
    List<String> getUserGroupNames(String userName);

    @SqlUpdate("INSERT INTO group_members(group_id, user_name, role_name) VALUES (?, ?, ?)")
    void addGroupMember(int groupId, String userName, String role_name);

    @SqlUpdate("DELETE FROM group_members WHERE group_id = ? AND user_name = ?")
    void removeGroupMember(int groupId, String userName);

    @SqlUpdate("INSERT INTO group_schematics(group_id, schem_id, schem_name) VALUES(?, ?, ?)")
    void addGroupSchematic(int groupId, String schemId, String schemName);

    @SqlUpdate("DELETE FROM group_schematics WHERE group_id = ? AND schem_id = ?")
    void removeGroupSchematicById(int groupId, String schemId);

    @SqlQuery("SELECT schem_name FROM group_schematics where group_id = ? AND schem_name = ?")
    Optional<String> getGroupSchematicName(int groupId, String schemName);

    @SqlQuery("SELECT schem_id, schem_name from group_schematics WHERE group_id = ?")
    @KeyColumn("schem_id")
    @ValueColumn("schem_name")
    Map<String, String> getGroupSchematics(int groupId);
}
