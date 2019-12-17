package com.gotway.gotway.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.mapper.PropertiesMapper;
import com.gotway.gotway.mapper.SysLogMapper;
import com.gotway.gotway.pojo.Properties;
import com.gotway.gotway.pojo.PropertiesExample;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.SysLogExample;
import com.gotway.gotway.service.SysLogService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private PropertiesMapper propertiesMapper;
    @Value("${TRANSLATE_SYS_LOG_CN}")
    private String TRANSLATE_SYS_LOG_CN;
    @Value("${TRANSLATE_SYS_LOG_CN_DEFUALT_VALUE}")
    private String TRANSLATE_SYS_LOG_CN_VALUE;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "operation", minLen=1 ),
            @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
            @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
            @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception {
        SysLog sysLog = Map2Bean.getInstance().getBean(SysLog.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);

        SysLogExample example = new SysLogExample();
        SysLogExample.Criteria c = example.createCriteria();
        if(sysLog.getOperation()!=null){
            c.andOperationLike("%"+sysLog.getOperation()+"%");
        }
        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));
        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<SysLog> sysLogs = sysLogMapper.selectByExample(example);
        Long total = page.getTotal();

        if(sysLogs!=null && sysLogs.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",sysLogs,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",sysLogs,total.intValue());
    }


}
