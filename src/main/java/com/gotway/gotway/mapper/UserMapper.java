package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.User;
import com.gotway.gotway.pojo.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User>{
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkRepeated(String sql);//检查字段是否重复

    int checkUserId(Integer id);

    List<User> selectUserWithRoleName(UserExample example);

    List<User> selectNearBy(Map<String,Object> mapByRequest);//附近的人

    List<User> selectFocusOrFans(Map<String,Object> mapByRequest);//关注和粉丝

    List<User> selectByExample2(UserExample example);
}