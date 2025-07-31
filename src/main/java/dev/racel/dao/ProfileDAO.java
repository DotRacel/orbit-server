package dev.racel.dao;

import dev.racel.entity.Profile;
import dev.racel.mapper.ProfileMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

public interface ProfileDAO {
    @SqlUpdate("""
            CREATE TABLE IF NOT EXISTS profiles (
                id INT AUTO_INCREMENT PRIMARY KEY,
                profile_id CHAR(10) NOT NULL,
                owner varchar(100) NOT NULL,
                content text NOT NULL,
                FOREIGN KEY (owner) REFERENCES users(name)
            )""")
    void createTable();

    @SqlQuery("""
    SELECT * FROM profiles WHERE profile_id = ?
    """)
    @RegisterRowMapper(ProfileMapper.class)
    Optional<Profile> getProfileById(String id);

    @SqlUpdate("""
            INSERT INTO profiles(profile_id, owner, content) 
                        VALUES(:profile.profileId, :profile.owner, :profile.content)""")
    void insertProfile(@BindBean("profile") Profile profile);
}
