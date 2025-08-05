package dev.racel.handler.event.impl.group;

import dev.racel.config.DbConfig;
import dev.racel.entity.message.GroupMessage;
import dev.racel.entity.message.SelectedGroupMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

public class SetSelectedGroupEventHandler implements OrbitEventHandler<GroupMessage> {

    @Override
    public String getName() {
        return "setSelectedGroup";
    }

    @Override
    public void handle(Session session, GroupMessage data) {
        var user = session.getOrbitUser().get();
        var groupOpt = DbConfig.getInstance().getGroupDAO().getGroupByName(data.getGroupName());

        if(groupOpt.isEmpty()) {
            session.sendChat("Group doesn't exist.");
            return;
        }

        var group = groupOpt.get();
        var isUserInGroup = DbConfig.getInstance().getGroupDAO().isUserInGroup(group.getId(), user.getName());

        if (!isUserInGroup && !data.getPassword().equals(group.getPassword())) {
            session.sendChat("Group password is wrong.");
            return;
        }

        user.setSelected_group(data.getGroupName());
        DbConfig.getInstance().getUserDAO().updateUser(user);
        session.sendMessage("getSelectedGroup", new SelectedGroupMessage(data.getGroupName()));

        session.getClient().getAllRooms().forEach(room -> {
            session.getClient().leaveRoom(room);
        });

        session.getClient().joinRoom(data.getGroupName());

        Logger.info("User {} selected a new group {}", user.getName(), user.getSelected_group());
    }
}
