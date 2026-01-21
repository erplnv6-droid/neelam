package com.SCM.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;

import com.SCM.GstLoginUserDto.CancelEinvoiceDto;
import com.SCM.GstLoginUserDto.EinvoiceCancelDto;
import com.SCM.GstLoginUserDto.EinvoiceResponseDto;
import com.SCM.GstLoginUserDto.EwayBillByIrnDto;
import com.SCM.GstLoginUserDto.EwayBillCancelDto;
import com.SCM.GstLoginUserDto.EwayBillCancelResponseDto;
import com.SCM.GstLoginUserDto.EwayBillLoginDto;
import com.SCM.GstLoginUserDto.GetEinvoiceDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
import com.SCM.dto.SalesDto;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface EinvoiceService {
	
	public GstLoginDto userLogin() throws Exception;
	
	 public String getGSTINDetails(String gstin,GstLoginDto gstLoginDto) throws JSONException, Exception;
	 
	  public EinvoiceResponseDto getEncryptedData(SalesDto einvoiceDto)throws  Exception;

	  public CancelEinvoiceDto cancelInvoice(EinvoiceCancelDto cancelDto) throws JsonProcessingException, JSONException, Exception;
	  
	  public EwayBillByIrnDto EwayBillGenerate(EwayBillLoginDto billLoginDto) throws JsonProcessingException, JSONException, Exception;
	  
	  public GetEinvoiceDto getEinvoiceByIrn(String irn) throws JSONException, Exception;
	  
	  public EwayBillCancelResponseDto cancelEwayBill(EwayBillCancelDto ewayBillCancelDto) throws JSONException, IOException, Exception;
	  
	  public String getEwayBillByIrn(String irn);
	  
	  
	  
	  

}


