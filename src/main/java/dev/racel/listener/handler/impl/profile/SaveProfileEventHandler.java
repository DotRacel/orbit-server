package dev.racel.listener.handler.impl.profile;

import dev.racel.config.DbConfig;
import dev.racel.entity.Profile;
import dev.racel.entity.message.UseProfileMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import dev.racel.util.JsonUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.tinylog.Logger;

public class SaveProfileEventHandler implements OrbitEventHandler<String> {
    @Override
    public String getName() {
        return "saveProfile";
    }

    @Override
    public void handle(Session session, String data) {
        var user = session.getOrbitUser().get();

        if(!JsonUtil.isValidJson(data)) {
            Logger.error("Invalid json received from user {}, partial data: {}",
                    user.getName(), data.substring(0, 64));
            return;
        }

        Profile profile = new Profile(
                RandomStringUtils.insecure().nextAlphabetic(10),
                user.getName(),
                data
        );

        DbConfig.getInstance().getProfileDAO().insertProfile(profile);
        session.sendMessage("addProfile", new UseProfileMessage(profile.getProfileId()));
        Logger.info("Profile {} has been saved by user {}", profile.getProfileId(), user.getName());
    }
}
