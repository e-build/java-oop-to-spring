package com.framework.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private JsonUtils(){}
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deserialize(String jsonString, Class type) {
        try {
            return mapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
