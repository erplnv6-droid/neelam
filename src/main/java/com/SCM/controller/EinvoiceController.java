package com.SCM.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.SCM.GstLoginUserDto.CancelEinvoiceDto;
import com.SCM.GstLoginUserDto.CancelEinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.CancelEwayBillPartB;
import com.SCM.GstLoginUserDto.CancelEwayBillPartBResponse;
import com.SCM.GstLoginUserDto.CancelPurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceCancelDto;
import com.SCM.GstLoginUserDto.EinvoicePurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceResponseDto;
import com.SCM.GstLoginUserDto.EinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.EwayBillAuthDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillByIrnDto;
import com.SCM.GstLoginUserDto.EwayBillCancelDto;
import com.SCM.GstLoginUserDto.EwayBillCancelResponseDto;
import com.SCM.GstLoginUserDto.EwayBillExtendValidityDto;
import com.SCM.GstLoginUserDto.EwayBillGenerateResDto;
import com.SCM.GstLoginUserDto.EwayBillLoginDto;
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.EwayBillUpdatePartBDto;
import com.SCM.GstLoginUserDto.GetEinvoiceDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
import com.SCM.GstRepository.EwayBillAuthRepository;
import com.SCM.dto.PurchaseReturnDto;
import com.SCM.dto.SalesDto;
import com.SCM.dto.SalesReturnDto;
import com.SCM.model.PurchaseReturn;
import com.SCM.model.Sales;
import com.SCM.model.SalesReturn;
import com.SCM.service.EwayBillService;
import com.SCM.service.GstService;
import com.SCM.service.LoginService;
import com.SCM.serviceimpl.EinvoiceAuth;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/gst")
@CrossOrigin(origins = "*")
public class EinvoiceController {

	@Autowired
	private GstService gstService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private EinvoiceAuth einvoiceAuth;

	@Autowired
	private EwayBillService billService;

	@GetMapping("/gstin/{gstin}")
	public ResponseEntity<String> getDetailsByGstin(@PathVariable("gstin") String gstin) throws Exception {
		
		GstLoginDto gstLoginDto = einvoiceAuth.userLogin();
		GstLoginDto newgstLoginDto = gstService.saveGstLoginUsetr(gstLoginDto);
//		authService.getGSTINDetails(gstin);
		return new ResponseEntity<String>(einvoiceAuth.getGSTINDetails(gstin, gstLoginDto), HttpStatus.OK);
	}

	@PostMapping("/post/encrptedeinvoice/{salesId}")
	public EinvoiceResponseDto generateEncryptEinvoice(@RequestBody SalesDto einvoice, @PathVariable int salesId)
			throws Exception {

		EinvoiceResponseDto data = einvoiceAuth.getEncryptedData(einvoice);

		EinvoiceResponseDto einvoiceResponseDto = gstService.saveEinvoiceData(data, salesId);

		Sales sales = new Sales();

		sales.setInvoice_status("ACT");

//				EinvoiceData einvoiceData=gstService.saveData(data);

		return data;
	}

	@PostMapping("/post/encryptedeinvoice/{salesReturnId}")
	public EinvoiceSalesReturnDto generateEncryptEinvoiceSalesR(@RequestBody SalesReturnDto salesReturnDto,
			@PathVariable int salesReturnId) throws Exception {

		EinvoiceSalesReturnDto data = einvoiceAuth.getEncryptedData1(salesReturnDto);

		EinvoiceSalesReturnDto einvoiceResponseDto = gstService.saveEinvoiceSalesReturn(data, salesReturnId);

		SalesReturn salesReturn = new SalesReturn();

		salesReturn.setInvoice_status("ACT");

//					EinvoiceData einvoiceData=gstService.saveData(data);

		return data;
	}

	@PostMapping("/post/purchaseencryptedeinvoice/{purchaseReturnId}")
	public EinvoicePurchaseReturnDto generateEncryptEinvoicePurchaseR(@RequestBody PurchaseReturnDto purchaseReturnDto,
			@PathVariable int purchaseReturnId) throws Exception {

		EinvoicePurchaseReturnDto data = einvoiceAuth.getEncryptedData2(purchaseReturnDto);

		EinvoicePurchaseReturnDto einvoiceResponseDto = gstService.saveEinvoicePurchaseReturn(data, purchaseReturnId);

		PurchaseReturn purchaseReturn = new PurchaseReturn();

		purchaseReturn.setInvoice_status("ACT");

//					EinvoiceData einvoiceData=gstService.saveData(data);

		return data;
	}

	@PostMapping("/get/cancelirn/{salesId}")

	public CancelEinvoiceDto cancelIrn(@RequestBody EinvoiceCancelDto einvoiceCancelDto, @PathVariable int salesId)
			throws Exception {
		CancelEinvoiceDto einvoiceCancelResponseDto = einvoiceAuth.cancelInvoice(einvoiceCancelDto);

		CancelEinvoiceDto cancelEinvoiceDto = gstService.cancelEinvoice(einvoiceCancelResponseDto, salesId);

		return einvoiceCancelResponseDto;
	}

	@PostMapping("/get/cancelsalesreturn/{salesReturnId}")

	public CancelEinvoiceSalesReturnDto CancelSalesReturn(@RequestBody EinvoiceCancelDto einvoiceCancelDto,
			@PathVariable int salesReturnId) throws JsonProcessingException, JSONException, Exception {
		CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturnDto = einvoiceAuth
				.cancelInvoiceSalesReturn(einvoiceCancelDto);

		CancelEinvoiceSalesReturnDto einvoiceSalesReturnDto = gstService
				.cancelEinvoiceSalesReturn(cancelEinvoiceSalesReturnDto, salesReturnId);
		return cancelEinvoiceSalesReturnDto;

	}

	@PostMapping("/get/cancelpurchasereturn/{purchaseReturnId}")
	public CancelPurchaseReturnDto CancelPurchaseReturn(@RequestBody EinvoiceCancelDto einvoiceCancelDto,
			@PathVariable int purchaseReturnId) throws JsonProcessingException, JSONException, Exception {
		CancelPurchaseReturnDto cancelPurchaseReturnDto = einvoiceAuth.cancelInvoicePurchaseReturn(einvoiceCancelDto);

		CancelPurchaseReturnDto einvoicePurchaseReturnDto = gstService
				.cancelEinvoicePurchaseReturn(cancelPurchaseReturnDto, purchaseReturnId);
		return cancelPurchaseReturnDto;

	}

	@GetMapping("/get/generateeway/{salesId}")

	public EwayBillByIrnDto generateEway(@RequestBody EwayBillLoginDto billLoginDto, @PathVariable int salesId)
			throws Exception {
		EwayBillByIrnDto ewayBill = einvoiceAuth.EwayBillGenerate(billLoginDto);

		EwayBillByIrnDto billByIrnDto = gstService.saveEwayBillByIrn(ewayBill, salesId);

		return ewayBill;
	}

	@GetMapping("/get/geteinvoice/{irn}/{salesId}")

	public GetEinvoiceDto getEivoiceByIrn(@PathVariable String irn, @PathVariable int salesId) throws Exception {

		GetEinvoiceDto einvoiceData = einvoiceAuth.getEinvoiceByIrn(irn);

		GetEinvoiceDto einvoiceDto = gstService.getEinvoice(einvoiceData, salesId);

		return einvoiceData;
	}

//	@PostMapping("/post/gstdata")
//
//	public GstLoginDto getData(@RequestBody LoginDto loginDto) throws Exception
//	{
//		GstLoginDto gstUserLoginDto=einvoiceAuth.userLogin(loginDto);
//		
//		return gstUserLoginDto;
//	}

	@PostMapping("/get/ewaycancel/{salesId}")

	public EwayBillCancelResponseDto ewayBillCancelDto(@RequestBody EwayBillCancelDto ewayBillCancelDto,
			@PathVariable int salesId) throws Exception {
		EwayBillCancelResponseDto dataEway = einvoiceAuth.cancelEwayBill(ewayBillCancelDto);

		EwayBillCancelResponseDto cancelResponseDto = gstService.cancelEwayBill(dataEway, salesId);

		return dataEway;
	}

	@GetMapping("/get/ewaybill/{irn}")

	public ResponseEntity<String> ewayBillByIrn(@PathVariable String irn) {
		String getEwayBill = einvoiceAuth.getEwayBillByIrn(irn);

		return new ResponseEntity<String>(getEwayBill, HttpStatus.OK);

	}

	@PostMapping("/post/ewaybillauth")

	public EwayBillAuthResponseDto authEwayBill() throws Exception {
		EwayBillAuthResponseDto billAuthResponseDto = billService.ewayAuthentication();

		EwayBillAuthResponseDto billAuthResponseDto2 = gstService.saveEwayResponse(billAuthResponseDto);

		return billAuthResponseDto;
	}

	@PostMapping("/post/ewaypartb/{joId}")

	public EwayBillGenerateResDto generateEwayPartB(@RequestBody EwayBillPartBGenerateDto billPartBGenerateDto,
			@PathVariable int joId) throws Exception {

		EwayBillGenerateResDto data = billService.generateEwayPartB(billPartBGenerateDto, joId);

		EwayBillGenerateResDto billGenerateResDto = gstService.generatePartB(data, joId);

		return data;
	}

	@PostMapping("/post/canceleway/{joId}")

	public CancelEwayBillPartBResponse cancelEwayBillPartB(@RequestBody CancelEwayBillPartB cancelEwayBillPartB,
			@PathVariable int joId) throws JsonProcessingException {
		CancelEwayBillPartBResponse data = billService.cancelEwayBill(cancelEwayBillPartB, joId);

		CancelEwayBillPartBResponse billPartBResponse = gstService.billPartBResponse(data, joId);

		return data;
	}

	@GetMapping("/get/getEway/{trn_no}")

	public ResponseEntity<String> getEwayBill(@PathVariable String trn_no) throws JSONException {
		String data = billService.getTransportNo(trn_no);

		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@PostMapping("/post/updatePartB")

	public ResponseEntity<String> updatePartB(@RequestBody EwayBillUpdatePartBDto billUpdatePartBDto)
			throws JsonProcessingException {
		String data = billService.updatePartB(billUpdatePartBDto);

		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

	@PostMapping("/post/extendEway")

	public ResponseEntity<String> extendPartB(@RequestBody EwayBillExtendValidityDto billExtendValidityDto)
			throws JsonProcessingException {
		String data = billService.extendValidityEway(billExtendValidityDto);

		return new ResponseEntity<String>(data, HttpStatus.OK);
	}

}
