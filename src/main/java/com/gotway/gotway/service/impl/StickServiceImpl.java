package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.CommentsLogMapper;
import com.gotway.gotway.mapper.PraiseLogMapper;
import com.gotway.gotway.mapper.ReportLogMapper;
import com.gotway.gotway.mapper.StickMapper;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.pojo.vo.StickVo;
import com.gotway.gotway.pojo.vo.StickVoForPraise;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.FileLogService;
import com.gotway.gotway.service.StickService;
import com.gotway.gotway.service.TokenService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StickServiceImpl extends BaseServiceImpl<Stick> implements StickService {
    @Autowired
    private StickMapper stickMapper;
    @Autowired
    private FileLogService fileLogService;
    @Autowired
    private FieldService fieldServcie;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CommentsLogMapper commentsLogMapper;
    @Autowired
    private ReportLogMapper reportLogMapper;
    @Autowired
    private PraiseLogMapper praiseLogMapper;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "content", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "state", strVals = {"1","2"}),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
        @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
         @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        Stick stick = Map2Bean.getInstance().getBean(Stick.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        StickExample example = new StickExample();
        StickExample.Criteria c = example.createCriteria();
        if(stick.getContent()!=null)c.andContentLike("%"+stick.getContent()+"%");
        if(stick.getState()!=null)c.andStateEqualTo(stick.getState());
        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Stick> sticks = stickMapper.selectByExample(example);
        Long total = page.getTotal();

        if(sticks!=null && sticks.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",sticks,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",sticks,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "content", minLen = 1,maxLen=6400),
        @ValidateField(index = 0, notNull = true, key = "themeId", minLen = 1,maxLen=255),
        @ValidateField(index = 0, notNull = false, key = "imgs", minLen = 1),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Stick stick = Map2Bean.getInstance().getBean(Stick.class, mapByRequest, null);

        if(stick.getId()==null){
            stick.setAdmire(0);
            stick.setComments(0);
            stick.setReport(0);
            User user = tokenService.getUserInfo(mapByRequest);
            if(user!=null)stick.setUserId(user.getId());
            stick.setCreateTime(new Date());
            stick.setState(1);//状态[1:正常2:屏蔽]
            int insert = this.insert(stick);
            if(insert>0){
                fileLogService.saveImg(mapByRequest, stick.getId(),1);//保存图片 //type 帖子 :1
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",stick,null);
            }
        }else{
            int i = this.updateByPk(stick);
            fileLogService.saveImg(mapByRequest, stick.getId(),1);//保存图片 //type 帖子 :1
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",stick,null);
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
     }


    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        int i = this.deleteByPk(id);
        if(i>0){
            //删除评论
            CommentsLogExample commentsLogExample = new CommentsLogExample();
            commentsLogExample.createCriteria().andStickIdEqualTo(id);
            commentsLogMapper.deleteByExample(commentsLogExample);
            //删除点赞
            PraiseLogExample praiseLogExample = new PraiseLogExample();
            praiseLogExample.createCriteria().andStickIdEqualTo(id);
            praiseLogMapper.deleteByExample(praiseLogExample);
            //删除举报
            ReportLogExample reportLogExample = new ReportLogExample();
            reportLogExample.createCriteria().andStickIdEqualTo(id);
            reportLogMapper.deleteByExample(reportLogExample);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel getItem(Map<String, Object> mapByRequest) throws Exception {
        Stick stick = this.selectByPk(Integer.valueOf(mapByRequest.get("id").toString()));
        if(stick!=null)
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"select success",stick,null);
        return new ReturnModel(ReturnModel.CODE_FAILD,"select faild");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "ids", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "state", strVals = {"1","2"}),
    })
    public ReturnModel updateState(Map<String, Object> mapByRequest) throws Exception {
        String[] ids = mapByRequest.get("ids").toString().split(",");
        if(ids!=null && ids.length>0 &&(mapByRequest.get("state")!=null || mapByRequest.get("tag")!=null)){
            for (String id:ids) {
                Stick stick = new Stick();
                stick.setId(Integer.valueOf(id));
                if(mapByRequest.get("state")!=null)stick.setState(Integer.valueOf(mapByRequest.get("state").toString()));
                stickMapper.updateByPrimaryKeySelective(stick);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getListForComment(Map<String, Object> mapByRequest) throws Exception{
        User userInfo = tokenService.getUserInfo(mapByRequest);
        //查出我的帖子，以及我评论过的帖子
        Stick stick = Map2Bean.getInstance().getBean(Stick.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        StickExample example = new StickExample();
        StickExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userInfo.getId());
        c.andStateEqualTo(1);//屏蔽了的，不显示

        List<Integer> stickIds = new ArrayList<>();
        CommentsLogExample commentsExample = new CommentsLogExample();
        commentsExample.createCriteria().andUserIdEqualTo(userInfo.getId()).andStateEqualTo(1);//状态[1:正常2:屏蔽3删除]
        List<CommentsLog> commentsLogs = commentsLogMapper.selectByExample(commentsExample);
        commentsLogs.forEach(ct -> stickIds.add(ct.getStickId()));

        if(stickIds.size()>0)example.or().andIdIn(stickIds).andStateEqualTo(1);//屏蔽了的，不显示

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);
        else example.setOrderByClause("t.create_time desc");

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Stick> sticks = stickMapper.selectByExample(example);
        Long total = page.getTotal();

        if(sticks!=null && sticks.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",sticks,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",sticks,total.intValue());
    }

    @Override
    public ReturnModel getListForPraise(Map<String, Object> mapByRequest)throws Exception {
        User userInfo = tokenService.getUserInfo(mapByRequest);
        //查出我的帖子，以及我点赞过的帖子
        Stick stick = Map2Bean.getInstance().getBean(Stick.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        StickExample example = new StickExample();
        StickExample.Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userInfo.getId());
        c.andStateEqualTo(1);//屏蔽了的，不显示

        List<Integer> stickIds = new ArrayList<>();
        PraiseLogExample praiseLogExample = new PraiseLogExample();
        praiseLogExample.createCriteria().andUserIdEqualTo(userInfo.getId()).andStateEqualTo(0);//state=0表示点赞有效 1表示取消点赞
        List<PraiseLog> praiseLogs = praiseLogMapper.selectByExample(praiseLogExample);
        praiseLogs.forEach(ct -> stickIds.add(ct.getStickId()));

        if(stickIds.size()>0)example.or().andIdIn(stickIds).andStateEqualTo(1);//屏蔽了的，不显示

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);
        else example.setOrderByClause("t.create_time desc");
        example.setDistinct(true);//去除重复

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Stick> sticks = stickMapper.selectByExampleForPraise(example);
        //我点赞的帖子，加标记 isMyPraise = true
        for(Stick s :sticks){
            for (Integer id:stickIds) {
                if (s.getId().equals(id)){
                    StickVoForPraise a  = (StickVoForPraise)s;
                    a.setMyPraise(true);
                }
            }
        }

        Long total = page.getTotal();

        if(sticks!=null && sticks.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",sticks,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",sticks,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "content", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "stickerUserId", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "themeId", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "state", strVals = {"1","2"}),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),

    })
    public ReturnModel getListForApp(Map<String, Object> mapByRequest) throws Exception{
        if(mapByRequest.get("token")!=null){
            User userInfo = tokenService.getUserInfo(mapByRequest);
            if(userInfo!=null)mapByRequest.put("userId",userInfo.getId());
        }
        Stick stick = Map2Bean.getInstance().getBean(Stick.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);

        if(stick.getContent()!=null)mapByRequest.put("content","%"+stick.getContent()+"%");
        if(stick.getThemeId()!=null)mapByRequest.put("themeId",stick.getThemeId());
        mapByRequest.put("state",1);
        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))mapByRequest.put("sort",orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<StickVo> sticks = stickMapper.getListForApp(mapByRequest);
        Long total = page.getTotal();

        if(sticks!=null && sticks.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",sticks,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",sticks,total.intValue());
    }
}
