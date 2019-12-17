package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JPushUtil;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.mapper.MessageMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.mapper.UserMessageMapper;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.FileLogService;
import com.gotway.gotway.service.MessageService;
import com.gotway.gotway.service.TokenService;
import com.zf.ann.ValidateField;

import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional()
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMessageMapper userMessagemapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FieldService fieldServcie;
    @Autowired
    private FileLogService fileLogService;
    @Autowired
    private TokenService tokenService;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Value("${MESSAGE_TYPES}")
    private String MESSAGE_TYPES;
    @Value("${FEEDBACK_TYPES}")
    private String FEEDBACK_TYPES;
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "theme", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "content", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "nickname", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "types", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
        @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
        @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
        @ValidateField(index = 0, notNull = false, key = "messageFeedback", strVals = {"feedback","message"}),
    })
    public ReturnModel _getList(Map<String, Object> mapByRequest) throws Exception{
        Message message = Map2Bean.getInstance().getBean(Message.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        MessageExample example = new MessageExample();
        MessageExample.Criteria c = example.createCriteria();
        if(message.getTheme()!=null)c.andThemeLike("%"+message.getTheme()+"%");
        if(message.getContent()!=null)c.andContentLike("%"+message.getContent()+"%");
        if(!StringUtils.isEmpty(mapByRequest.get("nickname"))){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andNicknameLike("%"+mapByRequest.get("nickname").toString()+"%");
            List<User> users = userMapper.selectByExample(userExample);
            if(users!=null && users.size()>0){
                List<Integer> ids = new ArrayList<>();
                users.stream().forEach( e ->ids.add(e.getId()));
                c.andUserIdIn(ids);
            }else c.andUserIdEqualTo(-1);//nickname匹配不到，让它查出的数据为空
        }
        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));
        if(!StringUtils.isEmpty(mapByRequest.get("types"))){
            String[] types = mapByRequest.get("types").toString().split(",");
            List<Integer> tps = new ArrayList<>();
            for (String type:types) {
                if("ALL".equals(type)){
                    this.typesHandle(mapByRequest, c);
                    tps=null;
                    break;
                }else {
                    tps.add(Integer.valueOf(type));
                }
            }
            if(tps!=null&& tps.size()>0)c.andTypeIn(tps);
        }else{
            this.typesHandle(mapByRequest, c);
        }

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Message> messages = messageMapper.selectByExample(example);
        Long total = page.getTotal();
        //查出来的数据标为已读
        tagRead(mapByRequest, messages);

        if(messages!=null && messages.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",messages,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",messages,total.intValue());
    }

    private void tagRead(Map<String, Object> mapByRequest, List<Message> messages) {
        Date date = new Date();
        if(mapByRequest.get("token")!=null){
            User userInfo = tokenService.getUserInfo(mapByRequest);
            UserMessageExample example2 = new UserMessageExample();
            for (Message m:messages) {
                example2.clear();
                example2.createCriteria().andMessageIdEqualTo(m.getId()).andUserIdEqualTo(userInfo.getId());
                List<UserMessage> userMessages = userMessagemapper.selectByExample(example2);
                if(userMessages==null || userMessages.size()==0){
                    UserMessage um = new UserMessage();
                    um.setCreateTime(date);
                    um.setMessageId(m.getId());
                    um.setUserId(userInfo.getId());
                    userMessagemapper.insert(um);
                }

            }
        }
    }

    private void typesHandle(Map<String, Object> mapByRequest, MessageExample.Criteria c) {
        List<Integer> tps = new ArrayList<>();
        if("message".equals(mapByRequest.get("messageFeedback"))){
            MESSAGE_TYPES = SysParam.get("message_types")!=null ? SysParam.get("message_types"):MESSAGE_TYPES;
            Arrays.asList(MESSAGE_TYPES.split(",")).stream().forEach(s->tps.add(Integer.valueOf(s)));
        }else{
            FEEDBACK_TYPES = SysParam.get("feedback_types")!=null ? SysParam.get("feedback_types"):MESSAGE_TYPES;
            Arrays.asList(FEEDBACK_TYPES.split(",")).stream().forEach(s->tps.add(Integer.valueOf(s)));
        }
        if(tps!=null&& tps.size()>0)c.andTypeIn(tps);
    }

    @Override
    @ValidateField(index = 0, notNull = true)
    @ValidateField(index = 0, notNull = false, key = "id", minVal = 0)
    @ValidateField(index = 0, notNull = false, key = "theme", minLen = 1,maxLen=64)
    @ValidateField(index = 0, notNull = false, key = "imgs", minLen = 1)
    @ValidateField(index = 0, notNull = true, key = "content", minLen = 1,maxLen=2000)
    @ValidateField(index = 0, notNull = true, key = "type", minVal = 0)//[0:系统消息 1:其它 2:车辆设置 3:排行榜 4:服务相关 5:闪退 6:个人信息设置 7:粉圈]
    @ValidateField(index = 0, notNull = true, key = "state", strVals = {"1","2"})//消息列表时——推送状态[1:否2:是] 问题反馈时——状态[1:未标记2:标记]
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Message message = Map2Bean.getInstance().getBean(Message.class, mapByRequest, null);

        if(message.getId()==null){
            message.setCreateTime(new Date());
            User userInfo = tokenService.getUserInfo(mapByRequest);
            message.setUserId(userInfo!=null?userInfo.getId():null);
            int insert = this.insert(message); //int in = 1/0;//事务测试
            if(insert>0){
                if(message.getState()!=null && message.getState()==2)this.push(message.getContent());
                fileLogService.saveImg(mapByRequest, message.getId(),3);//保存图片 //type帖子 :1,用户头像:0 ,主题 ：2 ，反馈消息：3
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",message,null);
            }
        }else{
            int i = this.updateByPk(message);
            if(i>0){
                if(message.getState()!=null && message.getState()==2)this.push(message.getContent());
                fileLogService.saveImg(mapByRequest, message.getId(),3);//保存图片 //type帖子 :1,用户头像:0 ,主题 ：2 ，反馈消息：3
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",message,null);
            }
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
    }

    private void push(String message){
        String masterSecret= SysParam.get("masterSecret")==null ? "c07288094bd88731f7aef8bd" :SysParam.get("masterSecret") ;
        String appKey = SysParam.get("appKey")==null ? "b75e0c3575cbaa22f43999b3" : SysParam.get("appKey");
        String tag = SysParam.get("tag")==null ? "gotway" : SysParam.get("tag");
        JPushUtil ju = JPushUtil.getInstence(masterSecret, appKey);
        ju.publish(tag,message,"Gotway");

        //对ios的处理
        masterSecret= "c07288094bd88731f7aef8bd" ;
        appKey = "b75e0c3575cbaa22f43999b3";
        ju = new  JPushUtil(masterSecret, appKey);
        ju.publish(tag,message,"Gotway");
        ju = null;

    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        int i = this.deleteByPk(id);
        if(i>0){
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "ids", minLen = 1),
            @ValidateField(index = 0, notNull = true, key = "state", strVals = {"1","2"}),
    })
    public ReturnModel updateState(Map<String, Object> mapByRequest) throws Exception {
        String[] ids = mapByRequest.get("ids").toString().split(",");
        if(ids!=null && ids.length>0 &&(mapByRequest.get("state")!=null || mapByRequest.get("tag")!=null)){
            for (String id:ids) {
                Message message = new Message();
                message.setId(Integer.valueOf(id));
                if(mapByRequest.get("state")!=null)message.setState(Integer.valueOf(mapByRequest.get("state").toString()));
                messageMapper.updateByPrimaryKeySelective(message);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }

    @Override
    public ReturnModel unreadNum(Map<String, Object> mapByRequest) throws Exception {
        User userInfo = tokenService.getUserInfo(mapByRequest);
        int num =  messageMapper.selectUnreadNum(userInfo.getId());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"get success",num,null);
    }


}
