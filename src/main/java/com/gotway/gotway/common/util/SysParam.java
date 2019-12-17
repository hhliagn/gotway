package com.gotway.gotway.common.util;

import java.util.HashMap;
import java.util.Map;

public class SysParam {
    public static Map<String,String> param = new HashMap<>();

    public static String get(String key){
        return param.get(key);
    }
    public static String set(String key,String value){
        return param.put(key,value);
    }
}
