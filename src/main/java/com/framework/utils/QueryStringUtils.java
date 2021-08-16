package com.framework.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryStringUtils {

    private QueryStringUtils() {}

    public static Map<String, String> toMap(String queryString){
        Map<String, String> returnMap = Maps.newHashMap();
        for (String keyValue : queryString.split("&")){
            KeyValue kv = KeyValue.of(keyValue.split("="));
            returnMap.put(kv.getKey(), kv.getValue());
        }
        return returnMap;
    }

    public static List<String> toList(String queryString){
        List<String> returnList = Lists.newArrayList();
        for (String keyValue : queryString.split("&")){
            KeyValue kv = KeyValue.of(keyValue.split("="));
            returnList.add(kv.getValue());
        }
        return returnList;
    }

    public static String toString(Map<String, String> queryStringMap){
        List<String> builder = new ArrayList<String>();
        for (String key : queryStringMap.keySet())
            builder.add( key + "=" + queryStringMap.get(key) );
        return String.join("&", builder);
    }
}
