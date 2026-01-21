package com.SCM.controller;


import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.configurationprocessor.json.JSONException;

import com.SCM.GstLoginUserDto.CancelEwayBillPartB;
import com.SCM.GstLoginUserDto.EinvoiceCancelDto;
import com.SCM.GstLoginUserDto.EinvoiceDto;
import com.SCM.GstLoginUserDto.EinvoiceResponseDto;
import com.SCM.GstLoginUserDto.EwayBillAuthDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillCancelDto;
import com.SCM.GstLoginUserDto.EwayBillCancelResponseDto;
import com.SCM.GstLoginUserDto.EwayBillExtendValidityDto;
import com.SCM.GstLoginUserDto.EwayBillGenerateResDto;
import com.SCM.GstLoginUserDto.EwayBillLoginDto;
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.EwayBillUpdatePartBDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
import com.SCM.GstModel.EinvoiceData;
//import com.SCM.GstRepository.EwayBillAuthRepository;

//import com.SCM.GstService.EwayBillAuth;
import com.SCM.dto.SalesDto;
import com.SCM.service.GstService;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/gst")
public class GstController {
//
//	
//	@Autowired
//	private GstService gstService;
//	
//	@Autowired
//	private EinvoiceAuth authService;
//	
////	@Autowired
////	private EwayBillAuth ewayBillAuth;
//	
////	@Autowired
////	private EwayBillAuthRepository ewayBillAuthRepository;

	
//	@Autowired
//	private GstService gstService;
//	
//	@Autowired
//	private EinvoiceAuth authService;
//	
//	@Autowired
//	private EwayBillAuth ewayBillAuth;
//	
//	@Autowired
//	private EwayBillAuthRepository ewayBillAuthRepository;
//	
//	
//	
////	@PostMapping("/login")
////	
////	public ResponseEntity<GstLoginDto> gstLogin()
////	{
////		GstLoginDto gstLoginDto = authService.userLogin();
////		GstLoginDto gstData=gstService.saveGstLoginUsetr(gstLoginDto);
////		return new ResponseEntity<GstLoginDto>(gstData,HttpStatus.OK);
////	}
//	@GetMapping("/gstin/{gstin}")
//	public ResponseEntity<String> getDetailsByGstin(@PathVariable ("gstin") String gstin,@RequestBody LoginDto loginDto1) throws JSONException, JsonProcessingException, org.springframework.boot.configurationprocessor.json.JSONException
//	{
////		GstLoginDto gstLoginDto = authService.userLogin(loginDto1);
////		GstLoginDto newgstLoginDto=gstService.saveGstLoginUsetr(gstLoginDto);
//////		authService.getGSTINDetails(gstin);
////		return new ResponseEntity<String>(authService.getGSTINDetails(gstin,gstLoginDto),HttpStatus.OK);
//		return null;
//	
//	@GetMapping("/gstin/{gstin}")
//	public ResponseEntity<String> getDetailsByGstin(@PathVariable ("gstin") String gstin,@RequestBody LoginDto loginDto1) throws JSONException, JsonProcessingException, org.springframework.boot.configurationprocessor.json.JSONException
//	{
//		GstLoginDto gstLoginDto = authService.userLogin(loginDto1);
//		GstLoginDto newgstLoginDto=gstService.saveGstLoginUsetr(gstLoginDto);
////		authService.getGSTINDetails(gstin);
//		return new ResponseEntity<String>(authService.getGSTINDetails(gstin,gstLoginDto),HttpStatus.OK);
//	}
//	
//	
//	@GetMapping("/get/decrptedEinvoice")
//	public String decyptEinvoice(@RequestParam ("data") String data,@RequestParam ("decryptedSek") String decryptedSek)
//	{
//		String decrptedData=authService.decryptBySymmetricKey(data, decryptedSek);
//		
//		return decrptedData;
//	}
//	
//	@GetMapping("/post/encrptedeinvoice/{salesId}")
//public EinvoiceResponseDto generateEncryptEinvoice(@RequestBody SalesDto einvoice,@PathVariable int salesId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException, URISyntaxException, InterruptedException, ParseException, JSONException
//			{
//
////				EinvoiceResponseDto data=authService.getEncryptedData(einvoice);
//	     @GetMapping("/post/encrptedeinvoice/{salesId}")
//         public EinvoiceResponseDto generateEncryptEinvoice(@RequestBody SalesDto einvoice,@PathVariable int salesId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException, URISyntaxException, InterruptedException, ParseException, JSONException
//			{
//
//				EinvoiceResponseDto data=authService.getEncryptedData(einvoice);
//				
////				EinvoiceResponseDto einvoiceResponseDto=gstService.saveEinvoiceData(data, salesId);
//				
////				EinvoiceData einvoiceData=gstService.saveData(data);
//				
////				return data;
//		return null;
//			}
//	
//	
//	
//				return null;
//			}
//	
//	
//
//	@GetMapping("/get/cancelirn")
//
//	public ResponseEntity<String> cancelIrn(@RequestBody EinvoiceCancelDto einvoiceCancelDto) throws JsonProcessingException, JSONException
//	{
////		String einvoiceCancelResponseDto=authService.cancelInvoice(einvoiceCancelDto);
//		
////		return new ResponseEntity<String>(einvoiceCancelResponseDto,HttpStatus.OK);
//		return null;
//		String einvoiceCancelResponseDto=authService.cancelInvoice(einvoiceCancelDto);
//		
//		return new ResponseEntity<String>(einvoiceCancelResponseDto,HttpStatus.OK);
//	}
//
//
//	@GetMapping("/get/generateeway")
//
//	public ResponseEntity<String> generateEway(@RequestBody EwayBillLoginDto billLoginDto) throws JsonProcessingException, JSONException
//	{
////		String ewayBill=authService.EwayBillGenerate(billLoginDto);
//		
////		return new ResponseEntity<String>(ewayBill,HttpStatus.OK);
//		return null;
//		String ewayBill=authService.EwayBillGenerate(billLoginDto);
//		
//		return new ResponseEntity<String>(ewayBill,HttpStatus.OK);
//	}
//	
//	@GetMapping("/get/geteinvoice/{irn}")
//	
//	public ResponseEntity<String> getEivoiceByIrn(@PathVariable String irn) throws JSONException
//	{
////		String einvoiceData=authService.getEinvoiceByIrn(irn);
//		
////		return new ResponseEntity<String>(einvoiceData,HttpStatus.OK);
//		return null;
//		String einvoiceData=authService.getEinvoiceByIrn(irn);
//		
//		return new ResponseEntity<String>(einvoiceData,HttpStatus.OK);
//	}
//	
//	
//	@PostMapping("/post/gstdata")
//
//	public GstLoginDto getData(@RequestBody LoginDto loginDto) throws Exception
//	{
////		GstLoginDto gstUserLoginDto=authService.userLogin(loginDto);
//		
////		return gstUserLoginDto;
//		return null;
//		GstLoginDto gstUserLoginDto=authService.userLogin(loginDto);
//		
//		return gstUserLoginDto;
//	}
//	
//	
//	@GetMapping("/get/ewaycancel")
//	
//	public ResponseEntity<String> ewayBillCancelDto(@RequestBody EwayBillCancelDto ewayBillCancelDto) throws JSONException, IOException
//	{
////		String dataEway=authService.cancelEwayBill(ewayBillCancelDto);
//		
////		return new ResponseEntity<String>(dataEway,HttpStatus.OK);
//		return null;
//		String dataEway=authService.cancelEwayBill(ewayBillCancelDto);
//		
//		return new ResponseEntity<String>(dataEway,HttpStatus.OK);
//	}
//	
//	@GetMapping("/get/ewaybill/{irn}")
//	
//	public ResponseEntity<String> ewayBillByIrn(@PathVariable String irn)
//	{
////		String getEwayBill=authService.getEwayBillByIrn(irn);
////		return new ResponseEntity<String>(getEwayBill,HttpStatus.OK);
//		return null;
//		String getEwayBill=authService.getEwayBillByIrn(irn);
//		return new ResponseEntity<String>(getEwayBill,HttpStatus.OK);
//		
//	}
//	
//	@PostMapping("/post/ewaybillauth")
//	
//	public EwayBillAuthResponseDto authEwayBill(@RequestBody EwayBillAuthDto ewayBillAuthDto) throws Exception
//	{
////		EwayBillAuthResponseDto billAuthResponseDto=ewayBillAuth.ewayAuthentication(ewayBillAuthDto);
//		
////		EwayBillAuthResponseDto billAuthResponseDto2=gstService.saveEwayResponse(billAuthResponseDto);
//		
////		return billAuthResponseDto;
//		return null;
//		EwayBillAuthResponseDto billAuthResponseDto=ewayBillAuth.ewayAuthentication(ewayBillAuthDto);
//		
//		EwayBillAuthResponseDto billAuthResponseDto2=gstService.saveEwayResponse(billAuthResponseDto);
//		
//		return billAuthResponseDto;
//	}
//	
//	@PostMapping("/post/ewaypartb")
//	
//	public EwayBillGenerateResDto generateEwayPartB(@RequestBody EwayBillPartBGenerateDto billPartBGenerateDto) throws JsonProcessingException, JSONException 
//	{
//
////		EwayBillGenerateResDto data=ewayBillAuth.generateEwayPartB(billPartBGenerateDto);
//		
////		EwayBillGenerateResDto billGenerateResDto=gstService.saveEwayBillResponse(data);
//		
////		return data;
//		return null;
//		EwayBillGenerateResDto data=ewayBillAuth.generateEwayPartB(billPartBGenerateDto);
//		
//		EwayBillGenerateResDto billGenerateResDto=gstService.saveEwayBillResponse(data);
//		
//		return data;
//	}
//	
//	
//	@PostMapping("/post/canceleway")
//	
//	public ResponseEntity<String> cancelEwayBillPartB(@RequestBody CancelEwayBillPartB cancelEwayBillPartB) throws JsonProcessingException
//	{
////		String data=ewayBillAuth.cancelEwayBill(cancelEwayBillPartB);
//		
////		return new ResponseEntity<String>(data,HttpStatus.OK);
//		return null;
//		String data=ewayBillAuth.cancelEwayBill(cancelEwayBillPartB);
//		
//		return new ResponseEntity<String>(data,HttpStatus.OK);
//	}
//
//	
//	@GetMapping("/get/getEway/{ewbNo}")
//	
//	public ResponseEntity<String> getEwayBill(@PathVariable String ewbNo) throws JSONException
//	{
////		String data=ewayBillAuth.getEwayBill(ewbNo);
//		
////		return new ResponseEntity<String>(data,HttpStatus.OK);
//		return null;
//		String data=ewayBillAuth.getEwayBill(ewbNo);
//		
//		return new ResponseEntity<String>(data,HttpStatus.OK);
//	}
//	
//	
//	@PostMapping("/post/updatePartB")
//	
//	public ResponseEntity<String> updatePartB(@RequestBody EwayBillUpdatePartBDto billUpdatePartBDto) throws JsonProcessingException
//	{
////		String data=ewayBillAuth.updatePartB(billUpdatePartBDto);
//		
////		return new ResponseEntity<String>(data,HttpStatus.OK);
//		return null;
//		String data=ewayBillAuth.updatePartB(billUpdatePartBDto);
//		
//		return new ResponseEntity<String>(data,HttpStatus.OK);
//	}
//	
//	@PostMapping("/post/extendEway")
//	
//	public ResponseEntity<String> extendPartB(@RequestBody EwayBillExtendValidityDto billExtendValidityDto) throws JsonProcessingException
//	{
////		String data=ewayBillAuth.extendValidityEway(billExtendValidityDto);
//		
////		return new ResponseEntity<String>(data,HttpStatus.OK);
//		
//		return null;
//	}
//	
//	
//
//		String data=ewayBillAuth.extendValidityEway(billExtendValidityDto);
//		
//		return new ResponseEntity<String>(data,HttpStatus.OK);
//	}
//	
//	

}
