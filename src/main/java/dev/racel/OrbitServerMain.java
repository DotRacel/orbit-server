package dev.racel;

import dev.racel.config.AppConfig;
import dev.racel.config.DbConfig;
import dev.racel.config.WsConfig;
import dev.racel.entity.ServerVersion;

public class OrbitServerMain {
    public final static ServerVersion serverVersion = new ServerVersion("orbit-server", "1.0-SNAPSHOT");

    private static AppConfig appConfig;
    private static WsConfig wsConfig;
    private static DbConfig dbConfig;

    public static void main(String[] args) {
        dbConfig = DbConfig.getInstance();
        appConfig = AppConfig.getInstance();
        wsConfig = WsConfig.getInstance();
    }
}
