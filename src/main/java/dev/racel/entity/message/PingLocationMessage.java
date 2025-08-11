package dev.racel.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PingLocationMessage {
    double x, y, z;
    String name;
    int pots;
    String armordata, direction;
}
