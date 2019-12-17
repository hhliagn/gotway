package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.PraiseLog;
import com.gotway.gotway.pojo.PraiseLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PraiseLogMapper extends BaseMapper<PraiseLog>{
    long countByExample(PraiseLogExample example);

    int deleteByExample(PraiseLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PraiseLog record);

    int insertSelective(PraiseLog record);

    List<PraiseLog> selectByExample(PraiseLogExample example);

    PraiseLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PraiseLog record, @Param("example") PraiseLogExample example);

    int updateByExample(@Param("record") PraiseLog record, @Param("example") PraiseLogExample example);

    int updateByPrimaryKeySelective(PraiseLog record);

    int updateByPrimaryKey(PraiseLog record);
}