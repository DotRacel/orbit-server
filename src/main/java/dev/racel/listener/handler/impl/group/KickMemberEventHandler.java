package dev.racel.listener.handler.impl.group;

import dev.racel.entity.message.MemberManageMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class KickMemberEventHandler implements OrbitEventHandler<MemberManageMessage> {
    @Override
    public String getName() {
        return "groupKickMember";
    }

    @Override
    public void handle(Session session, MemberManageMessage data) {

    }
}
