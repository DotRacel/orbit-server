package dev.racel.handler.event.impl.group.schematic;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.dao.SchemDAO;
import dev.racel.dao.UserDAO;
import dev.racel.entity.Schematic;
import dev.racel.entity.message.SchemAddedMessage;
import dev.racel.entity.message.SchemShareMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.tinylog.Logger;

public class UploadSchemShareEventHandler implements OrbitEventHandler<SchemShareMessage> {
    private final UserDAO userDAO = DbConfig.getInstance().getUserDAO();
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();
    private final SchemDAO schemDAO = DbConfig.getInstance().getSchemDAO();

    @Override
    public String getName() {
        return "uploadSchemShare";
    }

    @Override
    public void handle(Session session, SchemShareMessage data) {
        var user = session.getOrbitUser().get();
        var selectedGroupName = userDAO.getSelectedGroupByName(user.getName());

        if(selectedGroupName.isEmpty()) {
            session.sendChat("You are not in any group yet!");
            return;
        }

        var group = groupDAO.getGroupByName(selectedGroupName.get()).get();

        if (groupDAO.getGroupSchematicName(group.getId(), data.getSchemName()).isPresent()) {
            session.sendChat("Schematic with name " + data.getSchemName() + " already exists!");
            return;
        }

        var schemId = RandomStringUtils.insecure().nextAlphabetic(8);
        groupDAO.addGroupSchematic(group.getId(), schemId, data.getSchemName());

        Byte[] bytes = data.getSchematic().stream().map(Integer::byteValue).toArray(Byte[]::new);
        var schematic = new Schematic(
                schemId,
                data.getSchemName(),
                data.getX(),
                data.getY(),
                data.getZ(),
                ArrayUtils.toPrimitive(bytes)
        );
        schemDAO.addSchematic(schematic);
        session.sendGroupChat(user.getName() + " just shared a schematic");
        session.sendGroupMessage("addedGroupSchematic", new SchemAddedMessage(
                data.getSchemName(),
                schemId,
                group.getGroupName()
        ));

        Logger.info("User {} shared a schematic {} in group {}",
                user.getName(), data.getSchemName(), group.getGroupName());
    }
}
