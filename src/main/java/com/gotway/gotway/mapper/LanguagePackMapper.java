package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.LanguagePack;
import com.gotway.gotway.pojo.LanguagePackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguagePackMapper extends BaseMapper<LanguagePack> {
    long countByExample(LanguagePackExample example);

    int deleteByExample(LanguagePackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LanguagePack record);

    int insertSelective(LanguagePack record);

    List<LanguagePack> selectByExample(LanguagePackExample example);

    LanguagePack selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LanguagePack record, @Param("example") LanguagePackExample example);

    int updateByExample(@Param("record") LanguagePack record, @Param("example") LanguagePackExample example);

    int updateByPrimaryKeySelective(LanguagePack record);

    int updateByPrimaryKey(LanguagePack record);
}