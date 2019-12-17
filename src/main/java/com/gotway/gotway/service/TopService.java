package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Top;
import java.util.Map;

public interface TopService extends BaseService<Top> {
	 //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;

    //列表
    ReturnModel refresh(Map<String, Object> mapByRequest) throws Exception;
}
