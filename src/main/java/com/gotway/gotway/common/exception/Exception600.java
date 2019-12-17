package com.gotway.gotway.common.exception;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 自定义异常
 * @author xuanhu
 *
 */
public class Exception600 extends RuntimeException {
	private Map<String,Object> map;
	public Exception600() {
		super();
	}

	public Exception600(String message) {
		super(message);
	}
	public Exception600(Map<String,Object> obj) {
		this.map = obj;
	}

	@Override
	public String toString() {
		if(map==null)return this.getMessage();
		else {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			StringBuilder sb =new StringBuilder();
			for (Entry<String, Object> entry : entrySet) {
				sb.append(entry.getValue());
				sb.append(",");
			}
			return sb.substring(0, sb.length()-1);
		}
	}

}
