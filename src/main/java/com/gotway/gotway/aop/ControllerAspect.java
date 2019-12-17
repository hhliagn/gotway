package com.gotway.gotway.aop;

import com.gotway.gotway.common.exception.Exception500;
import com.gotway.gotway.common.pojo.ReturnModel;
import com.gotway.gotway.common.util.JsonUtils;
import com.gotway.gotway.common.util.LogHelper;
import com.gotway.gotway.common.util.RequestUtil;
import com.gotway.gotway.mapper.PointRecordMapper;
import com.gotway.gotway.mapper.UserMapper;
import com.gotway.gotway.pojo.PointRecord;
import com.gotway.gotway.pojo.User;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * controller切面类 用于提取通用的异常处理代码
 */

@Aspect
@Component
@Order(100)
public class ControllerAspect {
	public static Logger log = Logger.getLogger(ControllerAspect.class);

	@Pointcut("execution(* com.gotway.gotway.controller.*.*(..))")
	private void pointCutMethod() {
	}
	// 切用户注册方法
	@Around("pointCutMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		// 业务方法执行 
		Object result;
		ReturnModel re = null;
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		long time = System.currentTimeMillis();
		try {
			Object[] args = pjp.getArgs();
			HttpServletRequest request = (HttpServletRequest)args[0];
			Map<String, Object> mapByRequest = RequestUtil.getMapByRequest(request);

			sb.append("url:").append(request.getRequestURL().toString());
			LogHelper.debug(sb.toString());
			sb2.append(sb.toString());
			sb.delete(0,sb.length());
			sb.append("参数：").append(JsonUtils.objectToJson(mapByRequest));
			LogHelper.debug(sb.toString());
			result = pjp.proceed();
			sb.delete(0,sb.length());
			sb.append("返回：").append(JsonUtils.objectToJson(result));
			LogHelper.debug(sb.toString());
		} catch (Exception500 e) {
			e.printStackTrace();
			sb.delete(0,sb.length());
			sb.append("异常：").append(e.toString());
			LogHelper.debug(sb.toString());
			re = new ReturnModel(ReturnModel.CODE_500, e.toString());
			return re.getMap();
		} catch (Exception e) {
			e.printStackTrace();
			sb.delete(0,sb.length());
			sb.append("异常：").append(e.toString());
			LogHelper.debug(sb.toString());
			re = new ReturnModel(ReturnModel.CODE_600,e instanceof IllegalArgumentException ? e.getMessage() : e.toString());
			return re.getMap();
		}
		log.debug(sb2.append(" -- time:(ms)" ).append((System.currentTimeMillis()-time)).toString() );
		sb = null;re = null;//垃圾回收
		return result;
	}
	

	
}