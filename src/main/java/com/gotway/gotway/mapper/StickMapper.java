package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.Stick;
import com.gotway.gotway.pojo.StickExample;
import java.util.List;
import java.util.Map;

import com.gotway.gotway.pojo.vo.StickVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StickMapper extends BaseMapper<Stick>{
    long countByExample(StickExample example);

    int deleteByExample(StickExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Stick record);

    int insertSelective(Stick record);

    List<Stick> selectByExample(StickExample example);

    Stick selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Stick record, @Param("example") StickExample example);

    int updateByExample(@Param("record") Stick record, @Param("example") StickExample example);

    int updateByPrimaryKeySelective(Stick record);

    int updateByPrimaryKey(Stick record);

    List<Stick> selectByExampleForPraise(StickExample example);

    List<StickVo> getListForApp(Map<String,Object> mapByRequest);
}