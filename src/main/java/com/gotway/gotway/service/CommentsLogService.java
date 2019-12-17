package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.CommentsLog;
import java.util.Map;

public interface CommentsLogService extends BaseService<CommentsLog> {
	 //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;
    //查看详情
    ReturnModel getItem(Map<String,Object> mapByRequest);
    //修改 状态
    ReturnModel updateState(Map<String,Object> mapByRequest);
}
