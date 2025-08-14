package dev.racel.handler.event.impl.group.waypoint;

import dev.racel.config.DbConfig;
import dev.racel.dao.GroupDAO;
import dev.racel.entity.message.GroupWaypointsMessage;
import dev.racel.entity.message.WaypointMessage;
import dev.racel.handler.event.OrbitEventHandler;
import dev.racel.session.Session;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

public class GetGroupWaypointsEventHandler implements OrbitEventHandler<GetGroupWaypointsEventHandler> {
    private final GroupDAO groupDAO = DbConfig.getInstance().getGroupDAO();

    @Override
    public String getName() {
        return "getGroupWaypoints";
    }

    @Override
    public void handle(Session session, GetGroupWaypointsEventHandler data) {
        var userName = session.getOrbitUser().getName();
        var groupNameOpt = groupDAO.getGroupByName(data.getName());

        if(groupNameOpt.isEmpty()){
            session.sendChat("Group " + data.getName() + " does not exist");
            return;
        }

        var waypointList = groupDAO.getGroupWaypoints(data.getName());
        Map<String, WaypointMessage> waypoints = new HashMap<>();
        waypointList.forEach(waypoint -> {
            waypoints.put(waypoint.getId(), new WaypointMessage(
                    data.getName(),
                    waypoint.getName(),
                    waypoint.getX(),
                    waypoint.getY(),
                    waypoint.getZ(),
                    waypoint.getServerIP()
            ));
        });

        session.sendMessage("groupWaypoints",
                new GroupWaypointsMessage(
                        data.getName(),
                        waypoints
                ));

        Logger.info("User {} fetched group {} waypoints",
                userName,
                data.getName());
    }
}
