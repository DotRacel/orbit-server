package dev.racel;

import dev.racel.config.AppConfig;
import dev.racel.entity.ServerVersion;
import dev.racel.handler.ServerVersionHandler;

public class OrbitServerMain {
    public final static ServerVersion serverVersion = new ServerVersion("orbit-server", "1.0-SNAPSHOT");

    private static AppConfig appConfig;
    public static void main(String[] args) {
        appConfig = new AppConfig();
    }
}
