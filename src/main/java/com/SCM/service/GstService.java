package com.SCM.service;

import java.io.IOException;

import org.json.JSONException;

import com.SCM.GstLoginUserDto.CancelEinvoiceDto;
import com.SCM.GstLoginUserDto.CancelEinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.CancelEwayBillPartBResponse;
import com.SCM.GstLoginUserDto.CancelPurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceCancelDto;
import com.SCM.GstLoginUserDto.EinvoicePurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceResponseDto;
import com.SCM.GstLoginUserDto.EinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillByIrnDto;
import com.SCM.GstLoginUserDto.EwayBillCancelDto;
import com.SCM.GstLoginUserDto.EwayBillCancelResponseDto;
import com.SCM.GstLoginUserDto.EwayBillGenerateResDto;
import com.SCM.GstLoginUserDto.EwayBillLoginDto;
import com.SCM.GstLoginUserDto.GetEinvoiceDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
import com.SCM.GstModel.EinvoiceData;
import com.SCM.GstModel.GstUserLogin;
import com.SCM.dto.SalesDto;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface GstService {

	public GstLoginDto saveGstLoginUsetr(GstLoginDto gstLoginDto);
	
	public EinvoiceResponseDto saveEinvoiceData(EinvoiceResponseDto einvoiceResponseDto, int salesId);
	
	public EwayBillAuthResponseDto saveEwayResponse(EwayBillAuthResponseDto billAuthResponseDto);
	
//	public EwayBillGenerateResDto saveEwayBillResponse(EwayBillGenerateResDto billGenerateResDto);
	
public EwayBillByIrnDto saveEwayBillByIrn(EwayBillByIrnDto billByIrnDto,int salesId);

public GetEinvoiceDto getEinvoice(GetEinvoiceDto einvoiceDto,int salesId);

public CancelEinvoiceDto cancelEinvoice(CancelEinvoiceDto cancelEinvoiceDto,int salesId);


public EwayBillGenerateResDto generatePartB(EwayBillGenerateResDto billGenerateResDto,int salesId);



public CancelEwayBillPartBResponse billPartBResponse(CancelEwayBillPartBResponse billPartBResponse,int joId);

public EinvoiceSalesReturnDto saveEinvoiceSalesReturn(EinvoiceSalesReturnDto einvoiceSalesReturnDto,int salesReturnId);

public EinvoicePurchaseReturnDto saveEinvoicePurchaseReturn(EinvoicePurchaseReturnDto einvoicePurchaseReturnDto,int purchaseReturnId);
	
public EwayBillCancelResponseDto cancelEwayBill(EwayBillCancelResponseDto billCancelDto,int salesId);	
//	public EinvoiceData saveData(EinvoiceData einvoiceData);
	
public CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturn(CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturnDto, int salesReturnId);
	
public CancelPurchaseReturnDto cancelEinvoicePurchaseReturn(CancelPurchaseReturnDto cancelPurchaseReturnDto, int purchaseReturnId);
//	public String getGSTINDetails(GstLoginDto newgstLoginDto, String gstin) throws JSONException;
	
public void deleteGstin();
}
