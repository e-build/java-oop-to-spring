package com.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;

public class JsonUtils {

    private JsonUtils(){}
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String serialize(Object obj) {
        try {
            return mapper.registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static <T> T deserialize(String jsonString, Class type) {
        try {
            return (T)mapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
