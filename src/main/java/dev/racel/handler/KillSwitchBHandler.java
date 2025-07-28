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
        var purchaseId = context.formParam("p");
        var uuid = context.formParam("u");
        var inGameName = context.formParam("i");
        var hardwareId = context.formParam("h");

        context.result("false;socketInfo");
    }
}
