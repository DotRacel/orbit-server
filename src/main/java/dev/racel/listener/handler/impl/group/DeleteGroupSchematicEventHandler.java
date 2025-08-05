package dev.racel.listener.handler.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.message.SchemRemovedMessage;
import dev.racel.listener.handler.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class DeleteGroupSchematicEventHandler implements OrbitEventHandler<SchemRemovedMessage> {
    @Override
    public String getName() {
        return "deleteSchemShare";
    }

    @Override
    public void handle(Session session, SchemRemovedMessage data) {
        var groupOpt = DbConfig.getInstance().getGroupDAO().getGroupByName(data.getGroup());

        if(groupOpt.isEmpty()) {
            session.sendChat("Group " + data.getGroup() + " does not exist");
            return;
        }

        DbConfig.getInstance().getGroupDAO().removeGroupSchematicById(groupOpt.get().getId(), data.getSchematicID());
        session.sendGroupMessage("removedGroupSchematic", data);

        Logger.info("User {} removed schematic {} in group {}", session.getOrbitUser().get().getName(), data.getGroup(), data.getGroup());
    }
}
