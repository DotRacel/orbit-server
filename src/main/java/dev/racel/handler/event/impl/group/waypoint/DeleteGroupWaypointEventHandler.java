package dev.racel.handler.event.impl.group.waypoint;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.message.DeleteGroupWaypointMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;

public class DeleteGroupWaypointEventHandler implements OrbitEventHandler<DeleteGroupWaypointMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "deleteGroupWaypoint";
    }

    @Override
    public void handle(Session session, DeleteGroupWaypointMessage data) {
        var user = session.getOrbitUser().get().getName();
        var groupOpt = groupDAO.getGroupByName(data.getGroup());

        if(groupOpt.isEmpty()) {
            session.sendChat("Group " +  data.getGroup() + " does not exist");
            return;
        }

        var group = groupOpt.get();

        if(!groupDAO.isUserInGroup(group.getId(), user)) {
            session.sendChat("You are not in the group!");
            return;
        }

        groupDAO.removeGroupWaypointById(data.getGroup(), data.getWaypointId());
        session.sendChat("Waypoint #" + data.getWaypointId() + " is deleted");
    }
}
