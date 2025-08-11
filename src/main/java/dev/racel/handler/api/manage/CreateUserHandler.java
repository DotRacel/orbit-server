package dev.racel.handler.api.manage;

import dev.racel.config.AppConfig;
import dev.racel.config.DbConfig;
import dev.racel.dao.UserDAO;
import dev.racel.entity.message.ErrorMessage;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

public class CreateUserHandler implements Handler {
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

        if (userDAO.getUserByName(name).isPresent()) {
            ctx.status(400)
                    .contentType("application/json")
                    .json(new AppConfig.ErrorResponse("username already exists"));
            return;
        }

        var purchaseId = "orbit-" + RandomStringUtils.secure().nextAlphanumeric(10);
        userDAO.createNewUser(name, purchaseId);
        ctx.status(201)
                .contentType("application/json")
                .json(new NewUserInfo(name, purchaseId));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class NewUserInfo {
        String username, purchaseId;
    }
}
