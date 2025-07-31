package dev.racel.dao;

import dev.racel.entity.OrbitUser;
import dev.racel.mapper.UserMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

public interface UserDAO {
    @SqlUpdate("""
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                purchase_id CHAR(32) NOT NULL,
                hwid VARCHAR(100),
                last_ign varchar(100),
                last_uuid varchar(100),
                last_version varchar(20)
            )""")
    void createTable();

    @SqlUpdate("""
            INSERT INTO users(name, purchase_id)
                        VALUES(?, ?)""")
    void createNewUser(String name,  String purchase_id);

    @SqlQuery("""
            SELECT *
            FROM users
            WHERE name=?""")
    @RegisterRowMapper(UserMapper.class)
    Optional<OrbitUser> getUserByName(String name);

    @SqlQuery("""
            SELECT *
            FROM users
            WHERE purchase_id=?
            """)
    @RegisterRowMapper(UserMapper.class)
    Optional<OrbitUser> getUserByPurchaseId(String purchase_id);

    @SqlUpdate("""
            UPDATE users SET name = :user.name, 
                    purchase_id = :user.purchaseId,
                    hwid = :user.hwid,
                    last_ign = :user.last_ign,
                    last_uuid = :user.last_uuid,
                    last_version = :user.last_version
            WHERE name = :user.name""")
    void updateUser(@BindBean("user") OrbitUser user);
}
