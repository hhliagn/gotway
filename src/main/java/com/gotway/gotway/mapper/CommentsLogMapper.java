package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.CommentsLog;
import com.gotway.gotway.pojo.CommentsLogExample;
import java.util.List;

import com.gotway.gotway.pojo.LanguagePack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsLogMapper extends BaseMapper<CommentsLog>{
    long countByExample(CommentsLogExample example);

    int deleteByExample(CommentsLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentsLog record);

    int insertSelective(CommentsLog record);

    List<CommentsLog> selectByExample(CommentsLogExample example);

    CommentsLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentsLog record, @Param("example") CommentsLogExample example);

    int updateByExample(@Param("record") CommentsLog record, @Param("example") CommentsLogExample example);

    int updateByPrimaryKeySelective(CommentsLog record);

    int updateByPrimaryKey(CommentsLog record);
}