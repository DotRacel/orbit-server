package dev.racel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static boolean isValidJson(String json) {
        try {
            mapper.readTree(json);
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }
}
