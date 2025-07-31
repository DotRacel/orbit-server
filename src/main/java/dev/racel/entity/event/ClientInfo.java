package dev.racel.entity.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInfo {
    @JsonProperty("UUID")
    String UUID;
    @JsonProperty("IGN")
    String inGameName;
    @JsonProperty("PURCHASE_ID")
    String purchaseID;
    @JsonProperty("HWID")
    String hwid;
    @JsonProperty("VERSION")
    String version;
}
