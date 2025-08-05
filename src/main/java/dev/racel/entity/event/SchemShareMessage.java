package dev.racel.entity.event;

import com.fasterxml.jackson.databind.node.ObjectNode;
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
