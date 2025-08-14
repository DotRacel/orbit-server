package dev.racel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.racel.config.WsConfig;
import org.tinylog.Logger;

public class JsonUtil {
    public static String getJsonFromObject(Object obj) {
        try {
            return WsConfig.mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Logger.error(e, "Failed to deserialize object");
        }
        return null;
    }
}
