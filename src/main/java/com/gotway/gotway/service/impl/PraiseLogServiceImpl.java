package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.PraiseLogMapper;
import com.gotway.gotway.mapper.StickMapper;
import com.gotway.gotway.pojo.PraiseLog;
import com.gotway.gotway.pojo.PraiseLogExample;
import com.gotway.gotway.pojo.Stick;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.PraiseLogService;
import com.gotway.gotway.service.TokenService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PraiseLogServiceImpl extends BaseServiceImpl<PraiseLog> implements PraiseLogService {
    @Autowired
    private PraiseLogMapper praiseLogMapper;
    @Autowired
    private StickMapper stickMapper;
    @Autowired
    private TokenService tokenServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{

        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        PraiseLogExample example = new PraiseLogExample();
        PraiseLogExample.Criteria c = example.createCriteria();

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<PraiseLog> praiseLogs = praiseLogMapper.selectByExample(example);
        Long total = page.getTotal();

        if(praiseLogs!=null && praiseLogs.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",praiseLogs,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",praiseLogs,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = true, key = "stickId", minVal = 0),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        PraiseLog praiseLog = Map2Bean.getInstance().getBean(PraiseLog.class, mapByRequest, null);
        User userInfo = tokenServcie.getUserInfo(mapByRequest);
        if(userInfo==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,not login");
        praiseLog.setUserId(userInfo.getId());
        praiseLog.setCreateTime(new Date());
        Stick stick = stickMapper.selectByPrimaryKey(praiseLog.getStickId());
        //先查是否点赞
        PraiseLogExample example = new PraiseLogExample();
        example.createCriteria().andStickIdEqualTo(praiseLog.getStickId()).andUserIdEqualTo(praiseLog.getUserId());
        List<PraiseLog> praiseLogs = praiseLogMapper.selectByExample(example);

        if(praiseLogs!=null && praiseLogs.size()>0){//已点过，取消点赞,扣减帖子点赞数
            for (PraiseLog p :praiseLogs) {
                p.setState((p.getState()+1)%2);//状态 0有效 1失效
                praiseLogMapper.updateByPrimaryKeySelective(p);
                if(Integer.valueOf(1).equals(p.getState())){
                    stick.setAdmire(stick.getAdmire()>0?(stick.getAdmire()-1):0);
                }else{
                    stick.setAdmire(stick.getAdmire()+1);
                }
                stickMapper.updateByPrimaryKeySelective(stick);
            }
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
        }else{//未点过，点赞，加点赞数
            int i = this.insert(praiseLog);
            stick.setAdmire(stick.getAdmire()+1);
            stickMapper.updateByPrimaryKeySelective(stick);
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
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
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }
}
