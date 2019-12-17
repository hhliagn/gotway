package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.MessageMapper;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.Message;
import com.gotway.gotway.pojo.MessageExample;
import com.gotway.gotway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class MessageExpression extends AbstractExpression {

    @Autowired
    private MessageMapper messageMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("Message".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("Message")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("Message")).append("(");
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
                    Message message = messageMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    String content = message.getTheme();
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
                        MessageExample example = new MessageExample();
                        example.createCriteria().andIdIn(idList);
                        List<Message> messages = messageMapper.selectByExample(example);
                        for (Message t:messages) {
                            String content = t.getTheme();
                            title.append(content.length()<=10?content:content.substring(0,10)).append("...").append("，");
                            sb.append(content.length()<=10?content:content.substring(0,10)).append("...").append("，");
                        }
                    }
                    if("，".equals(sb.substring(sb.length()-1)))sb.deleteCharAt(sb.length()-1);
                    if(param.get("state")!=null && "1".equals(param.get("state").toString())){
                        sb.append(")进行了取消标记。");
                    }else {
                        sb.append(")进行了标记。");
                    }
                    String replace = sb.toString().replace("消息/", "");
                    sb.delete(0,sb.length());
                    sb.append(replace);
                }
            }
            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }
}
