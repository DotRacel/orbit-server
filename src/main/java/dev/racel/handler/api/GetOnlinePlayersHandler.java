package dev.racel.handler.api;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class GetOnlinePlayersHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        //TODO: implement
        ctx.json(Collections.emptyList());
    }
}
