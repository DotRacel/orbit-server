package dev.racel.config;

import dev.racel.handler.api.*;
import io.javalin.Javalin;
import org.tinylog.Logger;

public class AppConfig {
    private static AppConfig INSTANCE;

    private AppConfig() {
        var app = Javalin.create()
                .get("/", new ServerVersionHandler())

                .get("/api/getPartneredServers", new PartneredServerHandler())

                .get("/api/mixin", new KillSwitchAHandler())
                .get("/api/schematic", new KillSwitchBHandler())

                .get("/public/getSchemShare", new SchemShareHandler())

                .start(8888);

        Logger.info("Web server initialized");
    }

    public static AppConfig getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AppConfig();
        }
        return INSTANCE;
    }
}
