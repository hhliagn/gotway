package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.MybatisQueryHelper;
import com.gotway.gotway.mapper.MenuMapper;
import com.gotway.gotway.mapper.UserMenuMapper;
import com.gotway.gotway.mapper.UserRoleMapper;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.pojo.vo.MenuP;
import com.gotway.gotway.pojo.vo.MenuVo;
import com.gotway.gotway.service.FieldService;
import com.gotway.gotway.service.MenuService;
import com.gotway.gotway.service.TokenService;

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
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMenuMapper userMenuMapper;
    @Autowired
    private FieldService fieldServcie;
    @Autowired
    private TokenService tokenService;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "name", minVal = 1 ),
            @ValidateField(index = 0, notNull = false, key = "sort", minLen = 1 ),
            @ValidateField(index = 0, notNull = true, key = "num", minVal = 1 ),
            @ValidateField(index = 0, notNull = true, key = "size", minVal = 1),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception{
        Menu menu = Map2Bean.getInstance().getBean(Menu.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        MenuExample example = new MenuExample();
        MenuExample.Criteria c = example.createCriteria();
        if(menu.getName()!=null)c.andNameLike("%"+menu.getName()+"%");

        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<Menu> menus = menuMapper.selectByExample(example);
        Long total = page.getTotal();

        if(menus!=null && menus.size()>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",menus,total.intValue());
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",menus,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "id", minVal = 0),
            @ValidateField(index = 0, notNull = true, key = "name", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "url", minLen = 1,maxLen=255),
            @ValidateField(index = 0, notNull = true, key = "state", minVal = 1),
            @ValidateField(index = 0, notNull = true, key = "sort", minVal=0),
            @ValidateField(index = 0, notNull = false, key = "parentId", minVal=0),
            @ValidateField(index = 0, notNull = true, key = "type", strVals = {"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "icon", minLen = 1,maxLen=255),
            @ValidateField(index = 0, notNull = true, key = "typeTag", strVals = {"1","2"}),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        Menu menu = Map2Bean.getInstance().getBean(Menu.class, mapByRequest, null);

        //检查Name是否被使用
        HashMap<String, Object> map = new HashMap<>();
        map.put("tableName","t_menu");
        map.put("fieldName","name_");
        map.put("fieldValue",menu.getName());
        if(menu.getId()==null){
            map.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");

            int insert = this.insert(menu);
            if(insert>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",menu,null);
        }else{
            if(this.selectByPk(menu.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",menu.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(name) ");
            int i = this.updateByPk(menu);
            if(i>0)return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",menu,null);
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
            //删除角色权限
            UserMenuExample example = new UserMenuExample();
            UserMenuExample.Criteria criteria = example.createCriteria();
            criteria.andMenuIdEqualTo(id);
            userMenuMapper.deleteByExample(example);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "roleId", minVal = 0),
    })
    public ReturnModel getRoleMenu(Map<String, Object> mapByRequest) throws Exception {
        Map2Bean m2b = Map2Bean.getInstance();
        ArrayList<MenuVo> menuVos = new ArrayList<>();
        //先查出所有菜单
        MenuExample example = new MenuExample();
        example.setOrderByClause("sort asc");
        List<Menu> menus = menuMapper.selectByExample(example);

        //根据roleid，查权限
        List<UserMenu> userMenus = null;
        if(mapByRequest.get("roleId")!=null){
            UserMenuExample example1 = new UserMenuExample();
            example1.createCriteria().andRoleIdEqualTo(Integer.valueOf(mapByRequest.get("roleId").toString()));
            userMenus = userMenuMapper.selectByExample(example1);
        }
        //将所有权限中，roleid对应的权限 标记一下checked
        for (Menu menu : menus) {
            MenuVo bean = m2b.getBean(MenuVo.class, menu);
            menuVos.add(bean);
            if(userMenus!=null)for (UserMenu um : userMenus) {
                if(menu.getId().equals(um.getMenuId())) {
                    bean.setChecked(MenuVo.CHECKED_TRUE);
                    break;
                }
            }
        }
        ArrayList<MenuP> handle = this.handle(menuVos);

        return new ReturnModel(ReturnModel.CODE_SUCCESS,"select success",handle,null);
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "token", minLen = 1),
    })
    public ReturnModel getUserMenu(Map<String, Object> mapByRequest) throws Exception {
        //通过token，拿用户id
        User user = null;
        user =  tokenService.getUserInfo(mapByRequest);
        if(user==null)return new ReturnModel(ReturnModel.CODE_FAILD,"select faild,token expire");

        ArrayList<MenuP> handle =new ArrayList<>();
        //查出所有菜单
        MenuExample example = new MenuExample();
        example.setOrderByClause("sort asc");
        List<Menu> menus = menuMapper.selectByExample(example);
        if("admin".equals(user.getAccount())){
            handle = this.handle(menus);
        }else {
            UserRoleExample exap = new UserRoleExample();
            exap.createCriteria().andUserIdEqualTo(user.getId());
            List<UserRole> userRoles = userRoleMapper.selectByExample(exap);
            UserRole ur = null;
            if(userRoles!=null && userRoles.size()>0)ur = userRoles.get(0);
            if(ur==null) return new ReturnModel(ReturnModel.CODE_FAILD,"select faild,the user did not specify a role");

            UserMenuExample example1 = new UserMenuExample();
            example1.createCriteria().andRoleIdEqualTo(Integer.valueOf(ur.getRoleId()));
            List<UserMenu> userMenus = userMenuMapper.selectByExample(example1);

            if(userMenus!=null && userMenus.size()>0)for (Menu menu : menus) {
                for (UserMenu um:userMenus) {
                    if(menu.getId().equals(um.getMenuId()))handle.add(menu);
                }
            }
            handle = this.handle(handle);
        }

        return new ReturnModel(ReturnModel.CODE_SUCCESS,"select success",handle,null);
    }

    private ArrayList<MenuP> handle(List<? extends MenuP> menus) {
        //层级关系的处理
        ArrayList<MenuP> newItems = new ArrayList<MenuP> ();
        HashMap<Integer,MenuP> maper = new HashMap<Integer,MenuP>();//将每一项以id为键 存在maper中
        //将id,跟记录映射起来
        for (int j=0,lenth=menus.size();j<lenth;j++) {
            maper.put(menus.get(j).getId(),menus.get(j));
        }
        //将顶级菜单放在newItems里面
        for (int i=0,len=menus.size();i<len;i++) {
            if(StringUtils.isEmpty(menus.get(i).getParentId()))newItems.add(menus.get(i));
        }

        for (int i=0,len=menus.size();i<len;i++) {
            Integer parentid = StringUtils.isEmpty(menus.get(i).getParentId())?null:menus.get(i).getParentId();
            if(parentid==null)continue;
            MenuP m = maper.get(parentid);
            if(m==null) continue;
            if(m.getChild()==null){
                m.setChild(new ArrayList<MenuP> ());
            }
            ArrayList<MenuP> list = (ArrayList<MenuP>)m.getChild();
            list.add(menus.get(i));
        }
        return newItems;
    }
}
