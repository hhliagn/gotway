package com.gotway.gotway.service.impl;

import com.github.pagehelper.PageHelper;
import com.gotway.gotway.common.pojo.Page;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.*;
import com.gotway.gotway.mapper.PropertiesMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.mapper.UserRoleMapper;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.pojo.UserExample;
import com.gotway.gotway.pojo.UserRole;
import com.gotway.gotway.pojo.UserRoleExample;
import com.gotway.gotway.pojo.vo.UserVo;
import com.gotway.gotway.service.*;

import com.zf.ann.ValidateField;
import com.zf.ann.ValidateGroup;
import my.convert.Map2Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PropertiesMapper propertiesMapper;
    @Autowired
    private FieldService fieldServcie;
    @Autowired
    private FileLogService fileLogServcie;
    @Autowired
    private TokenService tokenService;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "email",  minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = true, key = "password", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "code", minVal = 1),
    })
    public ReturnModel register(Map<String, Object> mapByRequest) throws Exception {
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);

        //校验code
        String code = tokenService.get(VERIFYCODE + ":" + mapByRequest.get("email").toString());
        if(!mapByRequest.get("code").equals(code))
            return new ReturnModel(ReturnModel.CODE_FAILD,"register failed,verification code mismatch",null,null);

        HashMap<String, Object> map = new HashMap<>();
        //检查email是否被使用
        map.put("tableName","t_user");
        map.put("fieldName","email");
        map.put("fieldValue",user.getEmail());
        map.put("id",-1);

        ReturnModel repeated = fieldServcie.is_repeated(map);
        if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(email) ",null,null);

        user.setIntegralLevel(0);
        user.setIntegralQty(0);
        user.setMileageTotal(0d);
        user.setState(1);//状态[1:正常2:停用]
        user.setUserType(2);//用户类型[1：管理员 2:会员]
        user.setCreateTime(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));//再一次加密
        user.setEmailTag(1);
        user.setLocationTag(1);
        user.setPhoneTag(1);
        int insert = this.insert(user);
        user.setPassword(null);
        if(insert>0){
            if(user.getHeadImg()!=null) {
                mapByRequest.put("imgs", user.getHeadImg());
                fileLogServcie.saveImg(mapByRequest, user.getId(), 0);
            }
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"register success",user,null);
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"register failed",null,null);
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "account", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = false, key = "nickname", minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = false, key = "realName", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = false, key = "birthday", regStr = "^\\d{4}-\\d{2}-\\d{2}$" ),
            @ValidateField(index = 0, notNull = false, key = "phone",  minLen = 6,maxLen=20),
            @ValidateField(index = 0, notNull = false, key = "email",  minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = false, key = "gender", strVals={"1","2","3"}),
            @ValidateField(index = 0, notNull = false, key = "area", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "num", minVal = 1),
            @ValidateField(index = 0, notNull = false, key = "size", minVal = 1),
            @ValidateField(index = 0, notNull = true, key = "userType", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "createTime_start", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
            @ValidateField(index = 0, notNull = false, key = "createTime_end", regStr = "^\\d{4}-\\d{2}-\\d{2}$"),
    })
    public ReturnModel getList(Map<String, Object> mapByRequest) throws Exception {
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        Page p = Map2Bean.getInstance().getBean(Page.class, mapByRequest, null);
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        if(!StringUtils.isEmpty(user.getNickname()))c.andNicknameLike("%"+user.getNickname()+"%");
        if(!StringUtils.isEmpty(user.getAccount()))c.andAccountLike("%"+user.getAccount()+"%");
        if(!StringUtils.isEmpty(user.getRealName()))c.andRealNameLike("%"+user.getRealName()+"%");
        if(!StringUtils.isEmpty(user.getArea()))c.andAreaLike("%"+user.getArea()+"%");
        if(!StringUtils.isEmpty(user.getState()))c.andStateEqualTo(user.getState());
        if(mapByRequest.get("createTime_start")!=null)c.andCreateTimeGreaterThanOrEqualTo(df.parse(mapByRequest.get("createTime_start").toString()+" 00:00:00"));
        if(mapByRequest.get("createTime_end")!=null)c.andCreateTimeLessThanOrEqualTo(df.parse(mapByRequest.get("createTime_end").toString()+" 23:59:59"));

        c.andUserTypeEqualTo(user.getUserType());
        //排序处理
        String orderByClause = MybatisQueryHelper.getOrderByClause(mapByRequest);
        if(!StringUtils.isEmpty(orderByClause))example.setOrderByClause(orderByClause);

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(p.getNum(), p.getSize());
        List<User> users = null;
        if(Integer.valueOf(2).equals(user.getUserType())){
            users = userMapper.selectByExample2(example);
        }else{
            users = userMapper.selectUserWithRoleName(example);
        }

        Long total = page.getTotal();

        if(users!=null && users.size()>0){
            users.stream().forEach(s->s.setPassword(null));
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"query success",users,total.intValue());
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"no data",users,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "id", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "account", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "nickname", minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = false, key = "realName", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = false, key = "birthday", regStr = "^\\d{4}-\\d{2}-\\d{2}$" ),
            @ValidateField(index = 0, notNull = false, key = "phone",  minLen = 6,maxLen=20),
            @ValidateField(index = 0, notNull = true, key = "email",  minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = true, key = "gender", strVals={"1","2","3"}),
            @ValidateField(index = 0, notNull = false, key = "area", minLen = 1),
            @ValidateField(index = 0, notNull = true, key = "state", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = true, key = "password", minLen = 1,maxLen=64),
    })
    public ReturnModel addOrUpdate(Map<String, Object> mapByRequest) throws Exception {
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        //检查用户名是否被使用 email是否被使用
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));//再一次加密
        map.put("tableName","t_user");
        map.put("fieldName","account");
        map.put("fieldValue",user.getAccount());
        map2.put("tableName","t_user");
        map2.put("fieldName","email");
        map2.put("fieldValue",user.getEmail());
        if(user.getId()==null){
            map.put("id",-1);
            map2.put("id",-1);
            ReturnModel repeated = fieldServcie.is_repeated(map);
            ReturnModel repeated2 = fieldServcie.is_repeated(map2);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(account) ");
            if(repeated2.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(email) ");
            user.setIntegralLevel(0);
            user.setIntegralQty(0);
            user.setMileageTotal(0d);
            user.setUserType(1);//用户类型[1：管理员 2:会员]
            user.setRoleTag(2);//角色标记[1：管理员2：操作员]
            user.setCreateTime(new Date());
            int insert = this.insert(user);
            user.setPassword(null);
            if(insert>0){
                if(user.getHeadImg()!=null) {
                    mapByRequest.put("imgs", user.getHeadImg());
                    fileLogServcie.saveImg(mapByRequest, user.getId(), 0);
                }
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"add success",user,null);
            }
        }else{
            if(this.selectByPk(user.getId())==null)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,data does not exist");
            map.put("id",user.getId());
            map2.put("id",user.getId());
            ReturnModel repeated = fieldServcie.is_repeated(map);
            ReturnModel repeated2 = fieldServcie.is_repeated(map2);
            if(repeated.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(account) ");
            if(repeated2.getCode()==400)return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed,The field values are duplicated .(email) ");
            int i = this.updateByPk(user);
            user.setPassword(null);
            if(i>0){
                if(user.getHeadImg()!=null) {
                    mapByRequest.put("imgs", user.getHeadImg());
                    fileLogServcie.saveImg(mapByRequest, user.getId(), 0);
                }
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",user,null);
            }
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed",null,null);
    }
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "email", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "password",  minLen = 1 ),
            @ValidateField(index = 0, notNull = false, key = "systemModel",  minLen = 0 ),
    })
    public ReturnModel login(Map<String, Object> mapByRequest) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        //帐号密码比对。
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        String systemModel = user.getSystemModel();
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andUserTypeEqualTo(2);//用户类型[1：管理员 2:会员]
        c.andEmailEqualTo(user.getEmail());
        c.andStateEqualTo(1);//启用状态
        c.andPasswordEqualTo(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        List<User> users = userMapper.selectByExample(example);
        if(users!=null && users.size()>0){
            user = users.get(0);
            if(!StringUtils.isEmpty(systemModel)){//系统型号的处理
                user.setSystemModel(systemModel);
                userMapper.updateByPrimaryKeySelective(user);
                tokenService.updateUserForToken(user);
            }
            //token对应的用户信息 处理
            String token = tokenService.setUserAndToken(user);
            //返回token。
            user.setPassword(null);
            map.put("user",user);
            map.put("token",token);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"login success",map,null);
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"login faild",null,null);
    }
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "account", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "password",  minLen = 1 ),
    })
    public ReturnModel backLogin(Map<String, Object> mapByRequest) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        //帐号密码比对。
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andUserTypeEqualTo(1);//用户类型[1：管理员 2:会员]
        c.andAccountEqualTo(user.getAccount());
        c.andStateEqualTo(1);//启用状态
        c.andPasswordEqualTo(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        List<User> users = userMapper.selectByExample(example);
        if(users!=null && users.size()>0){
            user = users.get(0);
            //token对应的用户信息 处理
            String token = tokenService.setUserAndToken(user);
            //返回token。
            user.setPassword(null);
            map.put("user",user);
            map.put("token",token);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"login success",map,null);
        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"login faild",null,null);
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "token", minLen = 1,maxLen=64),
    })
    public ReturnModel logout(Map<String, Object> mapByRequest) {
        String token = mapByRequest.get("token").toString();
        tokenService.deleteUserToken(token);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"logout success");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "id", minLen = 1,maxLen=64),
    })
    public ReturnModel del(Map<String, Object> mapByRequest) throws Exception {
        Integer id = Integer.valueOf(mapByRequest.get("id").toString());
        User user = userMapper.selectByPrimaryKey(id);
        if("admin".equals(user.getAccount()))
            return new ReturnModel(ReturnModel.CODE_FAILD,"The admin account cannot be deleted");
        int i =userMapper.checkUserId(id);
        if(i==0){
            int ii = this.deleteByPk(id);
            if(ii>0){
                return new ReturnModel(ReturnModel.CODE_SUCCESS,"delete success");
            }else return new ReturnModel(ReturnModel.CODE_FAILD,"delete failed");

        }
        return new ReturnModel(ReturnModel.CODE_FAILD,"There is a data association and deletion is not allowed");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "roleId", minLen = 1,maxLen=64),
    })
    public ReturnModel getAdminUser(Map<String, Object> mapByRequest) throws Exception{
        Map2Bean m2b = Map2Bean.getInstance();
        ArrayList<UserVo> notConfigured = new ArrayList<>();
        ArrayList<UserVo> configured = new ArrayList<>();

        List<UserRole> userRoles = null;
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria c = example.createCriteria();
        if(mapByRequest.get("roleId")!=null){
            c.andRoleIdEqualTo(Integer.valueOf(mapByRequest.get("roleId").toString()));
            //指定roleId的用户角色
            userRoles = userRoleMapper.selectByExample(example);
        }

        //所有用户角色
        example.clear();
        List<UserRole> allUserRoles = userRoleMapper.selectByExample(example);
        UserExample example2 = new UserExample();
        UserExample.Criteria criteria = example2.createCriteria();
        criteria.andUserTypeEqualTo(1);//后台用户
        criteria.andAccountNotEqualTo("admin");
        //所有用户
        List<User> users = userMapper.selectByExample(example2);

        if(userRoles!=null) for (User user:users) {
            for (UserRole ur:userRoles) {
                if(user.getId().equals(ur.getUserId())){
                    UserVo bean = m2b.getBean(UserVo.class, user);
                    bean.setChecked(1);
                    configured.add(bean);
                }
            }
        }
         for (User  user: users) {
            boolean flag = false;
             if(allUserRoles!=null)for (UserRole ur :allUserRoles) {
                if(user.getId().equals(ur.getUserId())){
                    flag = true;
                    break;
                }
            }
            if(flag)continue;
            configured.add(m2b.getBean(UserVo.class,user));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("configured",configured);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"select success",map,null);
    }

    @Value("${VERIFYCODE}")
    private String VERIFYCODE;
    @Value("${VERIFYCODE_SECOND}")
    private Integer VERIFYCODE_SECOND;

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "email", minLen = 1,maxLen=64),
    })
    public ReturnModel getVerifyCode(Map<String, Object> mapByRequest) {
//        UserExample example = new UserExample();
//        example.createCriteria().andEmailEqualTo();
//        List<User> users = userMapper.selectByExample(example);
//        if(users==null || users.size()==0)
//            return new ReturnModel(ReturnModel.CODE_FAILD,"get faild,the account does not exist");
//        User user = users.get(0);
        String email = mapByRequest.get("email").toString();
        String code = SendMail.getFourRandom();
        //发送邮件
        try {
            SendMail.sendMail(email,code);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(ReturnModel.CODE_FAILD,"get faild,send email faild");
        }
        tokenService.set(VERIFYCODE+":"+email,code,VERIFYCODE_SECOND);

        return new ReturnModel(ReturnModel.CODE_SUCCESS,"get success");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "email", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "password", minLen = 1,maxLen=64),
            @ValidateField(index = 0, notNull = true, key = "code", minLen = 1,maxLen=64),
    })
    public ReturnModel resetPassword(Map<String, Object> mapByRequest) {
        //校验code
        String code = tokenService.get(VERIFYCODE + ":" + mapByRequest.get("email").toString());
        if(!mapByRequest.get("code").toString().equals(code)){
            return new ReturnModel(ReturnModel.CODE_FAILD,"operate faild,verification failed.");
        }
        //重置密码
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(mapByRequest.get("email").toString());
        List<User> users = userMapper.selectByExample(example);
        if(users==null || users.size()==0)
            return new ReturnModel(ReturnModel.CODE_FAILD,"operate faild,the account does not exist");
        User user = users.get(0);
        user.setPassword(DigestUtils.md5DigestAsHex(mapByRequest.get("password").toString().getBytes()));
        userMapper.updateByPrimaryKeySelective(user);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "ids", minLen = 1),
            @ValidateField(index = 0, notNull = true, key = "state", strVals = {"1","2"}),
    })
    public ReturnModel updateState(Map<String, Object> mapByRequest) {//状态[1:正常2:停用]
        String[] ids = mapByRequest.get("ids").toString().split(",");
        if(ids!=null && ids.length>0){
            for (String id:ids) {
                User user = new User();
                user.setId(Integer.valueOf(id));
                user.setState(Integer.valueOf(mapByRequest.get("state").toString()));
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "longitude", minVal = 0),
            @ValidateField(index = 0, notNull = false, key = "latitude", minVal = 0),
            @ValidateField(index = 0, notNull = false, key = "num", minVal = 1),
            @ValidateField(index = 0, notNull = false, key = "size", minVal = 1),
            @ValidateField(index = 0, notNull = false, key = "distance", minVal = 0),
    })
    public ReturnModel nearby(Map<String, Object> mapByRequest) throws Exception {
        User userInfo = tokenService.getUserInfo(mapByRequest);
        if(!StringUtils.isEmpty(mapByRequest.get("longitude")))userInfo.setLongitude(Double.valueOf(mapByRequest.get("longitude").toString()));
        else mapByRequest.put("longitude",userInfo.getLongitude());
        if(!StringUtils.isEmpty(mapByRequest.get("latitude")))userInfo.setLatitude(Double.valueOf(mapByRequest.get("latitude").toString()));
        else mapByRequest.put("latitude",userInfo.getLatitude());
        userMapper.updateByPrimaryKeySelective(userInfo);//更新经纬度
        tokenService.updateUserForToken(userInfo);

        mapByRequest.put("id", userInfo.getId());
        Double distance = SysParam.get("distance")==null ? 500000D :Double.valueOf(SysParam.get("distance"));//单位米
        if(StringUtils.isEmpty(mapByRequest.get("distance")))mapByRequest.put("distance",distance);


        Integer num = mapByRequest.get("num")==null?1:Integer.valueOf(mapByRequest.get("num").toString());
        Integer size = mapByRequest.get("size")==null?3:Integer.valueOf(mapByRequest.get("size").toString());
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(num, size);
        List<User> users = userMapper.selectNearBy(mapByRequest);
        Long total = page.getTotal();
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"get success",users,total.intValue());
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = true, key = "longitude"),
            @ValidateField(index = 0, notNull = true, key = "latitude"),
            @ValidateField(index = 0, notNull = true, key = "id", minVal = 0),
    })
    public ReturnModel updateLocation(Map<String, Object> mapByRequest) throws Exception {
        User bean = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        User userInfo = null;
        if(bean.getId()==null){
            userInfo = tokenService.getUserInfo(mapByRequest);
        }else{
            userInfo = userMapper.selectByPrimaryKey(bean.getId());
            if(userInfo==null)new ReturnModel(ReturnModel.CODE_FAILD,"operate faild,the account does not exist");
        }
        userInfo.setLongitude(bean.getLongitude());
        userInfo.setLatitude(bean.getLatitude());

        userMapper.updateByPrimaryKeySelective(userInfo);
        String token = tokenService.updateUserForToken(userInfo);
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
    }

    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "nickname", minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = false, key = "birthday", regStr = "^\\d{4}-\\d{2}-\\d{2}$" ),
            @ValidateField(index = 0, notNull = false, key = "phone",  minLen = 6,maxLen=20),
            @ValidateField(index = 0, notNull = false, key = "gender", strVals={"1","2","3"}),
            @ValidateField(index = 0, notNull = false, key = "area", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "individualitySign", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "weight", minVal = 0),
            @ValidateField(index = 0, notNull = false, key = "emailTag", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "phoneTag", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "locationTag", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "headImg", minLen = 1),

    })
    public ReturnModel edit(Map<String, Object> mapByRequest) throws Exception {
        User userInfo = tokenService.getUserInfo(mapByRequest);
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        user.setId(userInfo.getId());

        int i = this.updateByPk(user);
        if(i>0){
            if(user.getHeadImg()!=null) {
                mapByRequest.put("imgs", user.getHeadImg());
                fileLogServcie.saveImg(mapByRequest, user.getId(), 0);
            }
            user = userMapper.selectByPrimaryKey(user.getId());
            tokenService.updateUserForToken(user);//刷新用户信息
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",user,null);
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed",null,null);
    }

    /**
     * 修改个人资料，清除排行榜缓存
     */
    @Autowired
    private TopService topService;
    @Override
    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "nickname", minLen = 1,maxLen=64 ),
            @ValidateField(index = 0, notNull = false, key = "birthday", regStr = "^\\d{4}-\\d{2}-\\d{2}$" ),
            @ValidateField(index = 0, notNull = false, key = "phone",  minLen = 6,maxLen=20),
            @ValidateField(index = 0, notNull = false, key = "gender", strVals={"1","2","3"}),
            @ValidateField(index = 0, notNull = false, key = "area", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "individualitySign", minLen = 1),
            @ValidateField(index = 0, notNull = false, key = "weight", minVal = 0),
            @ValidateField(index = 0, notNull = false, key = "emailTag", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "phoneTag", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "locationTag", strVals={"1","2"}),
            @ValidateField(index = 0, notNull = false, key = "headImg", minLen = 1),

    })
    public ReturnModel edit2(Map<String, Object> mapByRequest) throws Exception {
        User userInfo = tokenService.getUserInfo(mapByRequest);
        userInfo = userMapper.selectByPrimaryKey(userInfo.getId());
        User user = Map2Bean.getInstance().getBean(User.class, mapByRequest, null);
        user.setId(userInfo.getId());

        userInfo.setNickname(user.getNickname());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setPhone(user.getPhone());
        userInfo.setGender(user.getGender());
        userInfo.setArea(user.getArea());
        userInfo.setIndividualitySign(user.getIndividualitySign());
        userInfo.setWeight(user.getWeight());
        userInfo.setHeadImg(user.getHeadImg());

        int i = userMapper.updateByPrimaryKey(userInfo);
        if(i>0){
            if(user.getHeadImg()!=null) {
                mapByRequest.put("imgs", user.getHeadImg());
                fileLogServcie.saveImg(mapByRequest, user.getId(), 0);
            }
            user = userMapper.selectByPrimaryKey(user.getId());
            tokenService.updateUserForToken(user);//刷新用户信息
            mapByRequest.put("userId",user.getId());
            topService.refresh(mapByRequest);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"update success",user,null);
        }

        return new ReturnModel(ReturnModel.CODE_FAILD,"operate failed",null,null);
    }

    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "password", minLen = 1,maxLen = 64),
            @ValidateField(index = 0, notNull = false, key = "newPassword", minLen = 1,maxLen = 64),
    })
    public ReturnModel updatePassword(Map<String, Object> mapByRequest) {
        User user = tokenService.getUserInfo(mapByRequest);
        user = userMapper.selectByPrimaryKey(user.getId());
        if(DigestUtils.md5DigestAsHex(mapByRequest.get("password").toString().getBytes()).equals(user.getPassword())){
            user.setPassword(DigestUtils.md5DigestAsHex(mapByRequest.get("newPassword").toString().getBytes()));
            userMapper.updateByPrimaryKeySelective(user);
            return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success");
        }
        else{
            return new ReturnModel(ReturnModel.CODE_FAILD,"password mismatch");
        }
    }

    @ValidateGroup( { @ValidateField(index = 0, notNull = true),
            @ValidateField(index = 0, notNull = false, key = "token", minLen = 1,maxLen = 64),
    })
    public ReturnModel getSelfInfo(Map<String, Object> mapByRequest) {
        User user = tokenService.getUserInfo(mapByRequest);
        user = userMapper.selectByPrimaryKey(user.getId());
        tokenService.updateUserForToken(user);//更新缓存
        return new ReturnModel(ReturnModel.CODE_SUCCESS,"operate success",user,null);
    }

}
