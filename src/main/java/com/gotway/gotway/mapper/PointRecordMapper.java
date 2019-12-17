package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.PointRecord;
import com.gotway.gotway.pojo.PointRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRecordMapper extends BaseMapper<PointRecord>{
    long countByExample(PointRecordExample example);

    int deleteByExample(PointRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PointRecord record);

    int insertSelective(PointRecord record);

    List<PointRecord> selectByExample(PointRecordExample example);

    PointRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointRecord record, @Param("example") PointRecordExample example);

    int updateByExample(@Param("record") PointRecord record, @Param("example") PointRecordExample example);

    int updateByPrimaryKeySelective(PointRecord record);

    int updateByPrimaryKey(PointRecord record);
}