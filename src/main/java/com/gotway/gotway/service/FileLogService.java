package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.FileLog;
import com.gotway.gotway.pojo.FileLogExample;

import java.util.List;
import java.util.Map;

public interface FileLogService extends BaseService<FileLog> {
	 //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;
    //保存文件
    void saveImg(Map<String, Object> mapByRequest, Integer  id,Integer type );
}
