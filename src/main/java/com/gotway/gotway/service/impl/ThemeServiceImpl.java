package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.FileLogMapper;
import com.gotway.gotway.mapper.ThemeMapper;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.FileLogService;
import com.gotway.gotway.service.ThemeService;
import com.gotway.gotway.service.TokenService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ThemeServiceImpl extends BaseServiceImpl<Theme> implements ThemeService {
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private FieldService fieldServcie;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FileLogService fileLogService;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "theme", minLen = 1),
        @ValidateField(index = 0, notNull = false, key = "tag", strVals = {"1","2"} ),
        @ValidateField(index = 0, notNull = false, key = "state", strVals = {"1","2"} ),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
        @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
        @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        Theme theme = Map2Bean.getInstance().getBean(Theme.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        ThemeExample example = new ThemeExample();
        ThemeExample.Criteria c = example.createCriteria();
        if(theme.getTheme()!=null)c.andThemeLike("%"+theme.getTheme()+"%");
        if(theme.getTag()!=null) c.andTagEqualTo(theme.getTag());
        if(theme.getState()!=null)c.andStateEqualTo(theme.getState());
        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Theme> themes = themeMapper.selectByExample(example);
        Long total = page.getTotal();

        if(themes!=null && themes.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",themes,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",themes,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "theme", minLen = 1,maxLen=64),
        @ValidateField(index = 0, notNull = false, key = "describe", minLen = 1,maxLen=500),
        @ValidateField(index = 0, notNull = false, key = "state", minVal = 1),
        @ValidateField(index = 0, notNull = false, key = "imgs", minLen=1),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Theme theme = Map2Bean.getInstance().getBean(Theme.class, mapByRequest, null);
        //检查Name是否被使用
        HashMap<String, Object> map = new HashMap<>();
        map.put("tableName","t_theme");
        map.put("fieldName","theme");
        map.put("fieldValue",theme.getTheme());
        if(theme.getId()==null){
            map.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(theme) ");
            theme.setComments(0);
            theme.setReport(0);
            theme.setCreateTime(new Date());
            theme.setState(1);//状态[1:正常2:屏蔽]
            Integer userId = null;
            User user= tokenService.getUserInfo(mapByRequest);
            if(user!=null)userId = user.getId();
            theme.setUserId(userId);

            Integer maxTag  = themeMapper.selectMaxTag();
            theme.setTag(maxTag+1);
            int insert = this.insert(theme);
            if(insert>0){
                fileLogService.saveImg(mapByRequest,theme.getId(),2);
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",theme,null);
            }
        }else{
            if(this.selectByPk(theme.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",theme.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(theme) ");
            int i = this.updateByPk(theme);
            if(i>0){
                fileLogService.saveImg(mapByRequest,theme.getId(),2);
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",theme,null);
            }
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
     }


    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        int ii =themeMapper.checkThemeId(id);
        if(ii==0){
            int i = this.deleteByPk(id);
            if(i>0){
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
            }
            return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"There is a data association and deletion is not allowed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "ids", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "state", strVals = {"1","2"}),
    })
    public ReturnModel updateState(Map<String, Object> mapByRequest) {
        String[] ids = mapByRequest.get("ids").toString().split(",");
        if(ids!=null && ids.length>0 &&(mapByRequest.get("state")!=null || mapByRequest.get("tag")!=null)){
            for (String id:ids) {
                Theme theme = new Theme();
                theme.setId(Integer.valueOf(id));
                if(mapByRequest.get("state")!=null)theme.setState(Integer.valueOf(mapByRequest.get("state").toString()));
                themeMapper.updateByPrimaryKeySelective(theme);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "id", minLen = 1),
            @ValidateField(index = 0, notNull = true, key = "upDown", strVals = {"up","down"}),
    })
    public ReturnModel upDown(Map<String, Object> mapByRequest) {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        List<Theme> li = themeMapper.selectSelfAndUpOrDonwById(mapByRequest);
        Theme self = null;
        Theme up = null;
        Theme down = null;
        int tag = 0 ;
        if("up".equals(mapByRequest.get("upDown").toString())){
            //查出它本身和上一个序号 然后交换tag（序号）
            if(li!=null && li.size()>0) {
                for (Theme th:li ) {
                    if(id.equals(th.getId())){
                        self=th;
                        tag=self.getTag();
                        break;
                    }
                }
                if(tag==1)return new ReturnModel(ReturnModel.CODE_SUCCESS,"the top");
                else{
                    for (Theme th:li ) {
                        if(tag>th.getTag()){
                            up=th;
                            break;
                        }
                    }
                    Integer tag1 = self.getTag();
                    self.setTag(up.getTag());
                    up.setTag(tag1);
                    themeMapper.updateByPrimaryKeySelective(self);
                    themeMapper.updateByPrimaryKeySelective(up);
                }
            }

        }else{//下移
            //查出它本身和下一个序号，然后交换tag（序号）
            if(li!=null && li.size()>0) {
                for (Theme th:li ) {
                    if(id.equals(th.getId())){
                        self=th;
                        tag=self.getTag();
                        break;
                    }
                }
                Integer maxTag = themeMapper.selectMaxTag();
                if(tag==maxTag)return new ReturnModel(ReturnModel.CODE_SUCCESS,"the end");
                else{
                    for (Theme th:li ) {
                        if(tag<th.getTag()){
                            down=th;
                            break;
                        }
                    }
                    Integer tag1 = self.getTag();
                    self.setTag(down.getTag());
                    down.setTag(tag1);
                    themeMapper.updateByPrimaryKeySelective(self);
                    themeMapper.updateByPrimaryKeySelective(down);
                }
            }
        }

        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }
}
