package com.gotway.gotway.common.util;


import java.util.Map;

public class MybatisQueryHelper {

    public static String getOrderByClause(Map<String, Object> mapByRequest) {
        String orderByClause="";
        if(mapByRequest.get("sort")!=null && !"".equals(mapByRequest.get("sort").toString())) {
            String[] split = mapByRequest.get("sort").toString().split(",");
            if(split!=null)for (String string : split) {
                if(string.contains("+")) {
                    orderByClause+= string.replace("+", " ")+" ASC,";
                }
                else if(string.contains("-")) {
                    orderByClause+= string.replace("-", " ")+" DESC,";
                }
            }
            orderByClause=orderByClause.substring(0, orderByClause.length()-1);//去除最后一个,号
        }
        return orderByClause;
    }
}
