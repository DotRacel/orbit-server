package dev.racel.handler;

import dev.racel.config.DbConfig;
import dev.racel.dao.SchemDAO;
import dev.racel.entity.message.ErrorMessage;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SchemShareHandler implements Handler {
    private final SchemDAO schemDAO = DbConfig.getInstance().getSchemDAO();

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var id = ctx.queryParam("schematicID");
        if (id == null) {
            ctx.json(new ErrorMessage("Couldn't find a SchemShare with that id."));
            return;
        }

        var schem = schemDAO.getSchemContentById(id);
        if(schem.isEmpty()) {
            ctx.json(new ErrorMessage("Couldn't find a SchemShare with that id."));
            return;
        }

        var schem1 = schem.get();
        ctx.json(new SchematicResponse(schem1.getX(),
                schem1.getY(),
                schem1.getZ(),
                Arrays.stream(ArrayUtils.toObject(schem1.getContent())).map(Byte::intValue).collect(Collectors.toList())));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class SchematicResponse {
        int x, y, z;
        List<Integer> schematic;
    }
}
