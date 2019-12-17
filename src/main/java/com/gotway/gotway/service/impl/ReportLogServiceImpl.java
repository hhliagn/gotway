package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.ReportLogMapper;
import com.gotway.gotway.mapper.StickMapper;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.ReportLogService;
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
public class ReportLogServiceImpl extends BaseServiceImpl<ReportLog> implements ReportLogService {
    @Autowired
    private ReportLogMapper reportLogMapper;
    @Autowired
    private TokenService tokenServcie;
    @Autowired
    private StickMapper stickMapper;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "name", minVal = 1 ),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        ReportLog reportLog = Map2Bean.getInstance().getBean(ReportLog.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        ReportLogExample example = new ReportLogExample();
        ReportLogExample.Criteria c = example.createCriteria();
       // if(reportLog.getName()!=null)c.andNameLike("%"+reportLog.getName()+"%");

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<ReportLog> reportLogs = reportLogMapper.selectByExample(example);
        Long total = page.getTotal();

        if(reportLogs!=null && reportLogs.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",reportLogs,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",reportLogs,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = true, key = "stickId", minVal = 0),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        ReportLog reportLog = Map2Bean.getInstance().getBean(ReportLog.class, mapByRequest, null);
        User userInfo = tokenServcie.getUserInfo(mapByRequest);
        if(userInfo==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,not login");
        reportLog.setUserId(userInfo.getId());
        reportLog.setCreateTime(new Date());
        Stick stick = stickMapper.selectByPrimaryKey(reportLog.getStickId());
        //先查是否举报
        ReportLogExample example = new ReportLogExample();
        example.createCriteria().andStickIdEqualTo(reportLog.getStickId()).andUserIdEqualTo(reportLog.getUserId());
        List<ReportLog> reportLogs = reportLogMapper.selectByExample(example);
        if(reportLogs!=null && reportLogs.size()>0){//已举报：取消举报，扣减举报数
            for (ReportLog r :reportLogs) {
                reportLogMapper.deleteByPrimaryKey(r.getId());
                stick.setReport(stick.getReport()>0?stick.getReport()-1:0);
            }
            stickMapper.updateByPrimaryKeySelective(stick);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
        }else{
            int i = this.insert(reportLog);
            stick.setReport(stick.getReport()+1);
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
