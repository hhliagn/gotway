package com.gotway.gotway.aop;

import java.lang.reflect.Field;
import java.util.*;


import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.SysParam;
import com.gotway.gotway.mapper.*;
import com.gotway.gotway.pojo.*;
import com.gotway.gotway.pojo.bo.UserBo;
import com.gotway.gotway.service.TokenService;
import my.convert.Map2Bean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Aspect
@Component
@Order(101)
public class PointAndTopProduceAspect implements Observer{

    @Autowired
    private TopMapper topMapper;
    @Autowired
	private UserMapper userMapper;
    @Autowired
	private PointRecordMapper pointRecordMapper;
	@Autowired
	private PointAssistMapper pointAssistMapper;
	@Autowired
	private PraiseLogMapper praiseLogMapper;
	@Autowired
	private DrivingRecordMapper drivingRecordMapper;
	@Autowired
	private StickMapper stickMapper;
	@Autowired
	private TokenService tokenService;

	@Pointcut("execution(* com.gotway.gotway.service.impl.UserServiceImpl.edit2(..))")//修改个人信息
	private void pointCutEdit() {
	}
    @Pointcut("execution(* com.gotway.gotway.service.impl.UserServiceImpl.register(..))")//注册
    private void pointCutRegister() {
    }
	@Pointcut("execution(* com.gotway.gotway.service.impl.StickServiceImpl.addOrUpdate(..))")//发帖
	private void pointCutStick() {
	}
	@Pointcut("execution(* com.gotway.gotway.service.impl.PraiseLogServiceImpl.addOrUpdate(..))")//点赞
	private void pointCutPraise() {
	}
	@Pointcut("execution(* com.gotway.gotway.service.impl.CommentsLogServiceImpl.addOrUpdate(..))")//评论
	private void pointCutComment() {
	}
	@Pointcut("execution(* com.gotway.gotway.service.impl.DrivingRecordServiceImpl.addOrUpdate(..))")//骑行记录
	private void pointCutDrivingRecord() {
	}

	private Integer point_stick = SysParam.get("point_stick")==null ? 5 :Integer.valueOf(SysParam.get("point_stick"));
	private Integer point_praise = SysParam.get("point_praise")==null ? 1 :Integer.valueOf(SysParam.get("point_praise"));
	private Integer point_comment = SysParam.get("point_comment")==null ? 2 :Integer.valueOf(SysParam.get("point_comment"));
	private double point_meleage = SysParam.get("point_meleage")==null? 10000D: Double.valueOf(SysParam.get("point_meleage"));
	private Integer point_earn_praise = SysParam.get("point_earn_praise")==null ? 1 :Integer.valueOf(SysParam.get("point_earn_praise"));
	private Integer point_register = SysParam.get("point_register")==null ? 10 :Integer.valueOf(SysParam.get("point_register"));
	private Integer user_everyday_points = SysParam.get("user_everyday_points")==null ? 30 :Integer.valueOf(SysParam.get("user_everyday_points"));
	private Integer stick_max_points = SysParam.get("stick_max_points")==null ? 100 :Integer.valueOf(SysParam.get("stick_max_points@Override"));

	public void update(Observable o, Object arg) {
		point_stick = SysParam.get("point_stick")==null ? 5 :Integer.valueOf(SysParam.get("point_stick"));
		point_praise = SysParam.get("point_praise")==null ? 1 :Integer.valueOf(SysParam.get("point_praise"));
		point_comment = SysParam.get("point_comment")==null ? 2 :Integer.valueOf(SysParam.get("point_comment"));
		point_meleage = SysParam.get("point_meleage")==null? 10000D: Double.valueOf(SysParam.get("point_meleage"));
		point_earn_praise = SysParam.get("point_earn_praise")==null ? 1 :Integer.valueOf(SysParam.get("point_earn_praise"));
		point_register = SysParam.get("point_register")==null ? 10 :Integer.valueOf(SysParam.get("point_register"));
		user_everyday_points = SysParam.get("user_everyday_points")==null ? 30 :Integer.valueOf(SysParam.get("user_everyday_points"));
		stick_max_points = SysParam.get("stick_max_points")==null ? 100 :Integer.valueOf(SysParam.get("stick_max_points"));
		System.out.println("point_earn_praise:>>>>>>>>>>>>>>"+point_earn_praise);
	}

	// 切修改个人信息方法
	@Around("pointCutEdit()")
	public Object doAroundEdit(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Map<String,Object> param = (Map<String,Object>)args[args.length-1];
		User userInfo = tokenService.getUserInfo(param);
		Map2Bean m2b = Map2Bean.getInstance();
		User u = userMapper.selectByPrimaryKey(userInfo.getId());
		UserBo bean = m2b.getBean(UserBo.class, u);
		// 业务方法执行 
		Object result = pjp.proceed();
		ReturnModel re = (ReturnModel)result;
		if(re.getCode().equals(ReturnModel.CODE_SUCCESS)) {
			int i=0;
			User user = (User)re.getData();
			UserBo bean1 = Map2Bean.getInstance().getBean(UserBo.class, user);

			Field[] fs = UserBo.class.getDeclaredFields();
			for (Field f:fs) {
				f.setAccessible(true);
				if(StringUtils.isEmpty(f.get(bean)) && !StringUtils.isEmpty(f.get(bean1))) i++;
			}

			if(i>0)this.savePoint(user.getId(),i*point_register,"Edit User Info");
		}
		//int i=1/0;事务测试
		return result;
	}
    // 切用户注册方法
    @Around("pointCutRegister()")
    public Object doAroundRegister(ProceedingJoinPoint pjp) throws Throwable {
        // 业务方法执行
        Object result = pjp.proceed();
        ReturnModel re = (ReturnModel)result;
        if(re.getCode().equals(ReturnModel.CODE_SUCCESS)) {
            User user = (User)re.getData();
            this.savePoint(user.getId(),20,"Register");
        }
        //int i=1/0;事务测试
        return result;
    }
	// 切发帖的方法
	@Around("pointCutStick()")
	public Object doAroundStick(ProceedingJoinPoint pjp) throws Throwable {
		// 业务方法执行
		Object result = pjp.proceed();
		ReturnModel re = (ReturnModel)result;
		if(re.getCode().equals(ReturnModel.CODE_SUCCESS)) {
			Object[] args = pjp.getArgs();
			Map<String,Object> param = (Map<String,Object>)args[args.length-1];
			User user = tokenService.getUserInfo(param);
			this.savePoint(user.getId(),point_stick,"Add Stick");
			this.stickToTop(user);//排行榜数据更新
		}
		return result;
	}


	// 切点赞的方法
	@Around("pointCutPraise()")
	public Object doAroundPraise(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Map<String,Object> param = (Map<String,Object>)args[args.length-1];
		User user = tokenService.getUserInfo(param);
		Integer stickId = Integer.valueOf(param.get("stickId").toString());
		PraiseLogExample example = new PraiseLogExample();
		example.createCriteria().andStickIdEqualTo(stickId).andUserIdEqualTo(user.getId());
		List<PraiseLog> praiseLogs = praiseLogMapper.selectByExample(example);
		// 业务方法执行
		Object result = pjp.proceed();
		ReturnModel re = (ReturnModel)result;
		if(re.getCode().equals(ReturnModel.CODE_SUCCESS)) {

			if(praiseLogs==null || praiseLogs.size()==0 ){//第一次点时才产生积分。点了，取消，再点 时不产生。
				this.praiseOrCommentAddPoint(user,point_praise);//点赞人加分
				this.earnPraiseAddPoint(stickId);//帖主获赞加分
			}
		}
		return result;
	}
	// 切评论的方法
	@Around("pointCutComment()")
	public Object doAroundComment(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Map<String,Object> param = (Map<String,Object>)args[args.length-1];
		User user = tokenService.getUserInfo(param);
		Integer stickId = Integer.valueOf(param.get("stickId").toString());

		// 业务方法执行
		Object result = pjp.proceed();
		ReturnModel re = (ReturnModel)result;
		if(re.getCode().equals(ReturnModel.CODE_SUCCESS)) {
			this.praiseOrCommentAddPoint(user,point_comment);//评论人加分
		}
		return result;
	}
	// 切骑行记录的方法
	@Around("pointCutDrivingRecord()")
	public Object doAroundDrivingRecord(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Map<String,Object> param = (Map<String,Object>)args[args.length-1];
		String userId = param.get("userId").toString();
		User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));
		Double mileageBefore = 0D;
		if(!StringUtils.isEmpty(param.get("id"))) {
			DrivingRecord d = drivingRecordMapper.selectByPrimaryKey(Integer.valueOf(param.get("id").toString()));
			mileageBefore = d.getMileage();
		}
		// 业务方法执行
		Object result = pjp.proceed();
		ReturnModel re = (ReturnModel)result;
		if(re.getCode().equals(ReturnModel.CODE_SUCCESS)) {
			DrivingRecord dr = (DrivingRecord) re.getData();
			this.drivingPoint(user, mileageBefore, dr);
			this.drivingTop(mileageBefore,dr);
		}
		return result;
	}

	private void drivingPoint(User user, Double mileageBefore, DrivingRecord dr) throws Exception {
		double v = dr.getMileage() - mileageBefore;

		PointAssistExample pointAssistExample = new PointAssistExample();
		pointAssistExample.createCriteria().andUserIdEqualTo(user.getId()).andDrivingRecordIdEqualTo(-1).andCreateDateEqualTo(new Date());
		List<PointAssist> pointAssists = pointAssistMapper.selectByExample(pointAssistExample);
		int point =0;
		if(pointAssists == null || pointAssists.size() == 0){
			PointAssist pointAssist = new PointAssist();
			pointAssist.setCreateDate(new Date());
			pointAssist.setUserId(user.getId());
			pointAssist.setDrivingRecordId(-1);
			pointAssist.setNum(Double.valueOf(v/point_meleage).intValue());//10公里加一分
			pointAssist.setMileage(v%point_meleage);
			point = pointAssist.getNum();
			pointAssistMapper.insertSelective(pointAssist);
		}else{
			PointAssist pointAssist = pointAssists.get(0);
			if(pointAssist.getMileage()==null)pointAssist.setMileage(0D);
			int now = Double.valueOf((v + pointAssist.getMileage())/point_meleage).intValue();//10公里加一分
			point = now ;
			pointAssist.setMileage((v + pointAssist.getMileage())% point_meleage);
			pointAssist.setNum(point + pointAssist.getNum());
			pointAssistMapper.updateByPrimaryKeySelective(pointAssist);
		}

		if(point>0)this.savePoint(user.getId(),point,"Driving");//骑行加分
	}

	private void drivingTop(Double mileageBefore, DrivingRecord dr) {
		double v = dr.getMileage() - mileageBefore;
		TopExample topExample = new TopExample();
		topExample.createCriteria().andMemIdEqualTo(dr.getUserId());
		List<Top> tops = topMapper.selectByExample(topExample);
		Top top = null;
		if(tops!=null && tops.size()>0){
			top = tops.get(0);
			top.setTodayMileage(top.getTodayMileage()==null?v:top.getTodayMileage()+ v);

			top.setSumMileage(top.getSumMileage()==null ?v :top.getSumMileage()+v);
			topMapper.updateByPrimaryKeySelective(top);
		}else{
			top = new Top();
			top.setMemId(dr.getUserId());
			top.setTodayMileage(v);
			top.setSumMileage(v);
			topMapper.insertSelective(top);
		}
		//维护用户总里程数
		if(top!=null){
			User user = userMapper.selectByPrimaryKey(top.getMemId());
			user.setMileageTotal(top.getSumMileage()==null ?v :top.getSumMileage());
			userMapper.updateByPrimaryKeySelective(user);
			tokenService.updateUserForToken(user);
		}
	}

	private void stickToTop(User user) {
		TopExample topExample = new TopExample();
		topExample.createCriteria().andMemIdEqualTo(user.getId());
		List<Top> tops = topMapper.selectByExample(topExample);
		if(tops!=null && tops.size()>0){
			Top top = tops.get(0);
			top.setTopicCount(top.getTopicCount()==null?1:top.getTopicCount()+1);
			topMapper.updateByPrimaryKeySelective(top);
		}else{
			Top top = new Top();
			top.setMemId(user.getId());
			top.setTopicCount(1);
			topMapper.insertSelective(top);
		}
	}

	private void earnPraiseAddPoint(Integer stickId) throws Exception {

		PointAssistExample pointAssistExample = new PointAssistExample();
		pointAssistExample.createCriteria().andStickIdEqualTo(stickId);
		List<PointAssist> pointAssists = pointAssistMapper.selectByExample(pointAssistExample);
		Stick stick = stickMapper.selectByPrimaryKey(stickId);
		boolean f = pointAssists==null || pointAssists.size()==0 || pointAssists.get(0).getNum()<stick_max_points;
		if(f){
			this.savePoint(stick.getUserId(),point_earn_praise,"Earn Praise");//帖主获赞加分
			if(pointAssists==null || pointAssists.size()==0){
				PointAssist pointAssist = new PointAssist();
				pointAssist.setStickId(stickId);
				pointAssist.setNum(point_earn_praise);
				pointAssistMapper.insertSelective(pointAssist);
			}else{
				PointAssist pointAssist = pointAssists.get(0);
				pointAssist.setNum(pointAssist.getNum()+point_earn_praise);
				pointAssistMapper.updateByPrimaryKeySelective(pointAssist);
			}
		}
		this.praiseTop(stick);//排行榜处理
	}

	private void praiseTop(Stick stick) {
		TopExample topExample = new TopExample();
		topExample.createCriteria().andMemIdEqualTo(stick.getUserId());
		List<Top> tops = topMapper.selectByExample(topExample);
		if(tops!=null && tops.size()>0){
			Top top = tops.get(0);
			top.setSupportCount(top.getSupportCount()==null?1:top.getSupportCount()+1);
			topMapper.updateByPrimaryKeySelective(top);
		}else{
			Top top = new Top();
			top.setMemId(stick.getUserId());
			top.setSupportCount(1);
			topMapper.insertSelective(top);
		}
	}

	private void praiseOrCommentAddPoint(User user,int point) throws Exception {
		PointAssistExample pointAssistExample = new PointAssistExample();
		pointAssistExample.createCriteria().andUserIdEqualTo(user.getId()).andDrivingRecordIdIsNull().andCreateDateEqualTo(new Date());
		List<PointAssist> pointAssists = pointAssistMapper.selectByExample(pointAssistExample);
		boolean f = pointAssists==null || pointAssists.size()==0 || pointAssists.get(0).getNum()<user_everyday_points;
		if(f){
			this.savePoint(user.getId(),point,"Praise");//点赞人加分
			if(pointAssists==null || pointAssists.size()==0){
				PointAssist pointAssist = new PointAssist();
				pointAssist.setUserId(user.getId());
				pointAssist.setNum(point);
				pointAssist.setCreateDate(new Date());
				pointAssistMapper.insertSelective(pointAssist);
			}else{
				PointAssist pointAssist = pointAssists.get(0);
				pointAssist.setNum(pointAssist.getNum()+point);
				pointAssistMapper.updateByPrimaryKeySelective(pointAssist);
			}
		}

	}

	//生成 用户积分流水记录
	private void savePoint(Integer memid,Integer point,String source) throws Exception {
		Map<String,Object> info=null;
		PointRecord pointRecord = new PointRecord();
		pointRecord.setCreateDate(new Date());
		pointRecord.setMemId(memid);
		pointRecord.setPoint(point);
		pointRecord.setSource(source);
		pointRecordMapper.insert(pointRecord);
		User user = userMapper.selectByPrimaryKey(memid);
		int qty = user.getIntegralQty() != null ? user.getIntegralQty() : 0;
		int level = user.getIntegralLevel();
		user.setIntegralQty(qty+point);
		user.setIntegralLevel((user.getIntegralQty()/100)>0?(this.calculateLevel(user.getIntegralQty())):0);
		userMapper.updateByPrimaryKeySelective(user);
		tokenService.updateUserForToken(user);

		if(level != this.calculateLevel(user.getIntegralQty())){//当等级有变化
			TopExample topExample = new TopExample();
			topExample.createCriteria().andMemIdEqualTo(user.getId());
			List<Top> tops = topMapper.selectByExample(topExample);
			if(tops!=null && tops.size()>0){
				Top top = tops.get(0);
				top.setLevel(user.getIntegralLevel());
				topMapper.updateByPrimaryKeySelective(top);
			}else{
				Top top = new Top();
				top.setMemId(user.getId());
				top.setLevel(user.getIntegralLevel());
				topMapper.insertSelective(top);
			}
		}

    }
    private int calculateLevel(int qty) {
		int t = qty/100;
		if(t==0) return 0;
		Double z = Math.log(t)/Math.log(2);
		return z.intValue()+1;
	}

}