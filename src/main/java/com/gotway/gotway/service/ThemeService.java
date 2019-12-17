package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Theme;
import java.util.Map;

public interface ThemeService extends BaseService<Theme> {
	 //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;
    //修改状态（支持匹量修改）
    ReturnModel updateState(Map<String,Object> mapByRequest);
    //上移下移
    ReturnModel upDown(Map<String,Object> mapByRequest);
}
