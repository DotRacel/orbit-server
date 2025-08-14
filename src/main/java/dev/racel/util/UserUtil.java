package dev.racel.util;

import dev.racel.entity.OrbitUser;
import dev.racel.entity.message.ClientInfoMessage;

public class UserUtil {
    public static void updateUserByClientInfo(OrbitUser user, ClientInfoMessage clientInfoMessage) {
        user.setHwid(clientInfoMessage.getHwid());
        user.setLastIgn(clientInfoMessage.getInGameName());
        user.setLastUUID(clientInfoMessage.getUUID());
        user.setLastVersion(clientInfoMessage.getVersion());
    }
}
