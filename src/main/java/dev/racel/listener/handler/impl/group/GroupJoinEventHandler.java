package dev.racel.listener.handler.impl.group;

import dev.racel.entity.event.GroupMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;

public class GroupJoinEventHandler implements OrbitEventHandler<GroupMessage> {
    @Override
    public String getName() {
        return "groupJoin";
    }

    @Override
    public void handle(Session session, GroupMessage data) {

    }
}
