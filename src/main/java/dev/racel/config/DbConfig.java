package dev.racel.config;

import dev.racel.dao.UserDAO;
import lombok.Getter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

public class DbConfig {
    private static DbConfig INSTANCE;

    @Getter
    UserDAO userDAO;

    public DbConfig() {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite:orbit.db");
        jdbi.installPlugin(new SqlObjectPlugin());
        this.userDAO = jdbi.onDemand(UserDAO.class);

        Logger.info("Database initialized");
    }

    public static DbConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbConfig();
        }
        return INSTANCE;
    }
}
