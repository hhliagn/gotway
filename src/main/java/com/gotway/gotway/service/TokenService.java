package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.pojo.User;

import java.util.Map;

public interface TokenService extends BaseService<User> {
    //检查token是否有效
    ReturnModel checkToken(Map<String,Object> mapByRequest);
    //刷新token的时效
    ReturnModel refreshToken(Map<String,Object> mapByRequest);
    //通过token获取用户信息
    User getUserInfo(Map<String,Object> mapByRequest);
    //处理用户跟token信息
    String setUserAndToken(User user);
    //处理用户跟token信息
    String updateUserForToken(User user);
    //删除token
    boolean  deleteUserToken(String token);
    //设置值
    String set(String key,String value,long second);
    //获取
    String get(String key);
    //删除
    boolean delete(String key);

}
