package com.gotway.gotway.mapper;

import com.gotway.gotway.pojo.Theme;
import com.gotway.gotway.pojo.ThemeExample;
import java.util.List;
import java.util.Map;

import com.gotway.gotway.pojo.UserMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeMapper extends BaseMapper<Theme>{
    long countByExample(ThemeExample example);

    int deleteByExample(ThemeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Theme record);

    int insertSelective(Theme record);

    List<Theme> selectByExample(ThemeExample example);

    Theme selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Theme record, @Param("example") ThemeExample example);

    int updateByExample(@Param("record") Theme record, @Param("example") ThemeExample example);

    int updateByPrimaryKeySelective(Theme record);

    int updateByPrimaryKey(Theme record);

    int checkThemeId(Integer id);
    //查它自身和上一条或下一条记录
    List<Theme> selectSelfAndUpOrDonwById(Map<String,Object> mapByRequest);

    Integer selectMaxTag();
}