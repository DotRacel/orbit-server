package dev.racel.mapper;

import dev.racel.entity.Group;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new  Group(rs.getInt("id"), rs.getString("group_name"), rs.getString("password"));
    }
}
