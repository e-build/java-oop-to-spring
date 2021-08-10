package com.framework.http.constants;

import lombok.Getter;

public enum HttpHeader {

    CONTENT_TYPE("Content-Type"),
    ACCEPT("Accept");

    @Getter
    private final String value;

    HttpHeader(String value){
        this.value = value;
    }


}
