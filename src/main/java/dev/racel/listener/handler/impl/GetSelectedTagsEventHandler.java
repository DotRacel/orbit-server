package dev.racel.listener.handler.impl;

import dev.racel.listener.handler.OrbitEventHandler;
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
