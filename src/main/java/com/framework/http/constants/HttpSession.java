package com.framework.http.constants;

import lombok.Getter;

public enum HttpSession {

    SESSION_IDENTIFIER("JSESSIONID");

    @Getter
    private final String value;

    HttpSession(String value){
        this.value = value;
    }
}
