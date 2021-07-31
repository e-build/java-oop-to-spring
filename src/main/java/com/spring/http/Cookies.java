package com.spring.http;

import com.google.common.collect.Maps;
import com.spring.utils.KeyValue;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

public class Cookies {

    @Getter
    private final Map<String, String> data;

    public Cookies(String[] cookieKeyValueArr){
        this.data = Maps.newHashMap();
        for (String keyValue : cookieKeyValueArr ){
            int index = getSeparatorIndex(keyValue);
            this.data.put(keyValue.substring(0, index), keyValue.substring(index + 1));
        }
    }

    private int getSeparatorIndex(String notParsedKeyValue) {
        return notParsedKeyValue.indexOf("=");
    }
}
