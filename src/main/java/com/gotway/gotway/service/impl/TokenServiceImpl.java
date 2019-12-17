package com.gotway.gotway.service.impl;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.common.util.redis.JedisClient;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.TokenService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class TokenServiceImpl extends BaseServiceImpl<User> implements TokenService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${USER_ID_TOKEN}")
    private String USER_ID_TOKEN;
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "token", minLen = 1,maxLen=64),
    })
    public ReturnModel checkToken(Map<String, Object> mapByRequest) {
        String token = mapByRequest.get("token").toString();
        String tokenkey = USER_INFO + ":" + token;
        Boolean exists = jedisClient.exists(tokenkey);
        if(exists)return new ReturnModel(ReturnModel.CODE_SUCCESS,"token is valid");
        else return new ReturnModel(ReturnModel.CODE_FAILD,"token expire");
    }
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "token", minLen = 1,maxLen=64),
    })
    public ReturnModel refreshToken(Map<String, Object> mapByRequest) {
        String token = mapByRequest.get("token").toString();
        String tokenkey = USER_INFO + ":" + token;
        Boolean exists = jedisClient.exists(tokenkey);
        if(exists){
            jedisClient.expire(tokenkey, Integer.valueOf(SysParam.get("session_ expires_back")==null?"1800":SysParam.get("session_ expires_back")));
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"refresh token success");
        }
        else return new ReturnModel(ReturnModel.CODE_FAILD,"refresh token faild");
    }

    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "token", minLen = 1,maxLen=64),
    })
    public User getUserInfo(Map<String, Object> mapByRequest) {
        String token = mapByRequest.get("token").toString();
        String tokenkey = USER_INFO + ":" + token;
        String userInfo = jedisClient.get(tokenkey);
        User user = null;
        if(userInfo!=null && !"".equals(userInfo)){
            user = JsonUtils.jsonToPojo(userInfo, User.class);
        }
        return user;
    }

    @Override
    public String setUserAndToken(User user) {
        if(user==null)return null;
        //先删除旧的
        String oldToken = jedisClient.get(USER_ID_TOKEN + ":" + user.getId());
        this.deleteUserToken(oldToken);

        String token = UUID.randomUUID().toString();
        token = token.replace("-", "");
        // 把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。使用String类型保存Session信息。可以使用“前缀:token”为key
        String tokenkey = USER_INFO + ":" + token;
        jedisClient.set(tokenkey, JsonUtils.objectToJson(user));
        jedisClient.set(USER_ID_TOKEN+":"+user.getId(),token);//保存userId跟token的映射关系
        //设置key的过期时间。模拟Session的过期时间。默认一天。
        jedisClient.expire(tokenkey, Integer.valueOf(SysParam.get("session_ expires_app")==null?"86400":SysParam.get("session_ expires_app")));
        jedisClient.expire(USER_ID_TOKEN+":"+user.getId(),
                Integer.valueOf(SysParam.get("session_ expires_app")==null?"86400":SysParam.get("session_ expires_app")));
        return token;
    }

    @Override
    public String updateUserForToken(User user) {
        String tokenkey = USER_INFO + ":"+ jedisClient.get(USER_ID_TOKEN + ":" + user.getId());
        jedisClient.set(tokenkey, JsonUtils.objectToJson(user));
        jedisClient.expire(tokenkey, Integer.valueOf(SysParam.get("session_ expires_app")==null?"86400":SysParam.get("session_ expires_app")));
        return tokenkey;
    }

    @Override
    public boolean deleteUserToken(String token) {
        String tokenkey = USER_INFO + ":" + token;
        Boolean delete = jedisClient.delete(tokenkey);
        return delete;
    }

    @Override
    public String set(String key, String value, long second) {
        jedisClient.set(key,value);
        jedisClient.expire(key,second);
        return value;
    }

    @Override
    public String get(String key) {
        return jedisClient.get(key);
    }

    @Override
    public boolean delete(String key) {
        return jedisClient.delete(key);
    }

}
