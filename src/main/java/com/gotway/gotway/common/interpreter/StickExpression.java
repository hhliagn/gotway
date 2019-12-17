package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.StickMapper;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.Stick;
import com.gotway.gotway.pojo.StickExample;
import com.gotway.gotway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class StickExpression extends AbstractExpression {

    @Autowired
    private StickMapper stickMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("Stick".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("Stick")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("Stick")).append("(");
            if("addOrUpdate".equals(methodname)){
                String content = param.get("content")!=null ?param.get("content").toString():"";
                content = content.length()<=10?content:content.substring(0,10)+"...";
                title.append(content);
                sb.append(content).append(")进行了");
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    sb.append("修改。");
                }
                else sb.append("新增。");
            }
            if("del".equals(methodname)){
                if(param.get("id")!=null && !"".equals(param.get("id"))){
                    Stick stick = stickMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    String content = stick.getContent();
                    title.append(content.length()<=10?content:content.substring(0,10)).append("...");//取前10个字符
                    sb.append(content.length()<=10?content:content.substring(0,10)).append("...)进行了删除。");
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
                        StickExample example = new StickExample();
                        example.createCriteria().andIdIn(idList);
                        List<Stick> sticks = stickMapper.selectByExample(example);
                        for (Stick t:sticks) {
                            String content = t.getContent();
                            title.append(content.length()<=10?content:content.substring(0,10)).append("...").append("，");
                            sb.append(content.length()<=10?content:content.substring(0,10)).append("...").append("，");
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
