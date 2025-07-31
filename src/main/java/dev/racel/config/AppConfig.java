package dev.racel.config;

import dev.racel.handler.*;
import io.javalin.Javalin;
import jdk.jshell.ImportSnippet;
import org.tinylog.Logger;

public class AppConfig {
    private static AppConfig INSTANCE;

    private AppConfig() {
        var app = Javalin.create()
                .get("/", new ServerVersionHandler())

                .get("/api/getPartneredServers", new PartneredServerHandler())

                .get("/api/mixin", new KillSwitchAHandler())
                .get("/api/schematic", new KillSwitchBHandler())

                .start(8888);

        Logger.info("Web server started");
    }

    public static AppConfig getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AppConfig();
        }
        return INSTANCE;
    }
}
