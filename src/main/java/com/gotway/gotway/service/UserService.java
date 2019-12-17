package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.User;

import java.util.Map;

public interface UserService extends BaseService<User> {

    //注册
    ReturnModel register(Map<String, Object> mapByRequest) throws Exception;
    //列表
    ReturnModel getList(Map<String,Object> mapByRequest)throws Exception;
    //添加或修改
    ReturnModel addOrUpdate(Map<String,Object> mapByRequest)throws Exception;
    //app登陆
    ReturnModel login(Map<String,Object> mapByRequest)throws Exception;
    //后台登陆
    ReturnModel backLogin(Map<String,Object> mapByRequest)throws Exception;
    //退出登陆
    ReturnModel logout(Map<String,Object> mapByRequest);
    //删除用户
    ReturnModel del(Map<String,Object> mapByRequest)throws Exception;
    //获取管理员用户（已指定角色/未指定角色）
    ReturnModel getAdminUser(Map<String,Object> mapByRequest) throws Exception;
    //获取验证码到邮箱
    ReturnModel getVerifyCode(Map<String,Object> mapByRequest);
    //重置app用户密码
    ReturnModel resetPassword(Map<String,Object> mapByRequest);
    //修改状态（支持批量）
    ReturnModel updateState(Map<String,Object> mapByRequest);
    //附近车队
    ReturnModel nearby(Map<String,Object> mapByRequest) throws Exception;
    //更新位置信息
    ReturnModel updateLocation(Map<String,Object> mapByRequest)throws Exception ;
    //编辑用户信息（app）
    ReturnModel edit(Map<String,Object> mapByRequest)throws Exception;
    //编辑用户信息（app）
    ReturnModel edit2(Map<String,Object> mapByRequest)throws Exception;
    //修改密码
    ReturnModel updatePassword(Map<String,Object> mapByRequest);
    //获取最新个人信息
    ReturnModel getSelfInfo(Map<String,Object> mapByRequest);
}
