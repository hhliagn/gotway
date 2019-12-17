package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.ReportLog;
import com.gotway.gotway.pojo.ReportLogExample;
import java.util.List;

import com.gotway.gotway.pojo.Stick;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportLogMapper extends BaseMapper<ReportLog>{
    long countByExample(ReportLogExample example);

    int deleteByExample(ReportLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReportLog record);

    int insertSelective(ReportLog record);

    List<ReportLog> selectByExample(ReportLogExample example);

    ReportLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReportLog record, @Param("example") ReportLogExample example);

    int updateByExample(@Param("record") ReportLog record, @Param("example") ReportLogExample example);

    int updateByPrimaryKeySelective(ReportLog record);

    int updateByPrimaryKey(ReportLog record);
}