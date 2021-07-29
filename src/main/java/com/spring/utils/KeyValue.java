package com.spring.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class KeyValue {

    private final String key;
    private final String value;

    public static KeyValue of(String[] keyValueArray){
        return KeyValue.builder()
                .key(keyValueArray[0])
                .value(keyValueArray[1])
                .build();
    }
}
