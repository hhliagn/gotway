package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.LanguageMap;
import com.gotway.gotway.pojo.LanguageMapExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageMapMapper extends BaseMapper<LanguageMap>{
    long countByExample(LanguageMapExample example);

    int deleteByExample(LanguageMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LanguageMap record);

    int insertSelective(LanguageMap record);

    List<LanguageMap> selectByExample(LanguageMapExample example);

    LanguageMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LanguageMap record, @Param("example") LanguageMapExample example);

    int updateByExample(@Param("record") LanguageMap record, @Param("example") LanguageMapExample example);

    int updateByPrimaryKeySelective(LanguageMap record);

    int updateByPrimaryKey(LanguageMap record);
}