package com.gotway.gotway.aop;

import com.gotway.gotway.common.interpreter.*;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.mapper.SysLogMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.LanguagePack;
import com.gotway.gotway.pojo.SysLog;
import com.gotway.gotway.pojo.User;
import com.gotway.gotway.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


@Aspect
@Component
@Order(100)
public class SysLogAspect {


	@Autowired
	private TokenService tokenService;
	@Autowired
	private SysLogMapper sysLogMapper;
	@Autowired
	private ThemeExpression themeExpression;
	@Autowired
	private CommentsLogExpression commentsLogExpression;
	@Autowired
	private LanguageExpression languageExpression;
	@Autowired
	private MessageExpression messageExpression;
	@Autowired
	private PropertiesExpression propertiesExpression;
	@Autowired
	private RoleExpression roleExpression;
	@Autowired
	private StickExpression stickExpression;
	@Autowired
	private UserExpression userExpression;
	//@Autowired
	//private RoleExpression roleExpression;

	private Map<String,AbstractExpression> expressions = new HashMap<String,AbstractExpression>();
	@PostConstruct
	private void init()
	{
		expressions.put("Theme",themeExpression);
		expressions.put("CommentsLog",commentsLogExpression);
		expressions.put("Language",languageExpression);
		expressions.put("Message",messageExpression);
		expressions.put("Properties",propertiesExpression);
		expressions.put("Role",roleExpression);
		expressions.put("Stick",stickExpression);
		expressions.put("User",userExpression);
	}

	@Pointcut("(execution(* com.gotway.gotway.service.impl.ThemeServiceImpl.*(java.util.Map<String,Object>)) " +
			"|| execution(* com.gotway.gotway.service.impl.CommentsLogServiceImpl.*(java.util.Map<String,Object>))" +
			"|| execution(* com.gotway.gotway.service.impl.LanguageServiceImpl.*(java.util.Map<String,Object>))" +
			"|| execution(* com.gotway.gotway.service.impl.MessageServiceImpl.*(java.util.Map<String,Object>))" +
			"|| execution(* com.gotway.gotway.service.impl.PropertiesServiceImpl.*(java.util.Map<String,Object>))" +
			"|| execution(* com.gotway.gotway.service.impl.RoleServiceImpl.*(java.util.Map<String,Object>))" +
			"|| execution(* com.gotway.gotway.service.impl.StickServiceImpl.*(java.util.Map<String,Object>))" +
			"|| execution(* com.gotway.gotway.service.impl.UserServiceImpl.*(java.util.Map<String,Object>)) )" +
			"&& !execution(* com.gotway.gotway.service.impl.UserServiceImpl.*ogin(java.util.Map<String,Object>))" +
			"&& !execution(* com.gotway.gotway.service.impl.UserServiceImpl.register(java.util.Map<String,Object>))"+
			"&& !execution( * com.gotway.gotway.service.impl.*.*get*(..)) " +
			"&& !execution( * com.gotway.gotway.service.impl.UserServiceImpl.nearby(..))"+
			"&& !execution( * com.gotway.gotway.service.impl.UserServiceImpl.edit(..))"+
			"&& !execution( * com.gotway.gotway.service.impl.UserServiceImpl.updateLocation(..))"+
			"&& !execution( * com.gotway.gotway.service.impl.PropertiesServiceImpl.*Types(..))"
	)
	private void pointCut() {
	}
	// 切service方法
	@Around("pointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		SysLog sysLog = new SysLog();
		Object[] args = pjp.getArgs();
		String classname = pjp.getSignature().getDeclaringTypeName();
		String entityName= classname.substring(classname.lastIndexOf(".")+1).replace("Service", "")
				.replace("Impl","");
		String methodname = pjp.getSignature().getName();
		Map<String, Object> param = (Map<String, Object>) args[0];
		User userInfo = null;
		try {
				//拿到方法名，增删改操作才记录日志
			if(methodname.contains("add") || methodname.contains("del") || methodname.contains("update")
						||methodname.contains("auth")){
					userInfo = tokenService.getUserInfo(param);
				if(userInfo!=null && Integer.valueOf(1).equals(userInfo.getUserType())){
					sysLog.setCreateTime(new Date());
					sysLog.setTheme(methodname);
					sysLog.setObjectTag(entityName);
					sysLog.setUserId(userInfo.getId());
					if(expressions.get(entityName)!=null){
						AbstractExpression abstractExpression = expressions.get(entityName);
						abstractExpression.interpreter(userInfo,param,sysLog,methodname);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 业务方法执行
		Object result = pjp.proceed();

		//成功的操作才记录日志。
		try {
			if(((ReturnModel)result).getCode().equals(ReturnModel.CODE_SUCCESS)) {
				//拿到token,属于后台管理员用户的操作，才记录日志
				if(userInfo!=null && Integer.valueOf(1).equals(userInfo.getUserType())){
					//拿到方法名，增删改操作才记录日志
					if(methodname.contains("add") || methodname.contains("del") || methodname.contains("update")
							||methodname.contains("auth")){
						if(sysLog.getOperation()!=null)
						sysLogMapper.insertSelective(sysLog);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//int i=1/0;//事务测试
		return result;
	}

}