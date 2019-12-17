package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.FileLogMapper;
import com.gotway.gotway.pojo.FileLog;
import com.gotway.gotway.pojo.FileLogExample;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.FileLogService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FileLogServiceImpl extends BaseServiceImpl<FileLog> implements FileLogService {
    @Autowired
    private FileLogMapper fileLogMapper;
    @Autowired
    private FieldService fieldServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        FileLog fileLog = Map2Bean.getInstance().getBean(FileLog.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        FileLogExample example = new FileLogExample();
        FileLogExample.Criteria c = example.createCriteria();

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<FileLog> fileLogs = fileLogMapper.selectByExample(example);
        Long total = page.getTotal();

        if(fileLogs!=null && fileLogs.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",fileLogs,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",fileLogs,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
        @ValidateField(index = 0, notNull = false, key = "objectId", minVal=0),
        @ValidateField(index = 0, notNull = false, key = "objectType", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "picUrl", minLen = 1),

    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        FileLog fileLog = Map2Bean.getInstance().getBean(FileLog.class, mapByRequest, null);
        if(fileLog.getId()==null){
            int insert = this.insert(fileLog);
            if(insert>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",fileLog,null);
        }else{
            int i = this.updateByPk(fileLog);
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",fileLog,null);
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
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 1, notNull = true, minVal = 0),
            @ValidateField(index = 2, notNull = true, minVal = 0),
    })
    public void saveImg(Map<String, Object> mapByRequest, Integer  id,Integer type ) {
        if(mapByRequest.get("imgs")!=null){// 保存图片
            String[] imgs = mapByRequest.get("imgs").toString().split(",");
            for (String img:imgs) {
                FileLog record = new FileLog();
                FileLogExample example = new FileLogExample();
                example.createCriteria().andPicUrlEqualTo(img).andObjectIdIsNull();
                List<FileLog> fileLogs = fileLogMapper.selectByExample(example);
                if(fileLogs!=null && fileLogs.size()>0){
                    record = fileLogs.get(0);
                    record.setObjectType(type);//帖子 :1,用户头像:0 ,主题 ：2 ，反馈消息：3
                    record.setObjectId(id);
                    fileLogMapper.updateByPrimaryKey(record);
                }else{
                    record.setObjectType(type);//帖子 :1,用户头像:0 ,主题 ：2 ，反馈消息：3
                    record.setObjectId(id);
                    record.setPicUrl(img);
                    fileLogMapper.insert(record);
                }
            }
        }
    }
}
