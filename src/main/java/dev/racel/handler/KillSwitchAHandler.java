package dev.racel.handler;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

/**
 * KillSwitch A
 * Manipulates com.orbitclient.orbitclient.util.RetardedList field shouldDo
 * If it is true, then will crash.
 *
 * We should return false
 */
public class KillSwitchAHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {
        context.result("false");
    }
}
