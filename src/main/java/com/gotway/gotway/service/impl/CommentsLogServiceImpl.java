package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.CommentsLogMapper;
import com.gotway.gotway.mapper.StickMapper;
import com.gotway.gotway.pojo.CommentsLog;
import com.gotway.gotway.pojo.CommentsLogExample;
import com.gotway.gotway.pojo.Stick;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.CommentsLogService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommentsLogServiceImpl extends BaseServiceImpl<CommentsLog> implements CommentsLogService {
    @Autowired
    private CommentsLogMapper commentsLogMapper;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private StickMapper stickMapper;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "comments", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "state",strVals = {"1","2"}),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
            @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
            @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        CommentsLog commentsLog = Map2Bean.getInstance().getBean(CommentsLog.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        CommentsLogExample example = new CommentsLogExample();
        CommentsLogExample.Criteria c = example.createCriteria();
        if(commentsLog.getComments()!=null)c.andCommentsLike("%"+commentsLog.getComments()+"%");
        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));
        if(commentsLog.getState()!=null)c.andStateEqualTo(commentsLog.getState());
        c.andStateNotEqualTo(3);//删除了的评论，不查出来
        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<CommentsLog> commentsLogs = commentsLogMapper.selectByExample(example);
        Long total = page.getTotal();

        if(commentsLogs!=null && commentsLogs.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",commentsLogs,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",commentsLogs,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "comments", minLen = 1,maxLen=2000),
        @ValidateField(index = 0, notNull = true, key = "stickId", minLen = 1,maxLen=255),
        @ValidateField(index = 0, notNull = true, key = "objectType", minVal=-1),//对象类型[1:对帖评论 2:回复评论]
        @ValidateField(index = 0, notNull = false, key = "objectId", minVal=0),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        CommentsLog commentsLog = Map2Bean.getInstance().getBean(CommentsLog.class, mapByRequest, null);

        if(commentsLog.getId()==null){
            commentsLog.setState(1);//状态[1:正常2:屏蔽]
            commentsLog.setCreateTime(new Date());
            User userInfo = tokenService.getUserInfo(mapByRequest);
            commentsLog.setUserId(userInfo.getId());

            Stick stick = stickMapper.selectByPrimaryKey(commentsLog.getStickId());
            if(stick==null) return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,stick do not exist");

            if(commentsLog.getObjectType()>0){//如果是回复，objectId保评论评id
                CommentsLog commentsLog1 = commentsLogMapper.selectByPrimaryKey(commentsLog.getObjectId());
                if(commentsLog1==null) return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,comment do not exist");
            }
            int insert = this.insert(commentsLog);
            if(insert>0){
                //维护帖子评论数
                this.maintenanceStickComments(stick);
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",commentsLog,null);
            }
        }else{

            int i = this.updateByPk(commentsLog);
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",commentsLog,null);
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
     }

    private void maintenanceStickComments(Stick stick) {
        CommentsLogExample countByExample = new CommentsLogExample();
        countByExample.createCriteria().andStickIdEqualTo(stick.getId()).andStateEqualTo(1);
        Long count = commentsLogMapper.countByExample(countByExample);
        stick.setComments(count.intValue());
        stickMapper.updateByPrimaryKeySelective(stick);
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        CommentsLog commentsLog1 = commentsLogMapper.selectByPrimaryKey(id);
        Stick stick = stickMapper.selectByPrimaryKey(commentsLog1.getStickId());
        commentsLog1.setState(3);//逻辑删除
        int i = this.updateByPk(commentsLog1);
        if(i>0){
            //维护帖子评论数
            this.maintenanceStickComments(stick);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel getItem(Map<String, Object> mapByRequest) {
        HashMap<String, Object> map = new HashMap<>();
        CommentsLog entity = this.selectByPk(Integer.valueOf(mapByRequest.get("id").toString()));
        if(entity!=null){
            map.put("commentsLog",entity);
            //查原帖数据
            if(entity.getStickId()!=null) {
                Stick stick = stickMapper.selectByPrimaryKey(entity.getStickId());
                map.put("stick",stick);
            }
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"select success",map,null);
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"select faild");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "ids", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "state", strVals = {"1","2"}),
    })
    public ReturnModel updateState(Map<String, Object> mapByRequest) {
        String[] ids = mapByRequest.get("ids").toString().split(",");
        if(ids!=null && ids.length>0 &&(mapByRequest.get("state")!=null || mapByRequest.get("tag")!=null)){
            for (String id:ids) {
                CommentsLog commentsLog = new CommentsLog();
                commentsLog.setId(Integer.valueOf(id));
                if(mapByRequest.get("state")!=null)commentsLog.setState(Integer.valueOf(mapByRequest.get("state").toString()));
                commentsLogMapper.updateByPrimaryKeySelective(commentsLog);
                commentsLog = commentsLogMapper.selectByPrimaryKey(commentsLog.getId());
                Stick stick = stickMapper.selectByPrimaryKey(commentsLog.getStickId());
                //维护帖子评论数
                this.maintenanceStickComments(stick);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }
}
