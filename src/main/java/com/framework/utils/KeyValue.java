package com.framework.utils;

import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class KeyValue {

    private final String key;
    private final String value;

    public static Map<String, String> toMap(String[] keyValueArray){
        Map<String, String> map = Maps.newHashMap();
        for ( String keyValue : keyValueArray){
            KeyValue kv = KeyValue.of(keyValue.split("="));
            map.put(kv.getKey(), kv.getValue());
        }
        return map;
    }

    public static KeyValue of(String[] keyValueArray){
        return KeyValue.builder()
                .key(keyValueArray[0])
                .value(keyValueArray[1])
                .build();
    }
}
