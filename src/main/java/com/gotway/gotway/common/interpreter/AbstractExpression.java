package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.User;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExpression {
    protected Map<String,String> translateMap = new HashMap<>();

    public void interpreter(User user, Map<String,Object> param,SysLog sysLog,String methodname){
        if(translateMap.size()==0){
            String translate_sys_log_cn = SysParam.get("translate_sys_log_cn");
            this.translateMap = JsonUtils.jsonToPojo(translate_sys_log_cn, Map.class);
        }
        if(sysLog!=null){
            subInterpreter(user,param,sysLog,methodname);
        }
    }

    protected abstract void subInterpreter(User user,Map<String,Object> param,SysLog sysLog,String methodname);

}
