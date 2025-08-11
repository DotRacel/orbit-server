package dev.racel.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchCrumbMessage {
    Number x, y, z;
    String name, direction, dirType;
}
