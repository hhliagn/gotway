package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;

import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.RoleMapper;
import com.gotway.gotway.mapper.UserMenuMapper;
import com.gotway.gotway.mapper.UserRoleMapper;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.pojo.vo.RoleVo;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.RoleService;

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
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMenuMapper userMenuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private FieldService fieldServcie;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        Role role = Map2Bean.getInstance().getBean(Role.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        RoleExample example = new RoleExample();
        RoleExample.Criteria c = example.createCriteria();

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Role> roles = roleMapper.selectByExample(example);
        ArrayList<RoleVo> roleVos = new ArrayList<>();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            Role next = iterator.next();
            RoleVo bean = Map2Bean.getInstance().getBean(RoleVo.class, next);

            UserRoleExample ex = new UserRoleExample();
            ex.createCriteria().andRoleIdEqualTo(next.getId());
            List<UserRole> userRoles = userRoleMapper.selectByExample(ex);
            bean.setUserCount(userRoles.size());
            roleVos.add(bean);
        }
        Long total = page.getTotal();

        if(roles!=null && roles.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",roleVos,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",roleVos,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
            @ValidateField(index = 0, notNull = true, key = "name", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = false, key = "remark", minLen = 1,maxLen=255),
            @ValidateField(index = 0, notNull = false, key = "userIds", minLen = 1,maxLen=255),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Role role = Map2Bean.getInstance().getBean(Role.class, mapByRequest, null);
        role.setCreateTime(new Date());
        //检查Name是否被使用
        HashMap<String, Object> map = new HashMap<>();
        map.put("tableName","t_role");
        map.put("fieldName","name");
        map.put("fieldValue",role.getName());
        if(role.getId()==null){
            map.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");
            int insert = this.insert(role);
            if(insert>0){
                saveUserRoles(mapByRequest, role); //保存用户角色关系
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",role,null);
            }
        }else{
            if(this.selectByPk(role.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",role.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");
            int i = this.updateByPk(role);
            if(i>0){
                saveUserRoles(mapByRequest, role); //保存用户角色关系
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",role,null);
            }
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed");
    }

    private void saveUserRoles(Map<String, Object> mapByRequest, Role role) {
        //保存用户角色关系
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andRoleIdEqualTo(role.getId());
        userRoleMapper.deleteByExample(example);
        Object userIdsObj = mapByRequest.get("userIds");
        String userIds = userIdsObj==null ? "" :userIdsObj.toString();
        String[] uIds = userIds.split(",");
        for (String uid:uIds) {
            if(!StringUtils.isEmpty(uid)){
                UserRole userRole = new UserRole();
                userRole.setCreateTime(new Date());
                userRole.setRoleId(role.getId());
                userRole.setUserId(Integer.valueOf(uid));
                userRoleMapper.insert(userRole);
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
            //删除用户角色
            UserRoleExample exa = new UserRoleExample();
            exa.createCriteria().andRoleIdEqualTo(id);
            userRoleMapper.deleteByExample(exa);
            //删除角色权限
            UserMenuExample example = new UserMenuExample();
            UserMenuExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(id);
            userMenuMapper.deleteByExample(example);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "roleId", minVal = 0),
            @ValidateField(index = 0, notNull = false, key = "menuIds", minLen = 1),
    })
    public ReturnModel auth(Map<String, Object> mapByRequest) throws Exception {
        Role role = roleMapper.selectByPrimaryKey(Integer.valueOf(mapByRequest.get("roleId").toString()));
        if(role==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,Role does not exist");

        UserMenuExample exam = new UserMenuExample();
        exam.createCriteria().andRoleIdEqualTo(role.getId());
        userMenuMapper.deleteByExample(exam);

        if(!StringUtils.isEmpty(mapByRequest.get("menuIds"))){
            String[] menuIds = mapByRequest.get("menuIds").toString().split(",");
            if(menuIds!=null && menuIds.length>0)for (String id :menuIds) {
                UserMenu userMenu = new UserMenu();
                userMenu.setCreateTime(new Date ());
                userMenu.setRoleId(role.getId());
                userMenu.setMenuId(Integer.valueOf(id));
                userMenuMapper.insert(userMenu);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }
}
