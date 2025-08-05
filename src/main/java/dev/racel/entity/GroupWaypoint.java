package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupWaypoint {
    double x, y, z;
    String name, serverIP;
}
