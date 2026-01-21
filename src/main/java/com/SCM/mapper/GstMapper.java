package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.GstModel.CancelEinvoice;
import com.SCM.GstModel.CancelEinvoiceSalesReturn;
import com.SCM.GstModel.CancelEwayBill;
import com.SCM.GstModel.CancelEwayResponse;
import com.SCM.GstModel.CancelPurchaseReturn;
import com.SCM.GstModel.EinvoiceData;
import com.SCM.GstModel.EinvoicePurchaseReturnData;
import com.SCM.GstModel.EinvoiceSalesReturnData;
import com.SCM.GstModel.EwayBillByIrn;
import com.SCM.GstModel.EwayBillData;
import com.SCM.GstModel.EwayBillResponse;
import com.SCM.GstModel.GetEinvoice;
import com.SCM.GstModel.GstUserLogin;
import com.SCM.dto.SalesDto;

import com.SCM.GstDto.TranDtls;
import com.SCM.GstLoginUserDto.CancelEinvoiceDto;
import com.SCM.GstLoginUserDto.CancelEinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.CancelEwayBillPartBResponse;
import com.SCM.GstLoginUserDto.CancelPurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoicePurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceResponseDto;
import com.SCM.GstLoginUserDto.EinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillByIrnDto;
import com.SCM.GstLoginUserDto.EwayBillCancelDto;
import com.SCM.GstLoginUserDto.EwayBillCancelResponseDto;
import com.SCM.GstLoginUserDto.EwayBillGenerateResDto;
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.GetEinvoiceDto;
import com.SCM.GstLoginUserDto.GstLoginDto;

//import com.SCM.GstService.EwayBillAuth;


import com.SCM.model.Sales;
import com.SCM.repository.SalesRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GstMapper {

	@Autowired
	private ModelMapper mapper;

@Autowired
ObjectMapper objectMappe;

@Autowired
private SalesRepo salesRepo;
	


	public GstUserLogin mapToGstLogindata(GstLoginDto gstLoginDto)
	{
		
		GstUserLogin gstUserLogin = mapper.map(gstLoginDto, GstUserLogin.class);
		
		return gstUserLogin;
		
	
		
	}
	
	public GstLoginDto mapToGstLoginDto(GstUserLogin gstUserLogin)
	{
		GstLoginDto gstLoginDto=mapper.map(gstUserLogin, GstLoginDto.class);
		
		return gstLoginDto;
	}
	
	
	public EinvoiceData mapToEinvoiceDataDto(EinvoiceResponseDto einvoiceResponseDto)
	{
		EinvoiceData einvoiceData=mapper.map(einvoiceResponseDto, EinvoiceData.class);
		
		return einvoiceData;
	}
	
	public EinvoiceResponseDto mapToEinvoiceData(EinvoiceData einvoiceData)
	{
		EinvoiceResponseDto einvoiceResponseDto=mapper.map(einvoiceData, EinvoiceResponseDto.class);
		
		return einvoiceResponseDto;
	}
	
	
	public EinvoiceSalesReturnData mapToEinvoiceSalesRDto(EinvoiceSalesReturnDto einvoiceSalesReturnDto)
	{
		EinvoiceSalesReturnData einvoiceSalesReturnData=mapper.map(einvoiceSalesReturnDto, EinvoiceSalesReturnData.class);
		
		return einvoiceSalesReturnData;
	}
	
	public EinvoiceSalesReturnDto mapToEinvoiceSalesR(EinvoiceSalesReturnData einvoiceSalesReturnData)
	{
		EinvoiceSalesReturnDto salesReturnDto=mapper.map(einvoiceSalesReturnData,EinvoiceSalesReturnDto.class);
		
	return salesReturnDto;
	
	}
	
	public EinvoicePurchaseReturnData mapToEinvoicePurchaseRDto(EinvoicePurchaseReturnDto einvoicePurchaseReturnDto)
	{
		EinvoicePurchaseReturnData einvoicePurchaseReturnData=mapper.map(einvoicePurchaseReturnDto,EinvoicePurchaseReturnData.class);
		
		return einvoicePurchaseReturnData;
	}
	
	
	public EinvoicePurchaseReturnDto mapToEinvoicePurchaseR(EinvoicePurchaseReturnData einvoicePurchaseReturnData)
	{
		EinvoicePurchaseReturnDto einvoicePurchaseReturnDto=mapper.map(einvoicePurchaseReturnData, EinvoicePurchaseReturnDto.class);
		
		return einvoicePurchaseReturnDto;
	}
	
	
	
	public EwayBillData mapToEwayresponseDto(EwayBillAuthResponseDto billAuthResponseDto)
	{
		EwayBillData ewayBillAuth=mapper.map(billAuthResponseDto, EwayBillData.class);
		
		return ewayBillAuth;
	}
	
	public EwayBillAuthResponseDto mapToEwayBill(EwayBillData ewayBillAuth)
	{
		EwayBillAuthResponseDto ewayBillAuthResponseDto=mapper.map(ewayBillAuth, EwayBillAuthResponseDto.class);
		
		return ewayBillAuthResponseDto;
	}
	
	public EwayBillResponse mapToEwayResponseDto(EwayBillGenerateResDto bGenerateDto)
	{
		EwayBillResponse billResponse=mapper.map(bGenerateDto, EwayBillResponse.class);
		
		return billResponse;
	}
	
	public EwayBillGenerateResDto mapToEwayBillResp(EwayBillResponse billResponse)
	{
		EwayBillGenerateResDto billPartBGenerateDto=mapper.map(billResponse, EwayBillGenerateResDto.class);
		
		return billPartBGenerateDto;
	}
	

	public EwayBillByIrn mapToEwayBillByIrnDto(EwayBillByIrnDto billByIrnDto)
	{
		EwayBillByIrn billByIrn=mapper.map(billByIrnDto, EwayBillByIrn.class);
		return billByIrn;
	}
	
	public EwayBillByIrnDto mapToEwayBillByIrn(EwayBillByIrn billByIrn)
	{
		EwayBillByIrnDto billByIrnDto=mapper.map(billByIrn, EwayBillByIrnDto.class);
		return billByIrnDto;
	}
	
	public GetEinvoice mapToGetEinvoiceDto(GetEinvoiceDto einvoiceDto)
	{
		GetEinvoice einvoice=mapper.map(einvoiceDto, GetEinvoice.class);
		return einvoice;
	}
	
	public GetEinvoiceDto mapToGetEinvoice(GetEinvoice getEinvoice)
	{
		GetEinvoiceDto einvoiceDto=mapper.map(getEinvoice, GetEinvoiceDto.class);
		
		return einvoiceDto;
	}
	
	
	public CancelEinvoice mapToCancelEinvoiceDto(CancelEinvoiceDto cancelEinvoiceDto)
	{
		CancelEinvoice cancelEinvoice=mapper.map(cancelEinvoiceDto, CancelEinvoice.class);
		
		return cancelEinvoice;
	}
	
	public CancelEinvoiceDto mapToCancelEinvoice(CancelEinvoice cancelEinvoice)
	{
		CancelEinvoiceDto cancelEinvoiceDto=mapper.map(cancelEinvoice, CancelEinvoiceDto.class);
		
		return cancelEinvoiceDto;
	}
	
	
	public CancelEinvoiceSalesReturn mapToCancelSalesReturnDto(CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturnDto)
	{
		CancelEinvoiceSalesReturn cancelEinvoiceSalesReturn=mapper.map(cancelEinvoiceSalesReturnDto,CancelEinvoiceSalesReturn.class);
		
		return cancelEinvoiceSalesReturn;
	}
	
	public CancelEinvoiceSalesReturnDto mapToCancelSalesReturn(CancelEinvoiceSalesReturn cancelEinvoiceSalesReturn)
	{
		CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturnDto=mapper.map(cancelEinvoiceSalesReturn, CancelEinvoiceSalesReturnDto.class);
		
		return cancelEinvoiceSalesReturnDto;
	}
	
	
	public CancelPurchaseReturn mapToCancelPurchaseReturnDto(CancelPurchaseReturnDto cancelPurchaseReturnDto)
	{
		CancelPurchaseReturn purchaseReturn=mapper.map(cancelPurchaseReturnDto,CancelPurchaseReturn.class);
		
		return purchaseReturn;
	}
	
	
	public CancelPurchaseReturnDto mapToCancelPurchaseReturn(CancelPurchaseReturn cancelPurchaseReturn)
	{
		CancelPurchaseReturnDto cancelPurchaseReturnDto=mapper.map(cancelPurchaseReturn, CancelPurchaseReturnDto.class);
		
		return cancelPurchaseReturnDto;
	}

	
	
	
	public CancelEwayBill mapTocanceCancelEwayBill(EwayBillCancelResponseDto billCancelDto)
	{
		CancelEwayBill cancelEwayBill=mapper.map(billCancelDto,CancelEwayBill.class);
		
		return cancelEwayBill;
	}

	public EwayBillCancelResponseDto mapToCancelEwayBillDto(CancelEwayBill cancelEwayBill)
	{
		EwayBillCancelResponseDto ewayBillCancelDto=mapper.map(cancelEwayBill,EwayBillCancelResponseDto.class);
		
		return ewayBillCancelDto;
	}
	
	
	
	public CancelEwayBillPartBResponse mapToCancelPartB(CancelEwayResponse cancelEwayResponse)
	{
		CancelEwayBillPartBResponse billPartBResponse=mapper.map(cancelEwayResponse, CancelEwayBillPartBResponse.class);
		
		return billPartBResponse;
	}
	
	public CancelEwayResponse mapToCancelPartBDto(CancelEwayBillPartBResponse billPartBResponse)
	{
		CancelEwayResponse cancelEwayResponse=mapper.map(billPartBResponse, CancelEwayResponse.class);
		
		return cancelEwayResponse;
	}

	public Sales salesTosalesDto(SalesDto salesDto) throws JsonProcessingException
	{
		TranDtls tranDtls = salesDto.getTranDtls();
		String writeValueAsString = objectMappe.writeValueAsString(tranDtls);
		Sales sales=new Sales();
		sales.setTaxSch(tranDtls.getTaxSch());
		sales.setSupTyp(tranDtls.getSupTyp());
		
		Sales save = salesRepo.save(sales);
		
		return save;
		
	}

}
