package dev.racel.handler;

import dev.racel.entity.event.PartneredServers;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PartneredServerHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.json(new PartneredServers(List.of(
                new PartneredServers.Server("Hypixel", "mc.hypixel.net")
        )));
    }
}
