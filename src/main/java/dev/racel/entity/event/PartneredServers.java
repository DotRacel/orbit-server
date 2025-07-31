package dev.racel.entity.event;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PartneredServers {
    List<Server> partneredServers;

    @JsonValue
    List<Server> toJson() {
        return this.partneredServers;
    }


    @AllArgsConstructor
    @Data
    public static class Server {
        String serverName, serverIP;
    }
}