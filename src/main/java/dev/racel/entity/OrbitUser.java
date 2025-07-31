package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrbitUser {
    String name, purchaseId, hwid, last_ign, last_uuid, last_version, selected_group;
}
