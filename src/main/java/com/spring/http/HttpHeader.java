package com.spring.http;

import com.google.common.collect.Maps;
import com.spring.utils.KeyValue;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class HttpHeader {

    @Getter
    private final Map<String, String> data;

    public HttpHeader(String[] headerKeyValueStringArr) {
        this.data = Maps.newHashMap();
        for ( String headerKeyValueString : headerKeyValueStringArr ) {
            int separatorIdx = getSeparatorIndex(headerKeyValueString);
            this.data.put(
                    StringUtils.trim( headerKeyValueString.substring(0, separatorIdx) ),
                    StringUtils.trim( headerKeyValueString.substring(separatorIdx + 1) )
            );
        }
    }

    public HttpHeader(Map<String, String> data) {
        this.data = data;
    }

    private int getSeparatorIndex(String notParsedKeyValue) {
        return notParsedKeyValue.indexOf(": ");
    }

}
