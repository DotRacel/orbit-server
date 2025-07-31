package dev.racel.mapper;

import dev.racel.entity.OrbitUser;
import org.eclipse.jetty.server.Authentication;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<OrbitUser> {
    @Override
    public OrbitUser map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new OrbitUser(rs.getString("name"),
                rs.getString("purchase_id"),
                rs.getString("hwid"),
                rs.getString("last_ign"),
                rs.getString("last_uuid"),
                rs.getString("last_version"),
                rs.getString("selected_group"));
    }
}
