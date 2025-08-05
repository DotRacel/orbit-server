package dev.racel.entity.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorMessage {
    String error;

    public ErrorMessage(String error) {
        this.error = "&7(&9OrbitClient&7) &c" + error;
    }
}
