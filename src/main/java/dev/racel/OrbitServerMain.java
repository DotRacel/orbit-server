package dev.racel;

import dev.racel.config.AppConfig;
import dev.racel.config.WsConfig;
import dev.racel.entity.event.ServerVersion;

public class OrbitServerMain {
    public final static ServerVersion serverVersion = new ServerVersion("orbit-server", "1.0-SNAPSHOT");

    private static AppConfig appConfig;
    private static WsConfig wsConfig;

    public static void main(String[] args) {
        appConfig = new AppConfig();
        wsConfig = new WsConfig();
    }
}
