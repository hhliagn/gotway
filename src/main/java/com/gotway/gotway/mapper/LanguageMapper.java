package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.Language;
import com.gotway.gotway.pojo.LanguageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageMapper extends BaseMapper<Language>{
    long countByExample(LanguageExample example);

    int deleteByExample(LanguageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Language record);

    int insertSelective(Language record);

    List<Language> selectByExample(LanguageExample example);

    Language selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Language record, @Param("example") LanguageExample example);

    int updateByExample(@Param("record") Language record, @Param("example") LanguageExample example);

    int updateByPrimaryKeySelective(Language record);

    int updateByPrimaryKey(Language record);
}