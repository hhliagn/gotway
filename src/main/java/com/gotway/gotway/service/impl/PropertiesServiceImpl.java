package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.mapper.PropertiesMapper;
import com.gotway.gotway.pojo.Properties;
import com.gotway.gotway.pojo.PropertiesExample;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.PropertiesService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PropertiesServiceImpl extends BaseServiceImpl<Properties> implements PropertiesService {
    @Autowired
    private PropertiesMapper propertiesMapper;
    @Autowired
    private FieldService fieldServcie;
    @Value("${FEEDBACK_TYPES_KV}")
    private String FEEDBACK_TYPES_KV;
    @Value("${MESSAGE_TYPES_KV}")
    private String MESSAGE_TYPES_KV;
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "key", minLen = 1,maxLen=255),
            @ValidateField(index = 0, notNull = false, key = "value", minLen = 1,maxLen=255 ),
            @ValidateField(index = 0, notNull = false, key = "remark", minLen = 1,maxLen=255),
            @ValidateField(index = 0, notNull = false, key = "keys", minLen = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception {
        Properties properties = Map2Bean.getInstance().getBean(Properties.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        PropertiesExample example = new PropertiesExample();
        PropertiesExample.Criteria c = example.createCriteria();
        if(!StringUtils.isEmpty(properties.getKey()))c.andKeyLike("%"+properties.getKey()+"%");
        if(!StringUtils.isEmpty(properties.getValue()))c.andValueLike("%"+properties.getValue()+"%");
        if(!StringUtils.isEmpty(properties.getRemark()))c.andRemarkLike("%"+properties.getRemark()+"%");
        if(!StringUtils.isEmpty(mapByRequest.get("keys"))){
            String[] keys = mapByRequest.get("keys").toString().split(",");
            List<String> keysList = Arrays.asList(keys);
            c.andKeyIn(keysList);
        }

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Properties> propertiess = propertiesMapper.selectByExample(example);
        Long total = page.getTotal();

        if(propertiess!=null && propertiess.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",propertiess,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",propertiess,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "id", minVal=0),
            @ValidateField(index = 0, notNull = true, key = "key", minLen = 1,maxLen=255),
            @ValidateField(index = 0, notNull = true, key = "value", minLen = 1,maxLen=15000 ),
            @ValidateField(index = 0, notNull = true, key = "remark", minLen = 1,maxLen=255),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Properties properties = Map2Bean.getInstance().getBean(Properties.class, mapByRequest, null);
        //检查key是否被使用
        HashMap<String, Object> map = new HashMap<>();
        map.put("tableName","t_properties");
        map.put("fieldName","key_");
        map.put("fieldValue",properties.getKey());
        if(properties.getId()==null){
            map.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(key) ",null,null);

            int insert = this.insert(properties);
            if(insert>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",properties,null);
        }else{
            if(this.selectByPk(properties.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",properties.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(key) ",null,null);
            int i = this.updateByPk(properties);
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",properties,null);
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed",null,null);
    }

    @Override
    public ReturnModel updateForXL(Map<String, Object> mapByRequest) {
        PropertiesExample example = new PropertiesExample();
        if(!StringUtils.isEmpty(mapByRequest.get("app_login_img"))){
            Properties properties = new Properties();
            properties.setKey("app_login_img");
            properties.setValue(mapByRequest.get("app_login_img").toString());
            example.clear();
            example.createCriteria().andKeyEqualTo("app_login_img");
            propertiesMapper.updateByExampleSelective(properties,example);
        }
        if(!StringUtils.isEmpty(mapByRequest.get("app_logout_img"))){
            Properties properties = new Properties();
            properties.setKey("app_logout_img");
            properties.setValue(mapByRequest.get("app_logout_img").toString());
            example.clear();
            example.createCriteria().andKeyEqualTo("app_logout_img");
            propertiesMapper.updateByExampleSelective(properties,example);
        }
        if(!StringUtils.isEmpty(mapByRequest.get("update_frequency"))){
            Properties properties = new Properties();
            properties.setKey("update_frequency");
            properties.setValue(mapByRequest.get("update_frequency").toString());
            example.clear();
            example.createCriteria().andKeyEqualTo("update_frequency");
            propertiesMapper.updateByExampleSelective(properties,example);
        }
        if(!StringUtils.isEmpty(mapByRequest.get("current_version"))){
            Properties properties = new Properties();
            properties.setKey("current_version");
            properties.setValue(mapByRequest.get("current_version").toString());
            example.clear();
            example.createCriteria().andKeyEqualTo("current_version");
            propertiesMapper.updateByExampleSelective(properties,example);
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }

    @Override
    public ReturnModel feedbackTypes(Map<String, Object> mapByRequest) {
        String string =SysParam.get("feedback_types_kv");
        string = string ==null ? FEEDBACK_TYPES_KV :string;
        Map map = JsonUtils.jsonToPojo(string, Map.class);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"get success",map,null);
    }

    @Override
    public void set_GLOBAL_group_concat_max_len() {
        propertiesMapper.set_GLOBAL_group_concat_max_len();
    }

    @Override
    public ReturnModel messageTypes(Map<String, Object> mapByRequest) {
        String string =SysParam.get("message_types_kv");
        string = string ==null ? MESSAGE_TYPES_KV :string;
        Map map = JsonUtils.jsonToPojo(string, Map.class);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"get success",map,null);
    }
}
