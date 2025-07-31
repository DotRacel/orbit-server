package dev.racel.util;

import dev.racel.entity.OrbitUser;
import dev.racel.entity.event.ClientInfo;

public class UserUtil {
    public static void updateUserByClientInfo(OrbitUser user, ClientInfo clientInfo) {
        user.setHwid(clientInfo.getHwid());
        user.setLast_ign(clientInfo.getInGameName());
        user.setLast_uuid(clientInfo.getUUID());
        user.setLast_version(clientInfo.getVersion());
    }
}
