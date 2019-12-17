package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Language;
import java.util.Map;

public interface LanguageService extends BaseService<Language> {
	 //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;
    //获取语言包数据
    ReturnModel getItem(Map<String, Object> mapByRequest) throws Exception;
}
