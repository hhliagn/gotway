package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.PointAssist;
import com.gotway.gotway.pojo.PointAssistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointAssistMapper extends BaseMapper<PointAssist> {
    long countByExample(PointAssistExample example);

    int deleteByExample(PointAssistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PointAssist record);

    int insertSelective(PointAssist record);

    List<PointAssist> selectByExample(PointAssistExample example);

    PointAssist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointAssist record, @Param("example") PointAssistExample example);

    int updateByExample(@Param("record") PointAssist record, @Param("example") PointAssistExample example);

    int updateByPrimaryKeySelective(PointAssist record);

    int updateByPrimaryKey(PointAssist record);
}