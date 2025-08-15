package dev.racel.handler.event.impl.group.waypoint;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.GroupWaypoint;
import dev.racel.entity.message.GetGroupWaypointsMessage;
import dev.racel.entity.message.WaypointMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.tinylog.Logger;

public class UploadGroupWaypointEventHandler implements OrbitEventHandler<WaypointMessage> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "uploadGroupWaypoint";
    }

    @Override
    public void handle(Session session, WaypointMessage data) {
        var groupNameOpt = groupDAO.getGroupByName(data.getGroup());

        if(groupNameOpt.isEmpty()){
            session.sendChat("Group " + data.getGroup() + " does not exist");
            return;
        }

        var waypointId = RandomStringUtils.insecure().nextAlphabetic(8);
        var waypoint = new GroupWaypoint(
                data.getGroup(),
                data.getName(),
                waypointId,
                data.getX(),
                data.getY(),
                data.getZ(),
                data.getServerIP()
        );

        groupDAO.addGroupWaypoint(waypoint);
        session.sendChat("Waypoint #" + waypointId + " has been uploaded");
        Logger.info("User {} uploaded group waypoint #{}",
                session.getOrbitUser().getName(), waypointId);

        // Update users' group waypoints list
        new GetGroupWaypointsEventHandler().handle(session,
                new GetGroupWaypointsMessage(data.getGroup()));
    }
}
