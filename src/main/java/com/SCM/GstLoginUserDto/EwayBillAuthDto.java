package com.SCM.GstLoginUserDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class EwayBillAuthDto {


	private String action;
	
	private String username;
	
	private String password;
	
	private String app_key;

	public EwayBillAuthDto(String action, String username, String password, String app_key) {
		super();
		this.action = action;
		this.username = username;
		this.password = password;
		this.app_key = app_key;
	}

	public EwayBillAuthDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	
	
	
}
