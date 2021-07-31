package com.spring.http;

import com.google.common.collect.Maps;
import com.spring.utils.KeyValue;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
public class HttpHeader {

    @Getter
    private final Map<String, String> data;

    public static HttpHeader of(String[] headerKeyValueArr){
        HttpHeader httpHeader = new HttpHeader(Maps.newHashMap());
        for (String header : headerKeyValueArr){
            int separatorIdx= header.indexOf(": ");
            httpHeader.getData().put(header.substring(0, separatorIdx), header.substring(separatorIdx+1));
        }
        return httpHeader;
    }

    public static HttpHeader of(Map<String, String> data){
        return HttpHeader.builder().data(data).build();
    }

}
