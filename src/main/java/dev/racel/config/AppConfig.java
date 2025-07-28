package dev.racel.config;

import dev.racel.handler.*;
import io.javalin.Javalin;
import org.tinylog.Logger;

public class AppConfig {
    public AppConfig() {
        var app = Javalin.create()
                .get("/", new ServerVersionHandler())

                .get("/api/getPartneredServers", new PartneredServerHandler())

                .get("/api/mixin", new KillSwitchAHandler())
                .get("/api/schematic", new KillSwitchBHandler())

                .start(8080);

        Logger.info("Server started");
    }
}
