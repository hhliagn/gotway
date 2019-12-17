package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Properties;
import com.gotway.gotway.pojo.User;

import java.util.Map;

public interface PropertiesService extends BaseService<Properties> {
    //列表
    ReturnModel getList(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //修改界面的值
    ReturnModel updateForXL(Map<String,Object> mapByRequest);
    //消息类型列表
    ReturnModel messageTypes(Map<String,Object> mapByRequest);
    //反馈类型列表
    ReturnModel feedbackTypes(Map<String,Object> mapByRequest);
    //针对group_concat长度限制问题的解决
    void set_GLOBAL_group_concat_max_len();
}
