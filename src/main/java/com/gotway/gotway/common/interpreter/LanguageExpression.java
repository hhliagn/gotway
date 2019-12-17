package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.LanguagePackMapper;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.LanguagePack;
import com.gotway.gotway.pojo.LanguagePackExample;
import com.gotway.gotway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class LanguageExpression extends AbstractExpression {

    @Autowired
    private LanguagePackMapper languagePackMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("Language".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("Language")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("Language")).append("(");
            if("addOrUpdate".equals(methodname)){
                title.append(param.get("name"));
                sb.append(param.get("name")).append(")进行了");
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    sb.append("修改。");
                }
                else sb.append("新增。");
            }
            if("del".equals(methodname)){
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    LanguagePack languagePack = languagePackMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    title.append(languagePack.getName());
                    sb.append(languagePack.getName()).append(")进行了删除操作。");
                }
            }
            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }
}
