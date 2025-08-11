package dev.racel.handler.api.manage;

import dev.racel.config.AppConfig;
import dev.racel.config.DbConfig;
import dev.racel.dao.UserDAO;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class DeleteUserHandler implements Handler {
    private final UserDAO userDAO = DbConfig.getInstance().getUserDAO();

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var name = ctx.queryParam("name");

        if(name == null || name.isEmpty()){
            ctx.status(400)
                    .contentType("application/json")
                    .json(new AppConfig.ErrorResponse("missing parameter 'name'"));
            return;
        }

        if (userDAO.getUserByName(name).isEmpty()) {
            ctx.status(400)
                    .contentType("application/json")
                    .json(new AppConfig.ErrorResponse("user doesn't exist"));
            return;
        }

        userDAO.deleteUserByName(name);

        ctx.status(201)
                .contentType("application/json")
                .json("success");
    }
}
