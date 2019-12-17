package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.LanguagePackMapper;
import com.gotway.gotway.pojo.LanguagePack;
import com.gotway.gotway.pojo.LanguagePackExample;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.LanguagePackService;
import com.gotway.gotway.service.TokenService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LanguagePackServiceImpl extends BaseServiceImpl<LanguagePack> implements LanguagePackService {
    @Autowired
    private LanguagePackMapper languagePackMapper;
    @Autowired
    private FieldService fieldServcie;
    @Autowired
    private TokenService tokenServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "name", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "code", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        LanguagePack languagePack = Map2Bean.getInstance().getBean(LanguagePack.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        LanguagePackExample example = new LanguagePackExample();
        LanguagePackExample.Criteria c = example.createCriteria();
        if(languagePack.getName()!=null)c.andNameLike("%"+languagePack.getName()+"%");
        if(languagePack.getCode()!=null)c.andCodeEqualTo(languagePack.getCode());
        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<LanguagePack> languagePacks = languagePackMapper.selectByExample(example);
        Long total = page.getTotal();

        if(languagePacks!=null && languagePacks.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",languagePacks,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",languagePacks,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "name", minLen = 1,maxLen=64),
        @ValidateField(index = 0, notNull = true, key = "url", minLen = 1,maxLen=255),
        @ValidateField(index = 0, notNull = true, key = "code", minLen = 1,maxLen=64),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        LanguagePack languagePack = Map2Bean.getInstance().getBean(LanguagePack.class, mapByRequest, null);
        User user  = tokenServcie.getUserInfo(mapByRequest);
        if(user!=null){
            languagePack.setUserId(user.getId());
        }
        languagePack.setCreateTime(new Date());
        //检查Name是否被使用
        HashMap<String, Object> map = new HashMap<>();
        map.put("tableName","t_language_pack");
        map.put("fieldName","code_");
        map.put("fieldValue",languagePack.getCode());
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("tableName","t_language_pack");
        map2.put("fieldName","name_");
        map2.put("fieldValue",languagePack.getName());
        if(languagePack.getId()==null){
            map.put("id",-1);
            map2.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(code) ");
            ReturnModel repeated2 = fieldServcie.is_repeated(map2);
            if(repeated2.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");

            int insert = this.insert(languagePack);
            if(insert>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",languagePack,null);
        }else{
            if(this.selectByPk(languagePack.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",languagePack.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(code) ");
            ReturnModel repeated2 = fieldServcie.is_repeated(map2);
            if(repeated2.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");
            int i = this.updateByPk(languagePack);
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",languagePack,null);
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
     }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        int i = this.deleteByPk(id);
        if(i>0){
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }
}
