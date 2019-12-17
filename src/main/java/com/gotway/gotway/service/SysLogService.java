package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.SysLog;

import java.util.Map;

public interface SysLogService extends BaseService<SysLog> {
    //列表
    ReturnModel getList(Map<String, Object> mapByRequest)throws Exception;
}
