package com.gotway.gotway.service;

import com.gotway.gotway.common.pojo.Page;
import java.util.List;

public interface BaseService<T> {
	//添加实体对象信息到表
	 int insert(T entity) throws Exception;
	 //根据对象主键查询
	 T selectByPk(Integer id);
	 //根据对象主键删除
	 int deleteByPk(Integer id) throws Exception;
	 //根据对象主键删除
	 int deleteList(String[] ids) throws Exception;
	 //根据对象主键修改
	 int updateByPk(T entity) throws Exception;
	//根据对象动态查询对象列表
	 List<T> selectList(T entity);
	 //根据对象动态查询对象列表
	 Page<T> selectPage(Page<T> page);
}
