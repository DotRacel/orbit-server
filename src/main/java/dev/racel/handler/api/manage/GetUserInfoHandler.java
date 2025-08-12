package dev.racel.handler.api.manage;

import dev.racel.config.AppConfig;
import dev.racel.config.DbConfig;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetUserInfoHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var user = ctx.queryParam("user");

        if(user == null || user.isEmpty()) {
            ctx.status(400)
                    .contentType("application/json")
                    .json(new AppConfig.ErrorResponse("missing parameter 'user'"));
            return;
        }

        var userOpt = DbConfig.getInstance().getUserDAO().getUserByName(user);
        if(userOpt.isEmpty()) {
            ctx.status(400)
                    .contentType("application/json")
                    .json(new AppConfig.ErrorResponse("user not found"));
            return;
        }

        var orbitUser = userOpt.get();
        ctx.status(200)
                .contentType("application/json")
                .json(orbitUser);
    }
}
