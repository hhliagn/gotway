package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Menu;

import java.util.Map;

public interface MenuService extends BaseService<Menu> {
    //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;

    ReturnModel getRoleMenu(Map<String,Object> mapByRequest)throws Exception;

    ReturnModel getUserMenu(Map<String,Object> mapByRequest)throws Exception;
}
