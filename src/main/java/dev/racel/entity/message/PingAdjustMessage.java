package dev.racel.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PingAdjustMessage {
    Number y;
    String name;
    Number pos;
    String direction;
}
