package dev.racel.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PingBlockMessage {
    Number x, y, z;
    Number blockid;
    String name;
}
