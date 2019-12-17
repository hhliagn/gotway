package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.DrivingRecord;
import com.gotway.gotway.pojo.DrivingRecordExample;
import java.util.List;
import java.util.Map;

import com.gotway.gotway.pojo.LanguagePack;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrivingRecordMapper extends BaseMapper<DrivingRecord>{
    long countByExample(DrivingRecordExample example);

    int deleteByExample(DrivingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DrivingRecord record);

    int insertSelective(DrivingRecord record);

    List<DrivingRecord> selectByExample(DrivingRecordExample example);

    DrivingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DrivingRecord record, @Param("example") DrivingRecordExample example);

    int updateByExample(@Param("record") DrivingRecord record, @Param("example") DrivingRecordExample example);

    int updateByPrimaryKeySelective(DrivingRecord record);

    int updateByPrimaryKey(DrivingRecord record);

    List<Map<String,Object>> selectForStatisticsLastWeek();
    List<Map<String,Object>> selectForStatisticsLastMonth();
}