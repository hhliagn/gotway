package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.LanguageMapMapper;
import com.gotway.gotway.mapper.LanguageMapper;
import com.gotway.gotway.pojo.Language;
import com.gotway.gotway.pojo.LanguageExample;
import com.gotway.gotway.pojo.LanguageMap;
import com.gotway.gotway.pojo.LanguageMapExample;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.LanguageService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class LanguageServiceImpl extends BaseServiceImpl<Language> implements LanguageService {
    @Autowired
    private LanguageMapper languageMapper;
    @Autowired
    private LanguageMapMapper languageMapMapper;
    @Autowired
    private FieldService fieldServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "name", minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "code", minLen = 1 ),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        Language language = Map2Bean.getInstance().getBean(Language.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        LanguageExample example = new LanguageExample();
        LanguageExample.Criteria c = example.createCriteria();
        if(language.getName()!=null)c.andNameLike("%"+language.getName()+"%");
        if(language.getCode()!=null)c.andCodeEqualTo(language.getCode());

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Language> languages = languageMapper.selectByExample(example);
        Long total = page.getTotal();

        if(languages!=null && languages.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",languages,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",languages,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
            @ValidateField(index = 0, notNull = true, key = "name", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "code", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "tag", strVals = {"1","2"}),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Language language = Map2Bean.getInstance().getBean(Language.class, mapByRequest, null);

        //检查Name是否被使用
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("tableName","t_language");
        map2.put("fieldName","name");
        map2.put("fieldValue",language.getName());
        map.put("tableName","t_language_pack");
        map.put("fieldName","code_");
        map.put("fieldValue",language.getCode());
        if(language.getId()==null){
            map.put("id",-1);
            map2.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(code) ");
            ReturnModel repeated2 = fieldServcie.is_repeated(map2);
            if(repeated2.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");
            language.setCreateTime(new Date());
            int insert = this.insert(language);
            if(insert>0){
                if(mapByRequest.get("data")!=null) this.saveLanguageMap(language.getId(),(Map<String,String>)mapByRequest.get("data"));
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",language,null);
            }
        }else{
            if(this.selectByPk(language.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",language.getId());
            map2.put("id",language.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(code) ");
            ReturnModel repeated2 = fieldServcie.is_repeated(map2);
            if(repeated2.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");
            int i = this.updateByPk(language);
            if(i>0){
                if(mapByRequest.get("data")!=null) this.saveLanguageMap(language.getId(),(Map<String,String>)mapByRequest.get("data"));
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",language,null);
            }
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
     }

    private void saveLanguageMap(Integer id, Map<String,String> data) {
        if(data!=null){
            LanguageMapExample example = new LanguageMapExample();
            example.createCriteria().andLanguageIdEqualTo(id);
            languageMapMapper.deleteByExample(example);

            Set<Map.Entry<String, String>> entries = data.entrySet();
            for (Map.Entry<String, String> entry:entries) {
                LanguageMap record =new LanguageMap();
                record.setLanguageId(id);
                record.setKey(entry.getKey());
                record.setValue(entry.getValue());
                languageMapMapper.insertSelective(record);
            }
        }
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        int i = this.deleteByPk(id);
        if(i>0){
            LanguageMapExample example = new LanguageMapExample();
            example.createCriteria().andLanguageIdEqualTo(id);
            languageMapMapper.deleteByExample(example);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel getItem(Map<String, Object> mapByRequest) throws Exception {
        TreeMap<String, String> treeMap = new TreeMap<>();
        LanguageMapExample example = new LanguageMapExample();
        example.createCriteria().andLanguageIdEqualTo(Integer.valueOf(mapByRequest.get("id").toString()));
        List<LanguageMap> languageMaps = languageMapMapper.selectByExample(example);
        languageMaps.forEach(s -> treeMap.put(s.getKey(),s.getValue()));
        if(treeMap.size()>0)
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"get success",treeMap,null);
        else return new ReturnModel(ReturnModel.CODE_FAILD,"get failed,no data",treeMap,null);
    }
}
