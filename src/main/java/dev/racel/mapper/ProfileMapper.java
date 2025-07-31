package dev.racel.mapper;

import dev.racel.entity.Profile;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileMapper implements RowMapper<Profile> {
    @Override
    public Profile map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Profile(rs.getString("profile_id"),
                rs.getString("owner"),
                rs.getString("content"));
    }
}
