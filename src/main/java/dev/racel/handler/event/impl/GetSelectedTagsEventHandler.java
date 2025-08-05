package dev.racel.handler.event.impl;

import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class GetSelectedTagsEventHandler implements OrbitEventHandler<Object> {
    @Override
    public String getName() {
        return "getSelectedTags";
    }

    @Override
    public void handle(Session session, Object data) {
        //TODO: not implemented in client?
    }
}
