package dev.racel.util;

import dev.racel.entity.OrbitUser;
import dev.racel.entity.message.ClientInfoMessage;

public class UserUtil {
    public static void updateUserByClientInfo(OrbitUser user, ClientInfoMessage clientInfoMessage) {
        user.setHwid(clientInfoMessage.getHwid());
        user.setLast_ign(clientInfoMessage.getInGameName());
        user.setLast_uuid(clientInfoMessage.getUUID());
        user.setLast_version(clientInfoMessage.getVersion());
    }
}
