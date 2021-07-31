package com.spring.http;

import com.google.common.collect.Maps;
import com.spring.utils.KeyValue;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
public class Cookies {

    @Getter
    private final Map<String, String> data;

    public static Cookies of(String[] cookieKeyValueArr){
        Map<String, String> data = Maps.newHashMap();
        for (String keyValue : cookieKeyValueArr ){
            KeyValue kv = KeyValue.of(keyValue.split("="));
            data.put(kv.getKey(), kv.getValue());
        }
        return Cookies.builder().data(data).build();
    }
}
