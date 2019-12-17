package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.Top;
import com.gotway.gotway.pojo.TopExample;
import com.gotway.gotway.pojo.vo.TopVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TopMapper extends BaseMapper<Top>{
    long countByExample(TopExample example);

    int deleteByExample(TopExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Top record);

    int insertSelective(Top record);

    List<Top> selectByExample(TopExample example);

    Top selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Top record, @Param("example") TopExample example);

    int updateByExample(@Param("record") Top record, @Param("example") TopExample example);

    int updateByPrimaryKeySelective(Top record);

    int updateByPrimaryKey(Top record);

    List<TopVo> selectForTop(Map<String,Object> map);

    TopVo selectForSelf(Map<String,Object>  map);
}