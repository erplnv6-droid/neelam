package com.SCM.GstLoginUserDto;

public class GstLoginDto {

	private long id;

	private String clientId;

	private String userName;

	private String authToken;

	private String encryptedSek;

	private String decryptedSek;

	private String tokenExpiry;

	private String gstin;

	public GstLoginDto(long id, String clientId, String userName, String authToken, String encryptedSek,
			String decryptedSek, String tokenExpiry, String gstin) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.userName = userName;
		this.authToken = authToken;
		this.encryptedSek = encryptedSek;
		this.decryptedSek = decryptedSek;
		this.tokenExpiry = tokenExpiry;
		this.gstin = gstin;
	}

	
	public GstLoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getEncryptedSek() {
		return encryptedSek;
	}

	public void setEncryptedSek(String encryptedSek) {
		this.encryptedSek = encryptedSek;
	}

	public String getDecryptedSek() {
		return decryptedSek;
	}

	public void setDecryptedSek(String decryptedSek) {
		this.decryptedSek = decryptedSek;
	}

	public String getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(String tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}



	
	

	

}
