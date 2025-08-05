package dev.racel.entity.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchemShareMessage {
    String schemName;
    int x, y, z;
    List<Integer> schematic;
}
