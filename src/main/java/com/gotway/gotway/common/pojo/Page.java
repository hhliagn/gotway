package com.gotway.gotway.common.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page <T> {
	private Integer num;// 第几页
	private Integer size;//每页显示的记录数，即页大小
	private Integer total;//总记录数
	
	private List<?> items;// 返回数据列表
	private String search;//单个关键字查询
	private T param;//多查询条件时使用
	private String [] ss;//用来接收前段传多个值的情况
	private Map<String,Object> pd = new HashMap<String,Object>();


	public Integer getNum() {
		if(this.num==null ||this.num<1)return 1;//默认1
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getSize() {
		if(this.size==null ||this.size<1)return 1;//默认1
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public String[] getSs() {
		return ss;
	}

	public void setSs(String[] ss) {
		this.ss = ss;
	}

	public Map<String, Object> getPd() {
		return pd;
	}

	public void setPd(Map<String, Object> pd) {
		this.pd = pd;
	}
}
