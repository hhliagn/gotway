package com.gotway.gotway.mapper;



import com.gotway.gotway.common.pojo.Page;

import java.util.List;

public interface BaseMapper<T> {
   
    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
    
    Integer selectCountUseDyc(Page<T> page);

	List<T> selectListUseDyc(Page<T> page);

	
}