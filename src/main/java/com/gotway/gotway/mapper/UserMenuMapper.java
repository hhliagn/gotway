package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.UserMenu;
import com.gotway.gotway.pojo.UserMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMenuMapper extends BaseMapper<UserMenu>{
    long countByExample(UserMenuExample example);

    int deleteByExample(UserMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserMenu record);

    int insertSelective(UserMenu record);

    List<UserMenu> selectByExample(UserMenuExample example);

    UserMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserMenu record, @Param("example") UserMenuExample example);

    int updateByExample(@Param("record") UserMenu record, @Param("example") UserMenuExample example);

    int updateByPrimaryKeySelective(UserMenu record);

    int updateByPrimaryKey(UserMenu record);
}