package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.ThemeMapper;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.Theme;
import com.gotway.gotway.pojo.ThemeExample;
import com.gotway.gotway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Component
public class ThemeExpression extends AbstractExpression {

    @Autowired
    private ThemeMapper themeMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("Theme".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("Theme")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("Theme")).append("(");
            if("addOrUpdate".equals(methodname)){
                title.append(param.get("theme"));
                sb.append(param.get("theme")).append(")进行了");
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    sb.append("修改。");
                }
                else sb.append("新增。");
            }
            if("del".equals(methodname)){
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    Theme theme = themeMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    title.append(theme.getTheme());
                    sb.append(theme.getTheme()).append(")进行了删除。");
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
                        ThemeExample example = new ThemeExample();
                        example.createCriteria().andIdIn(idList);
                        List<Theme> themes = themeMapper.selectByExample(example);
                        for (Theme t:themes) {
                            title.append(t.getTheme()).append("，");
                            sb.append(t.getTheme()).append("，");
                        }
                    }
                    if("，".equals(sb.substring(sb.length()-1)))sb.deleteCharAt(sb.length()-1);
                    if(param.get("state")!=null && "1".equals(param.get("state").toString())){
                        sb.append(")进行了取消屏蔽。");
                    }else {
                        sb.append(")进行了屏蔽。");
                    }

                }
            }
            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }
}
