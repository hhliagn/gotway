package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.User;

import java.util.Map;

public interface FieldService extends BaseService<User> {
    //检查字段是否重复
    ReturnModel is_repeated(Map<String, Object> mapByRequest)throws Exception;
}
