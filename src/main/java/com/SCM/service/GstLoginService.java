package com.SCM.service;

import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.GstLoginDto;

public interface GstLoginService{

	public GstLoginDto saveToken(GstLoginDto gstUser);
	
	public GstLoginDto getGstUserLoginByGstin(String gstin);
	

	
	public void deleteGstUserLoginByGstin(String gstin);

	EwayBillAuthResponseDto getEwayBillByGstin(String auth_token);
}

