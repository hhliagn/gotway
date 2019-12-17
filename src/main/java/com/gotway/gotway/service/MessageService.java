package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Message;
import java.util.Map;

public interface MessageService extends BaseService<Message> {
	 //列表
    ReturnModel _getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;
    //修改状态
    ReturnModel updateState(Map<String,Object> mapByRequest)throws Exception;
    //未读消息数
    ReturnModel unreadNum(Map<String,Object> mapByRequest)throws Exception;

}
