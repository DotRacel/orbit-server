package dev.racel.config;

import dev.racel.dao.GroupDAO;
import dev.racel.dao.ProfileDAO;
import dev.racel.dao.UserDAO;
import lombok.Getter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

public class DbConfig {
    private static DbConfig INSTANCE;

    @Getter
    UserDAO userDAO;
    @Getter
    ProfileDAO profileDAO;
    @Getter
    GroupDAO groupDAO;

    public DbConfig() {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:orbit.db");
        jdbi.installPlugin(new SqlObjectPlugin());
        this.userDAO = jdbi.onDemand(UserDAO.class);
        this.userDAO.createTable();
        this.profileDAO = jdbi.onDemand(ProfileDAO.class);
        this.profileDAO.createTable();
        this.groupDAO = jdbi.onDemand(GroupDAO.class);
        this.groupDAO.createGroupsTable();
        this.groupDAO.createGroupLogsTable();
        this.groupDAO.createGroupMembersTable();
        this.groupDAO.createGroupRolesTable();
        this.groupDAO.createGroupRolePermissionsTable();

        Logger.info("Database initialized");
    }

    public static DbConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbConfig();
        }
        return INSTANCE;
    }
}
