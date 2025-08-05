package dev.racel.dao;

import dev.racel.entity.Schematic;
import dev.racel.mapper.SchemMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import javax.xml.validation.Schema;
import java.util.Optional;

public interface SchemDAO {
    @SqlUpdate("""
    CREATE TABLE IF NOT EXISTS schematics(
        id INTEGER PRIMARY KEY,
        schem_id VARCHAR(100) NOT NULL,
        schem_name VARCHAR(100) NOT NULL,
        x INT NOT NULL,
        y INT NOT NULL,
        z INT NOT NULL,
        content BLOB NOT NULL
    )
""")
    void createTable();

    @SqlUpdate("""
        INSERT INTO schematics(schem_id, schem_name, x, y, z, content)
        VALUES(:schem.schemId,
               :schem.schemName,
               :schem.x,
               :schem.y,
               :schem.z,
               :schem.content)
""")
    void addSchematic(@BindBean Schematic schem);

    @SqlQuery("SELECT * FROM schematics WHERE schem_id = ?")
    @RegisterRowMapper(SchemMapper.class)
    Optional<Schematic> getSchemContentById(String schemId);
}
