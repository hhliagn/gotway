package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.FileLog;
import com.gotway.gotway.pojo.FileLogExample;
import java.util.List;

import com.gotway.gotway.pojo.LanguagePack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileLogMapper extends BaseMapper<FileLog>{
    long countByExample(FileLogExample example);

    int deleteByExample(FileLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FileLog record);

    int insertSelective(FileLog record);

    List<FileLog> selectByExample(FileLogExample example);

    FileLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FileLog record, @Param("example") FileLogExample example);

    int updateByExample(@Param("record") FileLog record, @Param("example") FileLogExample example);

    int updateByPrimaryKeySelective(FileLog record);

    int updateByPrimaryKey(FileLog record);
}