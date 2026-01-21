package com.SCM.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jboss.jandex.Main;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

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
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.GetEinvoiceDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
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
import com.SCM.GstRepository.CancelEinvoiceRepository;
import com.SCM.GstRepository.CancelEinvoiceSalesResturn;
import com.SCM.GstRepository.CancelEwayBillRepository;
import com.SCM.GstRepository.CancelEwayPartBRepository;
import com.SCM.GstRepository.CancelPurchaseReturnRepository;
import com.SCM.GstRepository.EinvoicePurchaseReturnRepo;
import com.SCM.GstRepository.EinvoiceRepository;
import com.SCM.GstRepository.EinvoiceSalesReturnRepository;
import com.SCM.GstRepository.EwayBillAuthRepository;
import com.SCM.GstRepository.EwayBillByIrnRepository;
import com.SCM.GstRepository.EwayBillResponseRepository;
import com.SCM.GstRepository.GetEinvoiceRepository;
import com.SCM.GstRepository.GstRepository;
import com.SCM.dto.SalesDto;
import com.SCM.mapper.GstMapper;

import com.SCM.model.DistributorOpeningStock;
import com.SCM.model.Jobworkoutward;
import com.SCM.model.PurchaseReturn;
import com.SCM.model.Sales;
import com.SCM.model.SalesReturn;

import com.SCM.repository.JobworkoutwardRepo;
import com.SCM.repository.PurchaseReturnRepo;
import com.SCM.repository.SalesRepo;
import com.SCM.repository.SalesReturnRepo;
import com.SCM.service.GstService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class GstServiceImpl implements GstService {

	@Autowired
	private GstRepository gstRepository;

	@Autowired
	private EinvoiceRepository einvoiceRepository;

	@Autowired
	private EwayBillAuthRepository ewayBillAuthRepository;

	@Autowired
	private EwayBillResponseRepository billResponseRepository;

	@Autowired
	private CancelEwayPartBRepository bRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private GstMapper gstMapper;

	@Autowired
	private SalesRepo salesRepo;

	@Autowired
	private JobworkoutwardRepo jobworkoutwardRepo;

	@Autowired
	private EwayBillByIrnRepository billByIrnRepository;

	@Autowired
	private GetEinvoiceRepository einvoiceRepository2;

	@Autowired
	private CancelEinvoiceRepository cancelEinvoiceRepository;

	@Autowired
	private CancelEinvoiceSalesResturn cancelEinvoiceSalesResturn;

	@Autowired
	private GstUserLoginServiceImpl gstUserLoginServiceImpl;

	@Autowired
	private SalesReturnRepo salesReturnRepo;

	@Autowired
	private PurchaseReturnRepo purchaseReturnRepo;

	@Autowired
	private EinvoiceSalesReturnRepository einvoiceSalesReturnRepository;

	@Autowired
	private EinvoicePurchaseReturnRepo einvoicePurchaseReturnRepo;

	@Autowired
	private CancelEwayBillRepository cancelEwayBillRepository;

	@Autowired
	private CancelPurchaseReturnRepository cancelPurchaseReturnRepository;

	@Autowired
	private LoginServiceImpl loginServiceImpl;

	private static String publickeylocationString = null;

	private static Path publickeylocation;

	@Value("${FILE_PATH}")
	private static String FILE_PATH;

	@Autowired
	public GstServiceImpl(Environment env) throws IOException {

		this.publickeylocation = Paths.get(env.getProperty("app.file.upload-dir", "/einv_sandbox.pem")).toAbsolutePath()
				.normalize();
		Files.createDirectories(this.publickeylocation);

		this.publickeylocationString = Paths.get(env.getProperty("app.file.upload-dir", "/einv_sandbox.pem"))
				.toAbsolutePath().normalize().toString();

	}

	static String folderPath = "";
//		static byte[] appKey =  generateAESKey();
	public static String app_key = "/gezwTOgAwvW2fmu3nXDSoTHW/DmtbllsVM5skshn9k=";

	static String strSecretKey = app_key;

	static String userName = "API_LNVERP";
	static String password = "Lnverp@123";
	static String Gstin = "27AABCN9540N1ZY";
	static String subkey = "AL5i1p6T7z1D9K7z3d";
	static String encPayload = "";
	static String authtoken = "";
	static String sek = "";
	static ObjectMapper objectMapper;

	@Override
	public GstLoginDto saveGstLoginUsetr(GstLoginDto gstLoginDto) {
		// TODO Auto-generated method stub

		System.out.println("+++++++++++++++++++++++++++++");
		GstUserLogin gstUserLogin = gstMapper.mapToGstLogindata(gstLoginDto);// converting entity to dto//
		
//		 GstUserLogin gstUserLogin = new  GstUserLogin();
//		 gstUserLogin.setAuthToken(gstLoginDto.getAuthToken());
//	     gstUserLogin.setClientId(gstLoginDto.getClientId());
//		 gstUserLogin.setDecryptedSek(gstLoginDto.getDecryptedSek());
//		 gstUserLogin.setEncryptedSek(gstLoginDto.getEncryptedSek());
//		 gstUserLogin.setUserName(gstLoginDto.getUserName());
//		 gstUserLogin.setTokenExpiry(gstLoginDto.getTokenExpiry());
//		 gstUserLogin.setGstin(gstLoginDto.getGstin());

		 ;
		 
//		GstLoginDto loginDto=gstMapper.mapToGstLoginDto(gstRepository.save(gstUserLogin));
		GstUserLogin newGstUserLogin = gstRepository.findByGstin(gstLoginDto.getGstin());// find gst number from dto//
		GstUserLogin gstUserLogin2 = new GstUserLogin(); // empty

		if (newGstUserLogin == null) { // gst number is null//
			gstUserLogin2 = gstRepository.save(this.GstLogingDtoToGstUserLogin(gstLoginDto)); /// save data in database
		}
		if (newGstUserLogin != null) { // gst number in not null//
//			LocalDateTime now = LocalDateTime.now();
			String tokenExpiry = newGstUserLogin.getTokenExpiry();// get token expiration time//
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(tokenExpiry + " Asia/Kolkata", formatter);// zone format
																										// with token
																										// expired time
			ZonedDateTime zonedDateTimeNow = ZonedDateTime.now();// current zone time
			if (zonedDateTimeNow.isAfter(zonedDateTime)) {// check condition current zone time after token expired time
				gstRepository.deleteByGstin(gstUserLogin.getGstin());
//				gstRepository.dele
				gstUserLogin2 = gstRepository.save(gstUserLogin);
			}

			else {
				gstUserLogin2 = newGstUserLogin;
			}

		}

		return this.GstUserLoginToGstUserLoginDto(gstUserLogin2);
	}

	public GstUserLogin GstLogingDtoToGstUserLogin(GstLoginDto gstUserLoginDto) {
		return modelMapper.map(gstUserLoginDto, GstUserLogin.class);
	}

	public GstLoginDto GstUserLoginToGstUserLoginDto(GstUserLogin gstUserLogin) {
		return modelMapper.map(gstUserLogin, GstLoginDto.class);
	}

	public EinvoiceData EinvoiceDtoToEinvoice(EinvoiceResponseDto einvoiceResponseDto) {
		return modelMapper.map(einvoiceResponseDto, EinvoiceData.class);
	}

	public EinvoiceResponseDto EinvoiceToEinvoiceDto(EinvoiceData einvoiceData) {
		return modelMapper.map(einvoiceData, EinvoiceResponseDto.class);
	}

	public EwayBillData EwayBillToEwayBillDto(EwayBillAuthResponseDto billAuthResponseDto) {
		return modelMapper.map(billAuthResponseDto, EwayBillData.class);
	}

	public EwayBillAuthResponseDto EwayBillDtoToEwayBill(EwayBillData ewayBillData) {
		return modelMapper.map(ewayBillData, EwayBillAuthResponseDto.class);
	}

	public EwayBillResponse EwayResponseToEwayRespDto(EwayBillGenerateResDto bGenerateDto) {
		return modelMapper.map(bGenerateDto, EwayBillResponse.class);
	}

	public EwayBillGenerateResDto EwayResponseDtoToEwayResp(EwayBillResponse billResponse) {
		return modelMapper.map(billResponse, EwayBillGenerateResDto.class);
	}

	public EinvoiceSalesReturnData EinvoiceSalesRToEinvoiceSalesDto(EinvoiceSalesReturnDto einvoiceSalesReturnDto) {
		return modelMapper.map(einvoiceSalesReturnDto, EinvoiceSalesReturnData.class);
	}

	public EinvoiceSalesReturnDto EinvoiceSalesRDtoToEinvoiceSales(EinvoiceSalesReturnData einvoiceSalesReturnData) {
		return modelMapper.map(einvoiceSalesReturnData, EinvoiceSalesReturnDto.class);
	}

	public EinvoicePurchaseReturnData EinvoicePurchaseRToEinvoicePurchaseDto(
			EinvoicePurchaseReturnDto einvoicePurchaseReturnDto) {
		return modelMapper.map(einvoicePurchaseReturnDto, EinvoicePurchaseReturnData.class);

	}

	public EinvoicePurchaseReturnDto EinvoicePurchaseDtoToEinvoicePurchaseR(
			EinvoicePurchaseReturnData einvoicePurchaseReturnData) {
		return modelMapper.map(einvoicePurchaseReturnData, EinvoicePurchaseReturnDto.class);
	}

	public EinvoiceResponseDto saveEinvoiceData(EinvoiceResponseDto einvoiceResponseDto, int salesId) {

		Optional<Sales> sales = salesRepo.findById(salesId);

		Sales sales2 = sales.get();

		einvoiceResponseDto.setSales_id(sales2.getId());

		sales2.setInvoice_status("ACT");

		EinvoiceData einvoiceData = gstMapper.mapToEinvoiceDataDto(einvoiceResponseDto);
		EinvoiceData einvoiceData1 = einvoiceRepository.save(einvoiceData);

		int id = einvoiceResponseDto.getSales_id();
		Sales sales3 = salesRepo.findById(id).get();

		sales3.setInvoice_status(einvoiceData1.getStatus());

		sales3.setSignedQrCode(einvoiceData1.getSignedQRCode());
		sales3.setSignedInevoice(einvoiceData1.getSignedInvoice());
		sales3.setIrnno(einvoiceData1.getIrn());
		sales3.setEwaybillno(einvoiceData1.getEwbNo());
		sales3.setEwbDt(einvoiceData1.getEwbDt());
		sales3.setEwbValidTill(einvoiceData1.getEwbValidTill());
		sales3.setAckno(einvoiceData1.getAckNo());
		sales3.setAckdate(einvoiceData1.getAckDt());

		if (sales3.getEwaybillno() == 0) {
			sales3.setEway_status("InACT");
		} else {
			sales3.setEway_status("ACT");
		}

		salesRepo.save(sales3);

//		Sales sales3=salesRepo.findById(einvoiceData1.getSales_id()).get();
//		
//		salesRepo.save(sales3);

//		
		return this.EinvoiceToEinvoiceDto(einvoiceData1);
	}

	public EinvoiceSalesReturnDto saveEinvoiceSalesReturn(EinvoiceSalesReturnDto einvoiceSalesReturnDto,
			int salesReturnId) {

		Optional<SalesReturn> salesReturn = salesReturnRepo.findById(salesReturnId);

		SalesReturn salesReturn1 = salesReturn.get();

		einvoiceSalesReturnDto.setSales_return_id(salesReturn1.getId());

		salesReturn1.setInvoice_status("ACT");

		EinvoiceSalesReturnData einvoiceData = gstMapper.mapToEinvoiceSalesRDto(einvoiceSalesReturnDto);
		EinvoiceSalesReturnData einvoiceData1 = einvoiceSalesReturnRepository.save(einvoiceData);

		int id = einvoiceSalesReturnDto.getSales_return_id();
		SalesReturn salesreturn = salesReturnRepo.findById(id).get();

		salesreturn.setInvoice_status(einvoiceData1.getStatus());

		salesreturn.setSignedQrCode(einvoiceData1.getSignedQRCode());
		salesreturn.setSignedInevoice(einvoiceData1.getSignedInvoice());
		salesreturn.setIrnno(einvoiceData1.getIrn());
		salesreturn.setEwaybillno(einvoiceData1.getEwbNo());
		salesreturn.setEwbDt(einvoiceData1.getEwbDt());
		salesreturn.setEwbValidTill(einvoiceData1.getEwbValidTill());
		salesreturn.setAckno(einvoiceData1.getAckNo());
		salesreturn.setAckdate(einvoiceData1.getAckDt());
		salesReturnRepo.save(salesreturn);

//		Sales sales3=salesRepo.findById(einvoiceData1.getSales_id()).get();
//		
//		salesRepo.save(sales3);

//		
		return this.EinvoiceSalesRDtoToEinvoiceSales(einvoiceData1);
	}

	public EinvoicePurchaseReturnDto saveEinvoicePurchaseReturn(EinvoicePurchaseReturnDto einvoicePurchaseReturnDto,
			int purchaseReturnId) {

		Optional<PurchaseReturn> purchaseReturn = purchaseReturnRepo.findById(purchaseReturnId);

		PurchaseReturn purchaseReturn1 = purchaseReturn.get();

		einvoicePurchaseReturnDto.setPurchase_return_id(purchaseReturn1.getId());

		purchaseReturn1.setInvoice_status("ACT");

		EinvoicePurchaseReturnData einvoiceData = gstMapper.mapToEinvoicePurchaseRDto(einvoicePurchaseReturnDto);
		EinvoicePurchaseReturnData einvoiceData1 = einvoicePurchaseReturnRepo.save(einvoiceData);

		int id = einvoicePurchaseReturnDto.getPurchase_return_id();
		PurchaseReturn purchasereturn = purchaseReturnRepo.findById(id).get();

		purchasereturn.setInvoice_status(einvoiceData1.getStatus());

		purchasereturn.setSignedQrCode(einvoiceData1.getSignedQRCode());
		purchasereturn.setSignedInevoice(einvoiceData1.getSignedInvoice());
		purchasereturn.setIrnno(einvoiceData1.getIrn());
		purchasereturn.setEwaybillno(einvoiceData1.getEwbNo());
		purchasereturn.setEwbDt(einvoiceData1.getEwbDt());
		purchasereturn.setEwbValidTill(einvoiceData1.getEwbValidTill());
		purchasereturn.setAckno(einvoiceData1.getAckNo());
		purchasereturn.setAckdate(einvoiceData1.getAckDt());
		purchaseReturnRepo.save(purchasereturn);

//		Sales sales3=salesRepo.findById(einvoiceData1.getSales_id()).get();
//		
//		salesRepo.save(sales3);

//		
		return this.EinvoicePurchaseDtoToEinvoicePurchaseR(einvoiceData1);
	}

	public EinvoiceData saveData(EinvoiceData einvoiceData) {
		EinvoiceData data = einvoiceRepository.save(einvoiceData);

		return data;
	}

	@Override
	public EwayBillAuthResponseDto saveEwayResponse(EwayBillAuthResponseDto billAuthResponseDto) {
		EwayBillData ewayBillAuth = gstMapper.mapToEwayresponseDto(billAuthResponseDto);

		EwayBillData ewayBillAuth1 = ewayBillAuthRepository.findByAuthToken(billAuthResponseDto.getAuthToken());

//		if(ewayBillAuthRepository.existsByAuthToken(billAuthResponseDto.getAuthToken()))
//		{
//			throw new RuntimeException("AuthToken Already Exist");
//		}
//		
//		if(!ewayBillAuthRepository.existsByAuthToken(billAuthResponseDto.getAuthToken()))
//		{
//			EwayBillData ewayBillAuth2=ewayBillAuthRepository.save(ewayBillAuth);
//		}

		if (ewayBillAuth1 == null) {
			EwayBillData ewayBillData = ewayBillAuthRepository.save(ewayBillAuth);

		}

		if (ewayBillAuth1 != null) {
			if (!ewayBillAuthRepository.existsByAuthToken(billAuthResponseDto.getAuthToken())) {
				EwayBillData ewayBillData = ewayBillAuthRepository.save(ewayBillAuth);
			}

		}

		return null;

	}

//	public EwayBillGenerateResDto saveEwayBillResponse(EwayBillGenerateResDto billPartBGenerateDto)
//	{
//		EwayBillResponse billResponse=gstMapper.mapToEwayResponseDto(billPartBGenerateDto);
//		
//		EwayBillResponse billResponse2=billResponseRepository.save(billResponse);
//		
//		return this.EwayResponseDtoToEwayResp(billResponse2);
//	}

	@Override
	public EwayBillByIrnDto saveEwayBillByIrn(EwayBillByIrnDto billByIrnDto, int salesId) {
		// TODO Auto-generated method stub

		Optional<Sales> sales = salesRepo.findById(salesId);

		Sales sales2 = sales.get();

		billByIrnDto.setSales_id(sales2.getId());

		EwayBillByIrn byIrn = gstMapper.mapToEwayBillByIrnDto(billByIrnDto);

		EwayBillByIrn byIrn2 = billByIrnRepository.save(byIrn);

		int id = billByIrnDto.getSales_id();
		Sales sales3 = salesRepo.findById(id).get();

		sales3.setEwaybillno(billByIrnDto.getEwbNo());
		sales3.setEwbDt(billByIrnDto.getEwbDt());
		sales3.setEwbValidTill(billByIrnDto.getEwbValidTill());
		sales3.setEway_status(byIrn2.getStatus());

		salesRepo.save(sales3);

		return gstMapper.mapToEwayBillByIrn(byIrn2);

	}

	@Override
	public GetEinvoiceDto getEinvoice(GetEinvoiceDto einvoiceDto, int salesId) {
		// TODO Auto-generated method stub

		Optional<Sales> sales = salesRepo.findById(salesId);

		Sales sales2 = sales.get();

		einvoiceDto.setSales_id(sales2.getId());

		GetEinvoice getEinvoice = gstMapper.mapToGetEinvoiceDto(einvoiceDto);

		GetEinvoice getEinvoice2 = einvoiceRepository2.save(getEinvoice);

		return gstMapper.mapToGetEinvoice(getEinvoice2);
	}

	@Override
	public CancelEinvoiceDto cancelEinvoice(CancelEinvoiceDto cancelEinvoiceDto, int salesId) {
		// TODO Auto-generated method stub

		Optional<Sales> sales = salesRepo.findById(salesId);

		Sales sales2 = sales.get();

		cancelEinvoiceDto.setSales_id(sales2.getId());

		CancelEinvoice cancelEinvoice = gstMapper.mapToCancelEinvoiceDto(cancelEinvoiceDto);

		CancelEinvoice cancelEinvoice2 = cancelEinvoiceRepository.save(cancelEinvoice);

		int id = cancelEinvoiceDto.getSales_id();
		Sales sales3 = salesRepo.findById(id).get();

		sales3.setInvoice_status(cancelEinvoiceDto.getStatus());
		salesRepo.save(sales3);

		return gstMapper.mapToCancelEinvoice(cancelEinvoice2);
	}

	public CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturn(
			CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturnDto, int salesReturnId) {
		// TODO Auto-generated method stub

		Optional<SalesReturn> salesReturn = salesReturnRepo.findById(salesReturnId);

		SalesReturn salesReturn2 = salesReturn.get();

		cancelEinvoiceSalesReturnDto.setSales_return_id(salesReturn2.getId());

		CancelEinvoiceSalesReturn cancelEinvoiceReturn = gstMapper
				.mapToCancelSalesReturnDto(cancelEinvoiceSalesReturnDto);

		CancelEinvoiceSalesReturn cancelEinvoiceReturn2 = cancelEinvoiceSalesResturn.save(cancelEinvoiceReturn);

		int id = cancelEinvoiceSalesReturnDto.getSales_return_id();
		SalesReturn salesReturn3 = salesReturnRepo.findById(id).get();

		salesReturn3.setInvoice_status(cancelEinvoiceSalesReturnDto.getStatus());
		salesReturnRepo.save(salesReturn3);

		return gstMapper.mapToCancelSalesReturn(cancelEinvoiceReturn2);
	}

	public CancelPurchaseReturnDto cancelEinvoicePurchaseReturn(CancelPurchaseReturnDto cancelPurchaseReturnDto,
			int purchaseReturnId) {
		// TODO Auto-generated method stub

		Optional<PurchaseReturn> purchaseReturn = purchaseReturnRepo.findById(purchaseReturnId);

		PurchaseReturn purchaseReturn2 = purchaseReturn.get();

		cancelPurchaseReturnDto.setPurchase_return_id(purchaseReturn2.getId());

		CancelPurchaseReturn cancelPurchaseReturn = gstMapper.mapToCancelPurchaseReturnDto(cancelPurchaseReturnDto);

		CancelPurchaseReturn cancelPurchaseReturn2 = cancelPurchaseReturnRepository.save(cancelPurchaseReturn);

		int id = cancelPurchaseReturnDto.getPurchase_return_id();
		PurchaseReturn purchaseReturn3 = purchaseReturnRepo.findById(id).get();

		purchaseReturn3.setInvoice_status(cancelPurchaseReturnDto.getStatus());
		purchaseReturnRepo.save(purchaseReturn3);

		return gstMapper.mapToCancelPurchaseReturn(cancelPurchaseReturn2);
	}

	public EwayBillCancelResponseDto cancelEwayBill(EwayBillCancelResponseDto billCancelDto, int salesId) {
		Optional<Sales> sales = salesRepo.findById(salesId);

		Sales sales2 = sales.get();

		billCancelDto.setSalesId(sales2.getId());

		CancelEwayBill cancelEwayBill = gstMapper.mapTocanceCancelEwayBill(billCancelDto);

		CancelEwayBill cancelEwayBill2 = cancelEwayBillRepository.save(cancelEwayBill);

		int id = billCancelDto.getSalesId();

		Sales sales3 = salesRepo.findById(id).get();

		sales3.setEway_status(billCancelDto.getStatus());

		salesRepo.save(sales3);

		return gstMapper.mapToCancelEwayBillDto(cancelEwayBill2);
	}

	public EwayBillGenerateResDto generatePartB(EwayBillGenerateResDto billGenerateResDto, int JoId) {

		Optional<Jobworkoutward> jobworkoutward = jobworkoutwardRepo.findById(JoId);

		Jobworkoutward jobworkoutward2 = jobworkoutward.get();

		billGenerateResDto.setJo_id(JoId);

		jobworkoutward2.setEway_status("Generated");

		EwayBillResponse einvoiceData = gstMapper.mapToEwayResponseDto(billGenerateResDto);
		EwayBillResponse einvoiceData1 = billResponseRepository.save(einvoiceData);

		int id = billGenerateResDto.getJo_id();
		Jobworkoutward jobworkoutward3 = jobworkoutwardRepo.findById(id).get();

		jobworkoutward3.setEway_status(billGenerateResDto.getStatus());
		jobworkoutward3.setEwayBillNo(einvoiceData1.getEwayBillNo());
		jobworkoutward3.setEwayBillDate(einvoiceData1.getEwayBillDate());
		jobworkoutward3.setValidUpto(einvoiceData1.getValidUpto());

		jobworkoutwardRepo.save(jobworkoutward3);

		return gstMapper.mapToEwayBillResp(einvoiceData1);
	}

	@Override
	public CancelEwayBillPartBResponse billPartBResponse(CancelEwayBillPartBResponse billPartBResponse, int joId) {
		// TODO Auto-generated method stub
		Optional<Jobworkoutward> jobworkoutward = jobworkoutwardRepo.findById(joId);

		Jobworkoutward jobworkoutward2 = jobworkoutward.get();

		billPartBResponse.setJo_id(jobworkoutward2.getId());

		CancelEwayResponse cancelEwayResponse = gstMapper.mapToCancelPartBDto(billPartBResponse);

		CancelEwayResponse cancelEwayResponse2 = bRepository.save(cancelEwayResponse);

		int id = billPartBResponse.getJo_id();
		Jobworkoutward jobworkoutward3 = jobworkoutwardRepo.findById(id).get();

		jobworkoutward3.setEway_status(cancelEwayResponse2.getStatus());

		jobworkoutwardRepo.save(jobworkoutward3);

		return gstMapper.mapToCancelPartB(cancelEwayResponse2);
	}

	public void deleteGstin() {
		gstRepository.deleteAll();
	}

}
