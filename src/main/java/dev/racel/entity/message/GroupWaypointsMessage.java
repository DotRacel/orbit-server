package dev.racel.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupWaypointsMessage {
    String group;
    Map<String, WaypointMessage> waypoints;
}
