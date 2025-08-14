package dev.racel.mapper;

import dev.racel.entity.GroupWaypoint;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WaypointMapper implements RowMapper<GroupWaypoint> {
    @Override
    public GroupWaypoint map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new GroupWaypoint(
                rs.getString("group_name"),
                rs.getString("name"),
                rs.getString("waypoint_id"),
                rs.getDouble("x"),
                rs.getDouble("y"),
                rs.getDouble("z"),
                rs.getString("server_ip")
        );
    }
}
