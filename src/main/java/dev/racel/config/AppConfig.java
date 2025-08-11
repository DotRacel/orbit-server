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

public class AppConfig {
    private static AppConfig INSTANCE;

    private static final String VALID_TOKEN = System.getenv("API_TOKEN")
            != null ? System.getenv("API_TOKEN") : "somehow-fixed-api-token-ald8S712bLJ";

    private AppConfig() {
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
                            if (!VALID_TOKEN.equals(token)) {
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
            INSTANCE = new AppConfig();
        }
        return INSTANCE;
    }
}
