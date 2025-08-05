package dev.racel.mapper;

import dev.racel.entity.Schematic;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemMapper implements RowMapper<Schematic> {
    @Override
    public Schematic map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Schematic(
                rs.getString("schem_id"),
                rs.getString("schem_name"),
                rs.getInt("x"),
                rs.getInt("y"),
                rs.getInt("z"),
                rs.getBytes("content")
        );
    }
}
