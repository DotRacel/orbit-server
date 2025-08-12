package dev.racel.config;

import static io.javalin.apibuilder.ApiBuilder.*;
import dev.racel.handler.api.*;
import dev.racel.handler.api.manage.CreateUserHandler;
import dev.racel.handler.api.manage.DeleteUserHandler;
import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tinylog.Logger;

import java.util.ResourceBundle;

public class AppConfig {
    private static AppConfig INSTANCE;

    private final String API_TOKEN;

    private AppConfig(String apiToken, int port) {
        this.API_TOKEN = apiToken;
        Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/api", () -> {
                    get("/getPartneredServers", new PartneredServerHandler());
                    get("/mixin", new KillSwitchAHandler());
                    get("/schematic", new KillSwitchBHandler());

                    path("/manage", () -> {
                        before("*", ctx -> {
                            String authHeader = ctx.header("Authorization");
                            if (authHeader == null) {
                                throw new UnauthorizedResponse("Missing Authorization Header");
                            }
                            String token = authHeader.startsWith("Bearer ")
                                    ? authHeader.substring(7)
                                    : authHeader;
                            if (!API_TOKEN.equals(token)) {
                                throw new UnauthorizedResponse("Invalid Token");
                            }
                        });

                        get("/createUser", new CreateUserHandler());
                        get("/deleteUser", new DeleteUserHandler());
                    });
                });

                path("/public", () -> {
                   get("/getSchemShare", new SchemShareHandler());
                });

                get("/", new ServerVersionHandler());
            });
        }).start(8888);

        Logger.info("Web server initialized");
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ErrorResponse {
        String error;
    }

    public static AppConfig getInstance() {
        if(INSTANCE == null) {
            var bundle = ResourceBundle.getBundle("config");

            INSTANCE = new AppConfig(bundle.getString("api_token"),
                    Integer.parseInt(bundle.getString("app.port")));
        }
        return INSTANCE;
    }
}
