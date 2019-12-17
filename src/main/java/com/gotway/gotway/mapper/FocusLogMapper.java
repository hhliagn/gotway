package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.FocusLog;
import com.gotway.gotway.pojo.FocusLogExample;
import java.util.List;

import com.gotway.gotway.pojo.LanguagePack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FocusLogMapper extends BaseMapper<FocusLog>{
    long countByExample(FocusLogExample example);

    int deleteByExample(FocusLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FocusLog record);

    int insertSelective(FocusLog record);

    List<FocusLog> selectByExample(FocusLogExample example);

    FocusLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FocusLog record, @Param("example") FocusLogExample example);

    int updateByExample(@Param("record") FocusLog record, @Param("example") FocusLogExample example);

    int updateByPrimaryKeySelective(FocusLog record);

    int updateByPrimaryKey(FocusLog record);
}