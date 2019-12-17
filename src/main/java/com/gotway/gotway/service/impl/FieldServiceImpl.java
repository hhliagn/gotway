package com.gotway.gotway.service.impl;

import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.FieldService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class FieldServiceImpl extends BaseServiceImpl<User> implements FieldService {
    @Autowired
    private UserMapper userMapper;


    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "tableName", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "fieldName",  minLen = 1 ),
            @ValidateField(index = 0, notNull = true, key = "fieldValue",  minLen = 0 ),
            @ValidateField(index = 0, notNull = true, key = "id",  minVal = -1),
    })
    public ReturnModel is_repeated(Map<String, Object> mapByRequest) throws Exception {
        String tableName = mapByRequest.get("tableName").toString();
        String fieldName = mapByRequest.get("fieldName").toString();
        String fieldValue = mapByRequest.get("fieldValue").toString();
        String id = mapByRequest.get("id").toString();
        String sql = "select count(0) from "+tableName+" where "+fieldName +" ='"+fieldValue+"' ";
        if(!"-1".equals(id))sql+= " and id <> '"+id+"'";

        int i = userMapper.checkRepeated( sql);
        if(i>0)return new ReturnModel(ReturnModel.CODE_FAILD,"有重复",null,null);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"没有重复",null,null);
    }
}
