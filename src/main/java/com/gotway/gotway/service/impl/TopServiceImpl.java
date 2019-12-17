package com.gotway.gotway.service.impl;


import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.mapper.TopMapper;
import com.gotway.gotway.pojo.Top;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.pojo.vo.TopVo;
import com.gotway.gotway.service.TokenService;
import com.gotway.gotway.service.TopService;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TopServiceImpl extends BaseServiceImpl<Top> implements TopService {
    @Autowired
    private TopMapper topMapper;
    @Autowired
    private TokenService tokenServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
        @ValidateField(index = 0, notNull = true, key = "type", strVals = {"day","month","week","praise","level","stick","sum"}),
        @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
        @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        User user = tokenServcie.getUserInfo(mapByRequest);
        mapByRequest.put("userId",user.getId());
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        TopVo self = null;
        List<TopVo> returnList = new ArrayList<TopVo>();

        HashMap<String, Object> map = new HashMap<>();
        String type = mapByRequest.get("type").toString();
        String topString = tokenServcie.get(type);
        String selfString = tokenServcie.get("self:"+type+":"+user.getId());
        List<TopVo> list = null;

        if(StringUtils.isEmpty(topString)){//如果为空，则查数据库
            list = topMapper.selectForTop(mapByRequest);
            tokenServcie.set(type,JsonUtils.objectToJson(list),1800);
        }else{
            list = JsonUtils.jsonToList(topString,TopVo.class);
        }
        if(StringUtils.isEmpty(selfString)){//如果为空，则查数据库
            self = topMapper.selectForSelf(mapByRequest);
            tokenServcie.set("self:"+type+":"+user.getId(),JsonUtils.objectToJson(self),1800);
        }else{
            self = JsonUtils.jsonToPojo(selfString,TopVo.class);
        }

        if(list!=null && list.size()>0){
            int start = (p.getNum()-1)*p.getSize();
            int end = start +p.getSize();
            int total = list.size();
            if(start<total){
                if(end>=total)end=total;
                for(int i=start;i<end;i++){
                    returnList.add(list.get(i));
                }
            }
        }
       if(self!=null)for (TopVo s : list) {//如果个人的数据在排行榜中，刚取榜中数据
            if(s.getUser().getId().equals(self.getUser().getId())){
                self = s ;
                break;
            }
        }
        if(self==null)self = new TopVo();
        self.setUser(user);
        map.put("self",self);
        map.put("tops",returnList);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",map,null);
    }

    /**
     * 当用户修改资料的时候刷新缓存
     * @param mapByRequest
     * @return
     * @throws Exception
     */
    @Override
    @ValidateField(index = 0, notNull = true, key = "userId", minVal = 1 )
    public ReturnModel refresh(Map<String, Object> mapByRequest) throws Exception {
        String [] types = {"day","month","week","praise","level","stick","sum"};
        String userId = mapByRequest.get("userId").toString();
        for (String type:types) {
            String topString = tokenServcie.get(type);
            String selfString = tokenServcie.get("self:"+type+":"+userId);
            //清除缓存的排行榜信息
            if(!StringUtils.isEmpty(topString)){
                tokenServcie.delete(type);
            }
            //清除缓存的个人排行信息
            if(!StringUtils.isEmpty(selfString)){
                tokenServcie.delete("self:"+type+":"+userId);
            }
        }

        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }


}
