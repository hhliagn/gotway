package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.PropertiesMapper;
import com.gotway.gotway.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class PropertiesExpression extends AbstractExpression {

    @Autowired
    private PropertiesMapper propertiesMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("Properties".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("Properties"));
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("Properties"));

            if("updateForXL".equals(methodname)){
                sb.append("进行了修改操作。");
            }
            if("addOrUpdate".equals(methodname)){
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    sb.append("(").append(param.get("key")).append(")");
                    sb.append("进行了修改操作。");
                }else{
                    sb.append("(").append(param.get("key")).append(")");
                    sb.append("进行了新增操作。");
                }
                title.append(":").append(param.get("key"));
            }

            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }
}
