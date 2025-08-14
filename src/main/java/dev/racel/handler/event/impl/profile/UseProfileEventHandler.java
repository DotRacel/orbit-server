package dev.racel.handler.event.impl.profile;

import dev.racel.config.DbConfig;
import dev.racel.entity.message.UseProfileMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class UseProfileEventHandler implements OrbitEventHandler<UseProfileMessage> {

    @Override
    public String getName() {
        return "useProfile";
    }

    @Override
    public void handle(Session session, UseProfileMessage data) {
        var user = session.getOrbitUser();
        var profile = DbConfig.getInstance().getProfileDAO().getProfileById(data.getProfileId());

        if(profile.isEmpty()) {
            Logger.info("User {} requested an unknown profile {}", user.getName(), data.getProfileId());
            return;
        }

        session.sendMessage("useProfile", profile.get().getContent());
    }
}
