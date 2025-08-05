package dev.racel.entity.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyMessage {
    @JsonProperty("orbitProductID")
    String orbitProductID;
}
