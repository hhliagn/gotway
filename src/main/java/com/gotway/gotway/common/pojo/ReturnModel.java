package com.gotway.gotway.common.pojo;

import java.util.HashMap;
import java.util.Map;
/**
 * 返回模型
 * @author xuanhu  2018-4-3
 *
 */
public class ReturnModel {

	public static Integer CODE_SUCCESS=200;//成功
	public static Integer CODE_FAILD=400;//失败
	public static Integer CODE_500=500;//自定义异常,提示给用户
	public static Integer CODE_600=600;//其它异常,提示给开发人员
	
	private Integer code;
	private String info;
	private Object data;
	private Integer recordNum;//总记录数
	private Map<String,Object> map=new HashMap<String,Object>();
	
	public ReturnModel() {
		super();
	}

	
	public ReturnModel(Integer code, String info) {
		super();
		this.code = code;
		this.info = info;
	}


	public ReturnModel(Integer code, String info, Object data, Integer recordNum) {
		super();
		this.code = code;
		this.info = info;
		this.data = data;
		this.recordNum = recordNum;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(Integer recordNum) {
		this.recordNum = recordNum;
	}
	public Map<String, Object> getMap() {
		if(code!=null) {
			map.put("code", code);
		}
		if(info!=null) {
			map.put("info", info);
		}
		if(data!=null) {
			map.put("data", data);
		}
		if(recordNum!=null) {
			map.put("recordNum", recordNum);
		}
		return map;
	}
	

	
}
