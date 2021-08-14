package com.framework.utils;

import java.util.UUID;

public class UUIDUtils {

    private UUIDUtils(){}

    public static String newId(){
        return UUID.randomUUID().toString();
    }

}
