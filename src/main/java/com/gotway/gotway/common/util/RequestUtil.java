package com.gotway.gotway.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;


/**
 * Request工具类
 * 
 * @author xuanhu
 * 
 */
public class RequestUtil {


	/**
	 * 获取request对象中所有参数，并设置到map中
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 将request的请求参数封装成map（包括URL传和通过form表单提交的参数）
	 * @throws IOException 
	 */
	public static Map<String, Object> getMapByRequest(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enu = request.getParameterNames();
		if(request.getAttribute("token")!=null) map.put("token",request.getAttribute("token"));
		
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			String paraValue = request.getParameter(paraName);
			String[] parameterValues = request.getParameterValues(paraName);
			if(parameterValues!=null && parameterValues.length>1) {
				map.put(paraName,parameterValues);
				continue;
			}
			if (paraValue != null && !"".equals(paraValue) && !StringUtils.isEmpty(paraValue)) {
				if("start".equals(paraName)&& !StringUtils.isEmpty(paraValue) || "length".equals(paraName)&& !StringUtils.isEmpty(paraValue)) {
					map.put(paraName, Integer.valueOf(paraValue));
				}else {
					map.put(paraName, paraValue.trim());
				}
			}
		}
		return map;
	}

	/**
	 * 异步请求返回
	 * 
	 * @param encoding
	 *            编码格式
	 * @param data
	 *            data
	 * @param response
	 *            HttpServletResponse
	 */
	public static void responseOut(String encoding, String data,
			HttpServletResponse response) {
		response.setContentType("text/html; charset=" + encoding);
		try {
			PrintWriter pw = response.getWriter();
			pw.print(data);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
}
