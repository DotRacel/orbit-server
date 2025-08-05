package dev.racel.handler;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

/**
 * KillSwitch B
 * Verifies purchase id
 */
public class KillSwitchBHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        var purchaseId = context.queryParam("p");
        var uuid = context.queryParam("u");
        var inGameName = context.queryParam("i");
        var hardwareId = context.queryParam("h");

        context.result("false;socketInfo");
    }
}
