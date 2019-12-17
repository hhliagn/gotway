package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.DrivingRecordMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.DrivingRecord;
import com.gotway.gotway.pojo.DrivingRecordExample;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.pojo.UserExample;
import com.gotway.gotway.service.DrivingRecordService;
import com.gotway.gotway.service.FieldService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class DrivingRecordServiceImpl extends BaseServiceImpl<DrivingRecord> implements DrivingRecordService {
    @Autowired
    private DrivingRecordMapper drivingRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FieldService fieldServcie;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "deviceName", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "nickname", minLen = 1 ),
        @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
        @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
        @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        DrivingRecord drivingRecord = Map2Bean.getInstance().getBean(DrivingRecord.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        DrivingRecordExample example = new DrivingRecordExample();
        DrivingRecordExample.Criteria c = example.createCriteria();
        if(drivingRecord.getDeviceName()!=null)c.andDeviceNameLike("%"+drivingRecord.getDeviceName()+"%");

        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));
        if(!StringUtils.isEmpty(mapByRequest.get("nickname"))){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andNicknameLike("%"+mapByRequest.get("nickname").toString()+"%");
            List<User> users = userMapper.selectByExample(userExample);
            if(users!=null && users.size()>0){
                List<Integer> ids = new ArrayList<>();
                users.stream().forEach( e ->ids.add(e.getId()));
                c.andUserIdIn(ids);
            }else c.andUserIdEqualTo(-1);//nickname匹配不到，让它查出的数据为空
        }
        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<DrivingRecord> drivingRecords = drivingRecordMapper.selectByExample(example);
        Long total = page.getTotal();

        if(drivingRecords!=null && drivingRecords.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",drivingRecords,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",drivingRecords,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "userId", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "deviceName", minLen = 1,maxLen=255),
        @ValidateField(index = 0, notNull = true, key = "deviceMac", minLen = 1),
        @ValidateField(index = 0, notNull = true, key = "mileage", minVal=0),
        @ValidateField(index = 0, notNull = false, key = "duration", minVal=0),
        @ValidateField(index = 0, notNull = true, key = "electricity", minVal = 0),
        @ValidateField(index = 0, notNull = false, key = "highestSpeed", minVal = 0),
        @ValidateField(index = 0, notNull = true, key = "aveSpeed", minVal = 0),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        DrivingRecord drivingRecord = Map2Bean.getInstance().getBean(DrivingRecord.class, mapByRequest, null);

        if(drivingRecord.getId()==null){
            drivingRecord.setCreateTime(new Date());
            int insert = this.insert(drivingRecord);
            if(insert>0){
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",drivingRecord,null);
            }
        }else{
            int i = this.updateByPk(drivingRecord);
            if(i>0){
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",drivingRecord,null);
            }
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
     }



    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
       @ValidateField(index = 0, notNull = true, key = "ids", minLen = 0),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        String[] ids = mapByRequest.get("ids").toString().split(",");
        if(ids!=null && ids.length>0){
            for (String id:ids) {
                Integer id2 = Integer.valueOf(id);
                int i = this.deleteByPk(id2);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
    }
}
