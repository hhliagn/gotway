package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.FocusLogMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.FocusLog;
import com.gotway.gotway.pojo.FocusLogExample;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.FocusLogService;
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
public class FocusLogServiceImpl extends BaseServiceImpl<FocusLog> implements FocusLogService {
    @Autowired
    private FocusLogMapper focusLogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenService tokenServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "userId", minVal = 0 ),
        @ValidateField(index = 0, notNull = false, key = "audienceId", minVal = 0 ),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))mapByRequest.put("orderByClause", orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<User> focusLogs = userMapper.selectFocusOrFans(mapByRequest);
        Long total = page.getTotal();

        if(focusLogs!=null && focusLogs.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",focusLogs,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",focusLogs,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "userId", minVal = 0),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        FocusLog focusLog = Map2Bean.getInstance().getBean(FocusLog.class, mapByRequest, null);
        User userInfo = tokenServcie.getUserInfo(mapByRequest);
        if(userInfo==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,not login");
        focusLog.setAudienceId(userInfo.getId());
        focusLog.setCreateTime(new Date());
        FocusLogExample example = new FocusLogExample();
        example.createCriteria().andAudienceIdEqualTo(userInfo.getId()).andUserIdEqualTo(focusLog.getUserId());
        List<FocusLog> focusLogs = focusLogMapper.selectByExample(example);

        if(focusLogs!=null && focusLogs.size()>0){//已关注：取消关注
            for (FocusLog f:focusLogs) {
                focusLogMapper.deleteByPrimaryKey(f.getId());
            }
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
        }else{
            int i = this.insert(focusLog);
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
