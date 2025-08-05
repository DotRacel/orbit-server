package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schematic {
    String schemId, schemName;
    int x, y, z;
    byte[] content;
}
