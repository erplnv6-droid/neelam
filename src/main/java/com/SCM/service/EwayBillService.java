package com.SCM.service;


import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;

import com.SCM.GstLoginUserDto.CancelEwayBillPartB;
import com.SCM.GstLoginUserDto.CancelEwayBillPartBResponse;
import com.SCM.GstLoginUserDto.EwayBillAuthDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillExtendValidityDto;
import com.SCM.GstLoginUserDto.EwayBillGenerateResDto;
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.EwayBillUpdatePartBDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EwayBillService {

	public EwayBillAuthResponseDto ewayAuthentication() throws Exception;
	
	public EwayBillGenerateResDto generateEwayPartB(EwayBillPartBGenerateDto partBGenerateDto,int joId) throws JsonProcessingException, JSONException, org.json.JSONException, Exception;
	
	public CancelEwayBillPartBResponse cancelEwayBill(CancelEwayBillPartB cancelEwayBillPartB,int joId) throws JsonProcessingException;
	
	public String getTransportNo(String transinNo)throws JSONException;
	
	public String updatePartB(EwayBillUpdatePartBDto billUpdatePartBDto) throws JsonProcessingException;
	
	public String extendValidityEway(EwayBillExtendValidityDto billExtendValidityDto) throws JsonProcessingException;
	
	
}
