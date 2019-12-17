package com.gotway.gotway.common.interpreter;

import com.gotway.gotway.mapper.CommentsLogMapper;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.CommentsLog;
import com.gotway.gotway.pojo.CommentsLogExample;
import com.gotway.gotway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.security.auth.Subject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Component
public class CommentsLogExpression extends AbstractExpression {

    @Autowired
    private CommentsLogMapper commentsLogMapper;
    @Override
    public void subInterpreter(User user, Map<String,Object> param, SysLog sysLog,String methodname) {
        if("CommentsLog".equals(sysLog.getObjectTag())){
            StringBuilder title = new StringBuilder();
            title.append(translateMap.get("CommentsLog")).append("：");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNickname()).append("，对").append(translateMap.get("CommentsLog")).append("(");
            if("addOrUpdate".equals(methodname)){
                String content = param.get("comments")!=null ?param.get("comments").toString():"";
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
                    CommentsLog commentsLog = commentsLogMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
                    String comments = commentsLog.getComments();
                    title.append(comments.length()<=10?comments:comments.substring(0,10)).append("...");
                    sb.append(comments.length()<=10?comments:comments.substring(0,10)).append("...)进行了删除操作。");
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
                        CommentsLogExample example = new CommentsLogExample();
                        example.createCriteria().andIdIn(idList);
                        List<CommentsLog> commentsLogs = commentsLogMapper.selectByExample(example);
                        for (CommentsLog t:commentsLogs) {
                            String comments = t.getComments();
                            title.append(comments.length()<=10?comments:comments.substring(0,10)).append("...，");
                            sb.append(comments.length()<=10?comments:comments.substring(0,10)).append("...，");
                        }
                    }
                    if("，".equals(sb.substring(sb.length()-1)))sb.deleteCharAt(sb.length()-1);
                    if(param.get("state")!=null && "1".equals(param.get("state").toString())){
                        sb.append(")进行了取消屏蔽操作。");
                    }else {
                        sb.append(")进行了屏蔽操作。");
                    }

                }
            }
            sysLog.setTheme(title.toString());
            sysLog.setOperation(sb.toString());
        }
    }

}
