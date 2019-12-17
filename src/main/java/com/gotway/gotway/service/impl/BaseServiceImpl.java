package com.gotway.gotway.service.impl;

import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.mapper.BaseMapper;
import com.gotway.gotway.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;


@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

	protected BaseMapper<T> mapper;

	@PostConstruct
	public void baseMapperInit() {
		//1.得到参数化类型的实际类型 的名字，如  clazzName = Supplier
		 ParameterizedType type =(ParameterizedType) this.getClass().getGenericSuperclass();
		 Class<T> clazz= (Class<T>) type.getActualTypeArguments()[0];
		 String clazzName = clazz.getSimpleName();//得到参数化类型的实际类型 的名字，如 Supplier
		 //2.拼成所需***Mapper
		 clazzName= (char)(clazzName.charAt(0)+32)+clazzName.substring(1) ;
		 clazzName+="Mapper";
		 System.out.println(clazzName);	
		 //3.通过反射 ，得到属性名为clazzName对应值的属性。如supplierMapper
		 try {
			Field field = this.getClass().getDeclaredField(clazzName);
			Field field_ = this.getClass().getSuperclass().getDeclaredField("mapper");
			field.setAccessible(true);
			field_.setAccessible(true);
			field_.set(this,field.get(this));
		} catch (Exception e) {
			System.out.println("字段获取失败");
			e.printStackTrace();
		} 
		// System.out.println("baseMapper============="+baseMapper.getClass());
	}

	@Override
	public int insert(T entity) throws Exception {
		int j = mapper.insertSelective(entity);
		//int i=1/0; //事务测试
		return j;
	}

	@Override
	public T selectByPk(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPk(Integer id) throws Exception {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteList(String[] ids) throws Exception {
		if(ids==null || ids.length<1)return 0;
		for (String id:ids) {
			if("".equals(id.trim()))break;
			mapper.deleteByPrimaryKey(Integer.valueOf(id.trim()));
		}
		return 1;
	}

	@Override
	public int updateByPk(T entity) throws Exception {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<T> selectList(T entity) {
		return null;
	}

	@Override
	public Page<T> selectPage( Page page) {
		return null;
	}


}
