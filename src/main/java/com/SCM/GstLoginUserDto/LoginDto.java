package com.SCM.GstLoginUserDto;

public class LoginDto {
private String UserName;
	
	private String Password;
	
	private String AppKey;
	
	private boolean ForceRefreshAccessToken;

	public LoginDto(String userName, String password, String appKey, boolean forceRefreshAccessToken) {
		super();
		UserName = userName;
		Password = password;
		AppKey = appKey;
		ForceRefreshAccessToken = forceRefreshAccessToken;
	}

	
	
	public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getAppKey() {
		return AppKey;
	}

	public void setAppKey(String appKey) {
		AppKey = appKey;
	}

	public boolean isForceRefreshAccessToken() {
		return ForceRefreshAccessToken;
	}

	public void setForceRefreshAccessToken(boolean forceRefreshAccessToken) {
		ForceRefreshAccessToken = forceRefreshAccessToken;
	}
	
	
}
