package com.SCM.dto;

import lombok.ToString;

@ToString
public class ExceptionDto {
	
	private String message;
	
	private String statuscode;
	
	private boolean status;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
