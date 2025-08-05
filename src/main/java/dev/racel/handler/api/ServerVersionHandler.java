package dev.racel.handler.api;

import dev.racel.OrbitServerMain;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class ServerVersionHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.json(OrbitServerMain.serverVersion);
    }
}
