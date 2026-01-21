package com.SCM.GstLoginUserDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class EwayBillAuthResponseDto {
	
	private long id;

	private String authToken;
	
	private String encryptedSek;
	
	private String decryptedSek;
	
	private String gstin;

	public EwayBillAuthResponseDto(long id, String authToken, String encryptedSek, String decryptedSek, String gstin) {
		super();
		this.id = id;
		this.authToken = authToken;
		this.encryptedSek = encryptedSek;
		this.decryptedSek = decryptedSek;
		this.gstin = gstin;
	}

	public EwayBillAuthResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}



	
	
	
	
}
