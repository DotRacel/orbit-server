package dev.racel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerVersion {
    String name;
    String version;
}
