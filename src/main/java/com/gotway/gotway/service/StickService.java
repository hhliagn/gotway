package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.Stick;
import java.util.Map;

public interface StickService extends BaseService<Stick> {
	 //列表
    ReturnModel getList(Map<String, Object> mapByRequest) throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String, Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel del(Map<String, Object> mapByRequest)throws Exception;
    //查看
    ReturnModel getItem(Map<String,Object> mapByRequest)throws Exception;
    //修改状态
    ReturnModel updateState(Map<String,Object> mapByRequest)throws Exception;
    //帖子列表 For 评论
    ReturnModel getListForComment(Map<String,Object> mapByRequest)throws Exception;
    //帖子列表 For 点赞
    ReturnModel getListForPraise(Map<String,Object> mapByRequest)throws Exception;
    //列表For App
    ReturnModel getListForApp(Map<String,Object> mapByRequest)throws Exception;
}
