package dev.racel.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrbitUser {
    String name, purchaseId, hwid;
    @JsonProperty("last_ign")
    String lastIgn;
    @JsonProperty("last_uuid")
    String lastUUID;
    @JsonProperty("last_version")
    String lastVersion;
    @JsonProperty("selected_group")
    String selectedGroup;
}
