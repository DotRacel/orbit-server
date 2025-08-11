package dev.racel.handler.event.impl.group.share;

import dev.racel.entity.message.PatchCrumbMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class SendSharePatchCrumbEventHandler implements OrbitEventHandler<PatchCrumbMessage> {
    @Override
    public String getName() {
        return "sendSharePatchCrumb";
    }

    @Override
    public void handle(Session session, PatchCrumbMessage data) {
        session.sendGroupMessage("sharePatchCrumb", data);
    }
}
