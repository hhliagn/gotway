package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.RoleMapper;
import com.gotway.gotway.mapper.RoleMapper;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.Role;
import com.gotway.gotway.pojo.RoleExample;
import com.gotway.gotway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class RoleExpression extends AbstractExpression {

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("Role".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("Role")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("Role")).append("(");
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
                    Role role = roleMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    title.append(role.getName());
                    sb.append(role.getName()).append(")进行了删除。");
                }
            }
            if("auth".equals(methodname)){
                if(param.get("roleId")!=null && !"".equals(param.get("roleId"))){
                    Role role = roleMapper.selectByPrimaryKey(Integer.valueOf(param.get("roleId").toString()));
                    title.append(role.getName());
                    sb.append(role.getName()).append(")进行了授权。");
                }
            }
            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }
}
