package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.LanguagePack;
import com.gotway.gotway.pojo.Properties;
import com.gotway.gotway.pojo.PropertiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesMapper extends BaseMapper<Properties>{
    long countByExample(PropertiesExample example);

    int deleteByExample(PropertiesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Properties record);

    int insertSelective(Properties record);

    List<Properties> selectByExample(PropertiesExample example);

    Properties selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Properties record, @Param("example") PropertiesExample example);

    int updateByExample(@Param("record") Properties record, @Param("example") PropertiesExample example);

    int updateByPrimaryKeySelective(Properties record);

    int updateByPrimaryKey(Properties record);

    void set_GLOBAL_group_concat_max_len();
}