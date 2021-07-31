package com.framework.http;

import com.google.common.collect.Maps;
import lombok.Getter;
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

    public void addHeader(String key, String value){
        this.data.put(key, value);
    }

    private int getSeparatorIndex(String notParsedKeyValue) {
        return notParsedKeyValue.indexOf(": ");
    }


}
