package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.ThemeMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class UserExpression extends AbstractExpression {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("User".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("User")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("User")).append("(");
            if("addOrUpdate".equals(methodname)){
                title.append(param.get("nickname"));
                sb.append(param.get("account")==null?" ":param.get("account")).append("-").append(param.get("nickname")).append(")进行了");
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    sb.append("修改。");
                }
                else sb.append("新增。");
            }
            if("del".equals(methodname)){
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    User user2 = userMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    String str = user2.getAccount() == null ? " " : user2.getAccount();
                    title.append(str).append("-").append(user2.getNickname());
                    sb.append(str).append("-").append(user2.getNickname()).append(")进行了删除。");
                }
            }
            if("updateState".equals(methodname)){
                if(param.get("ids")!=null && !"".equals(param.get("ids"))){
                    List<Integer> idList = new ArrayList<>();
                    String[] ids = param.get("ids").toString().split(",");
                    List<String> strings = Arrays.asList(ids);
                    strings.forEach(s->{
                        Integer i = null;
                        try { i = Integer.valueOf(s);} catch (NumberFormatException e) {}
                        if(i!=null)idList.add(i);
                    });
                    if(idList.size()>0){
                        UserExample example = new UserExample();
                        example.createCriteria().andIdIn(idList);
                        List<User> users = userMapper.selectByExample(example);
                        for (User t:users) {
                            String str = t.getAccount() == null ? " " : t.getAccount();
                            title.append(str).append("-").append(t.getNickname()).append("，");
                            sb.append(str).append("-").append(t.getNickname()).append("，");
                        }
                    }
                    if("，".equals(sb.substring(sb.length()-1)))sb.deleteCharAt(sb.length()-1);
                    if(param.get("state")!=null && "1".equals(param.get("state").toString())){
                        sb.append(")进行了启用。");
                    }else {
                        sb.append(")进行了停用。");
                    }

                }
            }
            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }
}
