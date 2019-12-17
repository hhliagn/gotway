package com.gotway.gotway.common.util;

import org.apache.log4j.Logger;

public class LogHelper {
	public static Logger log = Logger.getLogger(LogHelper.class);
	/**
	 * 
	* @Title: getName 
	* @Description: 获取当前类名和方法名
	* @return
	* @return: String
	* @date 2018年8月1日 上午9:10:02   @author xuanhu
	 */
	private static String getName() {
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if(stackTrace!=null && stackTrace.length>0) {
			StackTraceElement stackTraceElement = stackTrace[4];
			String className = stackTraceElement.getClassName();
			String methodName = stackTraceElement.getMethodName();
			sb.append(className).append("-").append(methodName);
		}
		return sb.toString();
	}
	/**
	 * 
	* @Title: debug 
	* @Description: debug级别
	* @param content
	* @return: void
	* @date 2018年8月1日 上午9:22:17   @author xuanhu
	 */
	public static void debug(String content) {
		log.debug(jointContent(content).toString());
	}
	/**
	 * 
	* @Title: info 
	* @Description: info级别 
	* @param content
	* @return: void
	* @date 2018年8月1日 上午9:22:30   @author xuanhu
	 */
	public static void info(String content) {
		log.info(jointContent(content).toString());
	}
	/**
	 * 
	* @Title: error 
	* @Description: error级别  
	* @param content
	* @return: void
	* @date 2018年8月1日 上午9:22:51   @author xuanhu
	 */
	public static void error(String content) {
		log.error(jointContent(content).toString());
	}
	private static StringBuilder jointContent(String content) {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()).append(">> ").append(content);
		return sb;
	}
}
