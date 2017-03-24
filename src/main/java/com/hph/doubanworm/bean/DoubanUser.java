package com.hph.doubanworm.bean;

import java.util.Date;

public class DoubanUser {
	
	private int id;
	private String name;
	private String content;
	private Date create_time;
	private String href;
	private String basic_info;
	private String address;
	
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getBasic_info() {
		return basic_info;
	}
	public void setBasic_info(String basic_info) {
		this.basic_info = basic_info;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
	
	

}
