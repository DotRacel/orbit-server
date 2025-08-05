package dev.racel.handler.event.impl.group.manage;

import dev.racel.entity.message.MemberManageMessage;
import dev.racel.handler.event.OrbitEventHandler;
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
