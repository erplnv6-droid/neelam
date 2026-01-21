package com.SCM.serviceimpl;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.jboss.jandex.Main;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.SCM.GstLoginUserDto.CancelEinvoiceDto;
import com.SCM.GstLoginUserDto.CancelEinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.CancelPurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceCancelDto;
import com.SCM.GstLoginUserDto.EinvoiceCancelResponseDto;
import com.SCM.GstLoginUserDto.EinvoiceDto;
import com.SCM.GstLoginUserDto.EinvoicePurchaseReturnDto;
import com.SCM.GstLoginUserDto.EinvoiceResponseDto;
import com.SCM.GstLoginUserDto.EinvoiceSalesReturnDto;
import com.SCM.GstLoginUserDto.EwayBillAuthDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillByIrnDto;
import com.SCM.GstLoginUserDto.EwayBillCancelDto;
import com.SCM.GstLoginUserDto.EwayBillCancelResponseDto;
import com.SCM.GstLoginUserDto.EwayBillLoginDto;
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.GetEinvoiceDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
import com.SCM.GstModel.EinvoiceData;
import com.SCM.GstModel.EwayBillByIrn;
import com.SCM.GstModel.GstUserLogin;
import com.SCM.GstRepository.EinvoiceRepository;
import com.SCM.GstRepository.EwayBillByIrnRepository;
import com.SCM.GstRepository.GstRepository;
import com.SCM.dto.PurchaseReturnDto;
import com.SCM.dto.SalesDto;
import com.SCM.dto.SalesReturnDto;
import com.SCM.mapper.GstMapper;
import com.SCM.model.Sales;
import com.SCM.repository.SalesRepo;
import com.SCM.service.EinvoiceService;
import com.SCM.serviceimpl.GstServiceImpl;
import com.SCM.serviceimpl.GstUserLoginServiceImpl;
import com.SCM.serviceimpl.LoginServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.grpc.netty.shaded.io.netty.channel.unix.Buffer;
import lombok.experimental.var;

@Service
public class EinvoiceAuth implements EinvoiceService {
	


@Autowired
private GstUserLoginServiceImpl gstUserLoginServiceImpl;






;
@Autowired
private LoginServiceImpl loginServiceImpl;

   private static String publickeylocationString;
   

   private Path publickeylocation;

 
   
    @Value("${FILE_PATH}")
    private String FILE_PATH;



    @Autowired
   	public EinvoiceAuth(Environment env) throws IOException {
   	
//   		this.publickeylocation = Paths.get(env.getProperty("app.file.upload-dir1","einv_production.pem")).toAbsolutePath()
//   				.normalize();
//   		Files.createDirectories(this.publickeylocation);
//   		System.out.println("publickeylocation" + publickeylocation);
//   		this.publickeylocationString = Paths.get(env.getProperty("app.file.upload-dir1","einv_production.pem")).toAbsolutePath()
//   				.normalize().toString();
//    	this.publickeylocation = Paths.get(env.getProperty("app.file.upload-dir1","/einv_production.pem")).toAbsolutePath()
//   				.normalize();
////   		Files.createDirectories(this.publickeylocation);
//   		System.out.println("publickeylocation" + publickeylocation);
//   		this.publickeylocationString = publickeylocation.toString();
//		
//   		System.out.println("publickeylocationString" + publickeylocationString);
    	try {
    		
    String relativePath = env.getProperty("app.file.upload-dir1","/einv_production.pem");
        Path relativeDirectory = Paths.get(relativePath).toAbsolutePath().normalize();
        
        // Construct the full file path for the public key
        this.publickeylocation = relativeDirectory.resolve("einv_production.pem");
        

       
        // Ensure the file exists and is readable
//        if (!Files.exists(this.publickeylocation) || !Files.isReadable(this.publickeylocation)) {
//            throw new IOException("File not accessible: " + this.publickeylocation);
//        }
        
        this.publickeylocationString = this.publickeylocation.toString();
        System.out.println("publickeylocationString: " + publickeylocationString);
        
        

        
        
  }
    	
    	catch (Exception e) {
		e.printStackTrace();
		}
   		
		
	}
    
    

    
    
    

	static String folderPath = "";
//	static byte[] appKey =  generateAESKey();
public static String app_key ="/gezwTOgAwvW2fmu3nXDSoTHW/DmtbllsVM5skshn9k=";
	
	static String strSecretKey = app_key;

	static String userName = "API_LNVERP";
	static String password = "Lnverp@123";
	static String Gstin = "27AABCN9540N1ZY";
	static String subkey = "AL5i1p6T7z1D9K7z3d";
	static String encPayload = "";
	static String authtoken = "";
	static String sek = "";
	static ObjectMapper objectMapper;
	
	

	public GstLoginDto userLogin() throws Exception {
		authtoken = "";
		folderPath = getPath();
		objectMapper = new ObjectMapper();

		try {
			String appKey = strSecretKey;
			System.out.println(appKey);
			

			String payload = "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\",\"appkey\":\""
					+ appKey + "\",\"ForceRefreshAccessToken\": true}";
			System.out.println("Payload: Plain: " + payload);
			payload = Base64.getEncoder().encodeToString(payload.getBytes());
			payload = "{\"Data\":\"" + encryptAsymmentricKey(payload) + "\"}";
			System.out.println("Payload: Encrypted: " + payload); 
	
			CloseableHttpClient httpClient = HttpClients.createDefault();
		   HttpPost postRequest = new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eivital/v1.04/auth");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Gstin", "27AABCN9540N1ZY");
			postRequest.setHeader("Ocp-Apim-Subscription-Key", "AL5i1p6T7z1D9K7z3d");
			
			
//
//	            postRequest.setHeader("appKey","[B@f601bd");
//			postRequest.addHeader("KeepAlive", "true");
//			postRequest.addHeader("AllowAutoRedirect", "false");
			StringEntity input = new StringEntity(payload,ContentType.APPLICATION_JSON);
			
			System.out.println("input:"+input);
//			input.setContentType("application/json");
			postRequest.setEntity(input);
			
		
			
			CloseableHttpResponse response = httpClient.execute(postRequest);
//			if (response.getCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getCode());
//			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			String responseText="";
			
			
	
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("Response:" + responseText);
            String status = objectMapper.readTree(responseText).get("Status").asText();
			System.out.println("status:"+status);
//			if (status.equals("0")) {
//				String errorDesc = "";
//				errorDesc = objectMapper.readTree(responseText).get("error").asText();
//				// errorDesc = new String(Base64.getDecoder().decode(errorDesc), "utf-8");
//				System.out.println("Error: " + errorDesc);
//
//			}
			GstLoginDto gstLoginDto=new GstLoginDto();
			if (status.equals("1")) {
				authtoken = objectMapper.readTree(responseText).get("Data").get("AuthToken").asText();
			
				sek = objectMapper.readTree(responseText).get("Data").get("Sek").asText();
				
				String decryptedSek = decrptBySymmetricKeySEK(sek);
				
				System.out.println("decrypted sek:"+decryptedSek);
				
				String tokenExpiry=objectMapper.readTree(responseText).get("Data").get("TokenExpiry").asText();
				
				String clientId=objectMapper.readTree(responseText).get("Data").get("ClientId").asText();
				
				userName=objectMapper.readTree(responseText).get("Data").get("UserName").asText();
				
				
				gstLoginDto.setClientId(clientId);
				gstLoginDto.setAuthToken(authtoken);
				gstLoginDto.setUserName(userName);
				gstLoginDto.setEncryptedSek(sek);
				gstLoginDto.setDecryptedSek(decryptedSek);
				gstLoginDto.setTokenExpiry(tokenExpiry);
				gstLoginDto.setGstin(Gstin);
				
				
			}
				return gstLoginDto;
			} catch (Exception ex) {
				Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, ex);
				
			}
		return null;
		

	
	
	
	}
	


	

	

	public static PublicKey getPublicKey()
			throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		System.out.println(publickeylocationString +"+++++++++ publickeylocationString ");
		try {
			System.out.println("+++++++++++++");

			try (FileInputStream in = new FileInputStream(publickeylocationString))
					{
					
			
			byte[] keyBytes = new byte[in.available()];
			in.read(keyBytes);
			in.close();
			String pubKey = new String(keyBytes, "UTF-8");
			pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
			
			keyBytes = Base64.getMimeDecoder().decode(pubKey);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(spec);
			return publicKey;
					}
		}catch (Exception e) {
			
			System.out.println(e);
		}
		return null;
		
//	      Base64Decoder decoder=new Base64Decoder();
	
	}
	


//	    public static RSAPublicKey readX509PublicKey(File file) throws Exception {
//	        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
//	 
//	        
//	        String publicKeyPEM = key
//	          .replace("-----BEGIN PUBLIC KEY-----", "")
//	          .replaceAll(System.lineSeparator(), "")
//	          .replace("-----END PUBLIC KEY-----", "");
//
//	        
//	        
//	        byte[] encoded = Base64.getDecoder();
//	        
//	      
//
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
//	        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//	    }

	public static byte[] generateAESKey() {
//		////////////////////////// This worked for E-Invoice commented for temporary period //////////////
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
						
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			return null;
			
		}
//		////////////////////////////////End///////////////////////////////
//		String strAppKey = "WbJzipC8WW0s7GbTV1BuXw==";
//		appKey = strAppKey.getBytes();
//		String appKeyBytes = "[B@8ba1a1d";
//		return appKeyBytes.getBytes();
		
	}

	public static String encryptAsymmentricKey(String clearText) throws Exception {
		PublicKey publicKeys = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, publicKeys);
		byte[] encryptedText = cipher.doFinal(clearText.getBytes());
		String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
		return encryptedPassword;
	}
	


	public static String getPath() {
		String folderPath = "";
		try {
			File tempFile = new File(
					EinvoiceAuth.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			folderPath = tempFile.getParentFile().getPath() + "\\";
			return folderPath;
		} catch (URISyntaxException ex) {
			Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, ex);
		}
		return folderPath;
	}

	public static String decrptBySymmetricKeySEK(String encryptedSek) {
//	byte[] appKey ="[B@2a451ca".getBytes();
	

//		String oldAppKey = "FO4qvo19bwCRHdj3GXMz5w==";
//		generateAESKey();
		byte[] decodedSecretKey = Base64.getDecoder().decode(strSecretKey);
		Key aesKey = new SecretKeySpec(decodedSecretKey, "AES"); // converts bytes(32 byte random generated) to key
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // encryption type = AES with padding PKCS5
			cipher.init(Cipher.DECRYPT_MODE, aesKey); // initiate decryption type with the key
			byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek); // decode the base64 encryptedSek to
																					// bytes
			byte[] decryptedSekBytes = cipher.doFinal(encryptedSekBytes); // decrypt the encryptedSek with the
																			// initialized cipher containing the
																			// key(Results in bytes)
			byte[] sekBytes = decryptedSekBytes;
			String decryptedSek = Base64.getEncoder().encodeToString(sekBytes); // convert the
																							// decryptedSek(bytes) to
			System.out.println(decryptedSek);																				// Base64 String
			return decryptedSek; // return results in base64 string
		} catch (Exception e) {
			return "Exception; " + e;
			
		}
	
	}

	public static String encryptBySymmetricKey(String json, String decryptedSek) {
		byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
		Key aesKey = new SecretKeySpec(sekByte, "AES");
		try {

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encryptedjsonbytes = cipher.doFinal(json.getBytes());
			String encryptedJson = Base64.getEncoder().encodeToString(encryptedjsonbytes);
			return encryptedJson;
		} catch (Exception e) {
			return "Exception " + e;
		}
	}

	public static String decryptBySymmetricKey(String data, String decryptedSek) {
		byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
		Key aesKey = new SecretKeySpec(sekByte, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] decodedValue = Base64.getDecoder().decode(data);
			byte[] decValue = cipher.doFinal(decodedValue);
			return new String(decValue);
		} catch (Exception e) {
			return "Exception " + e;
		}
	}

//	    public String decodeSignedJWT(String signedText)
//	    {
//			try {
//				String[] splitSignedText = signedText.split("\\.");
//				if (splitSignedText.length != 3) {
//		            throw new IllegalArgumentException("Invalid JWT format");
//		        }
//				String decodedSign = new String(Base64.getDecoder().decode(splitSignedText[0]));
//				System.out.println("decodedSign 1:"+decodedSign);
//				decodedSign =decodedSign + "\n Content:" +(new String(Base64.getDecoder().decode(splitSignedText[1])));
//				String content = new String(Base64.getDecoder().decode(splitSignedText[1]));
//				System.out.println("content 2:"+content);
//				decodedSign.replaceAll("\\\"","\"");
//				System.out.println("decodedSign 3:"+decodedSign);
//				
//				return content;
//				
//			} catch (Exception e) {
//				 Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
//				 return "Exception" + e;
//			}
//	    }
	    
	    
	    
	    
	    public String decodeSignedJWT(String signedText)
	    {
	    	 try {
	    	        String[] splitSignedText = signedText.split("\\.");
	    	        if (splitSignedText.length != 3) {
	    	            throw new IllegalArgumentException("Invalid JWT format");
	    	        }

	    	        // Decode header
	    	        String header = decodeBase64Url(splitSignedText[0]);
	    	        System.out.println("Decoded Header: " + header);

	    	        // Decode payload
	    	        String content = decodeBase64Url(splitSignedText[1]);
	    	        System.out.println("Decoded Payload: " + content);

	    	        return content;

	    	    } catch (Exception e) {
	    	        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
	    	        return "Exception: " + e.getMessage();
	    	    }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    private String decodeBase64Url(String base64Url) {
	    	 String base64 = base64Url.replace('-', '+').replace('_', '/');

	    	    // Add padding if necessary
	    	    int padding = base64.length() % 4;
	    	    if (padding > 0) {
	    	        StringBuilder paddingBuilder = new StringBuilder(base64);
	    	        for (int i = 0; i < 4 - padding; i++) {
	    	            paddingBuilder.append('=');
	    	        }
	    	        base64 = paddingBuilder.toString();
	    	    }

	    	    // Decode using standard Base64 decoder
	    	    return new String(Base64.getDecoder().decode(base64));
	    }
	    
	    
	    
	    
	    
	    
	    public String getGSTINDetails(String gstin,GstLoginDto gstLoginDto) throws JSONException,Exception{
//			GstUserLoginDto gstUserLoginDto = new GstUserLoginDto();
//			gstUserLoginDto = gstUserLoginService.getGstUserLoginByGstin("07AGAPA5363L002");
//			String decryptedSek = gstUserLoginDto.getDecryptedSek();
	    	String decryptedSek=gstLoginDto.getDecryptedSek();
			String url = "https://www.alankitgst.com/eInvoiceGateway/eivital/v1.04/Master/gstin/"+gstin;
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpGet.setHeader("Gstin","27AABCN9540N1ZY");
			httpGet.setHeader("user_name","API_LNVERP");
			httpGet.setHeader("AuthToken",this.authtoken); // Change Authtoken here
			CloseableHttpClient client = HttpClients.createDefault();
			try {
				CloseableHttpResponse response = client.execute(httpGet);
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String output;
				String responseText ="";
				while((output = br.readLine()) != null) {
					responseText = output;
				}
				JSONObject jsonObj =new JSONObject(responseText);
				System.out.println(jsonObj);
				int status=jsonObj.getInt("Status");
				
				
//				   if (status instanceof Integer) {
//			            int status1 = (Integer) status;
//			            System.out.println("Status as Integer: " + status);
//			        } else if (status instanceof String) {
//			            String status1 = (String) status;
//			            System.out.println("Status as String: " + status);
//			        } else {
//			            System.out.println("Unexpected type for Status: " + status.getClass().getSimpleName());
//			        }
			    
				
				  if(status==1) {
					  String data = jsonObj.getString("Data");
					String encryptedDetails=encryptBySymmetricKey(data, decryptedSek);
					System.out.println("enccypted data:"+encryptedDetails);
					String decryptedDetails = decryptBySymmetricKey(data,decryptedSek);
					JsonObject jsonObject = JsonParser.parseString(decryptedDetails).getAsJsonObject();
					return jsonObject.toString();
				}else {
					return jsonObj.getString("ErrorDetails");
				}
			
				
			} catch (IOException e) {
				return e.toString();
			}
			
		}
	    

	    
	    
            public EinvoiceResponseDto getEncryptedData(SalesDto einvoiceDto)throws Exception,JSONException {
//	    	System.out.println(eniEinvoiceDto.getBuyerDtls()+"chittu mcfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//			String encryptData=encryptAsymmentricKey(json);
	    	

//			System.out.println("encrypt data: "+encryptData);
//			String encrptData=encryptBySymmetricKey(json, this.sek);
//			System.out.println("encode data:"+encrptData);

	    	ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = objectMapper.writeValueAsString(einvoiceDto);

//			EinvoiceDto einvoiceDto1=objectMapper.convertValue(einvoiceDto, EinvoiceDto.class);
//			System.out.println("read value :" +einvoiceDto1);
			System.out.println("json data :" +json);
			String gstin = "27AABCN9540N1ZY";
			GstLoginDto gstUserLoginDto = new GstLoginDto();
			ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
			gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(gstin);
			LoginDto loginDto = new LoginDto(); // loginDto is empty
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(gstUserLoginDto.getTokenExpiry()+" Asia/Calcutta", formatter);
			
			if(nowZonedDateTime.isAfter(zonedDateTime)) {
				gstUserLoginDto = loginServiceImpl.gstUserLogin(loginDto);
			}
			else 
			{
				gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(gstin);
			}
			
		
			

		
			
//			System.out.println("einvoice dto:"+einvoiceDto);
			String decryptedSek =gstUserLoginDto.getDecryptedSek();
	
			System.out.println("current sek: "+decryptedSek);
//			JSONObject jsonObj1 =new JSONObject();
//			String data= jsonObj1.getString(json);
		
//		String payload= Base64.getEncoder().encodeToString(json.getBytes());
			

//			payload = Base64.getEncoder().encodeToString(json.getBytes());
//			System.out.println("payload data:" +payload);
//			payload = Base64.getMimeEncoder().encodeToString(json.getBytes());

			String payload = "{\"Data\":\"" + encryptBySymmetricKey(json,decryptedSek)+ "\"}";
			
			System.out.println("encrypted data: "+ payload);

		
			HttpPost httpPost =new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice");
			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
			httpPost.setHeader("user_name","API_LNVERP");
//			httpPost.toString(payload,ContentType.APPLICATION_JSON);
			httpPost.setHeader("AuthToken",this.authtoken);
//			httpPost.setEntity(payload);
//			httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		// Change Authtoken here
//		    String contentType = "text/plain;charset=utf-8";
		    
//			httpPost.setEntity(new StringEntity(payload,ContentType.create(contentType)));
			StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_JSON);

			httpPost.setEntity(entity);
			System.out.println("entity" +entity);
			
	

			CloseableHttpClient httpClient = HttpClients.createDefault();
	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

			try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			String responseText ="";
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("responseText: "+responseText);
	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

			JSONObject jsonObj = new JSONObject(responseText);
			
			int status = jsonObj.getInt("Status");
			String data = jsonObj.getString("Data");
//			System.out.println("Response:" + responseText);
			
		

	
			System.out.println("status"+status);
//			System.out.println("Data"+data);
				

		
			System.out.println("not null:" +jsonObj);
////			

			System.out.println("data chittu: "+data);
//			String status = jsonObj.getString("Status");

				
	
			EinvoiceResponseDto einvoiceResponse=new EinvoiceResponseDto();
				if(status==1) {
			
				
//			
//					String data=objectMapper.readTree(responseText).get("Data").asText();
					String einvoiceDecrypted =decryptBySymmetricKey(data, decryptedSek);
					
					System.out.println("einvoiceDecrypted:->"+einvoiceDecrypted);
					ObjectMapper objMp = new ObjectMapper();
//					String ackNo = objMp.readTree(responseText).get("AckNo").asText();
					
//					JSONObject encryptedJson = new JSONObject(einvoiceDecrypted);
//					
//					String stringData=encryptedJson.toString();
//					System.out.println("string data:"+stringData);
					
//					String decryptedData=objMp.writeValueAsString(encryptedJson);
					
//					System.out.println("decrypted data :"+decryptedData);
					
					
					
//					System.out.println("encrypted json:"+encryptedJson);
//					long ack_no = encryptedJson.getLong("AckNo");
//					
//					String ack_date=encryptedJson.getString("AckDt");
//					
//					String irn=encryptedJson.getString("Irn");
					
			
					
					long ack_no=objMp.readTree(einvoiceDecrypted).get("AckNo").asLong();
					
					System.out.println("ack no:"+ack_no);
					
					String ack_date=objMp.readTree(einvoiceDecrypted).get("AckDt").asText();
					
					String irn=objMp.readTree(einvoiceDecrypted).get("Irn").asText();
//					
					String signedInvoice=objMp.readTree(einvoiceDecrypted).get("SignedInvoice").asText();
//					
					String signedQRCode=objMp.readTree(einvoiceDecrypted).get("SignedQRCode").asText();
					
		
					
				
					String status1=objMp.readTree(einvoiceDecrypted).get("Status").asText();
			
				
//					
					long ewbNo=objMp.readTree(einvoiceDecrypted).get("EwbNo").asLong();
//					
					String ewbDt=objMp.readTree(einvoiceDecrypted).get("EwbDt").asText();
//					
					String ewbValidTill=objMp.readTree(einvoiceDecrypted).get("EwbValidTill").asText();
					
				
					
//					String status1=objectMapper2.readTree(responseText).get("Data").get("Status").asText();
					
					
					
					System.out.println("signed invoice:"+signedInvoice);
					
//					String irn = objMp.readTree(einvoiceDecrypted).get("Irn").asText();

//					String status = objMp.readTree(einvoiceDecrypted).get("Status").asText();

					String decodedInvoice = decodeSignedJWT(signedInvoice);
					String decodedQRCode = decodeSignedJWT(signedQRCode);
					
					System.out.println("decodedInvoice"+decodedInvoice);
					System.out.println("decodedQRCode"+decodedQRCode);

					ObjectMapper newObjMp = new ObjectMapper();
					
				
					
					
					String content = newObjMp.readTree(decodedInvoice).get("data").asText();
					JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
//	
//				return jsonObject.toString();
				
//					ObjectMapper objectMapper1=new ObjectMapper();
					
		
					
					// Save details into tables
//					JSONObject content1 = new JSONObject(decodedInvoice);
//					String strData = content1.getString("data");
//					
//					EinvoiceResponseDto einvoiceResponse1 = newObjMp.readValue(content, EinvoiceResponseDto.class);
//					
//					newObjMp.readTree(decodedInvoice).get(strData);

//					
					einvoiceResponse.setIrn(irn);
					einvoiceResponse.setAckNo(ack_no);
					einvoiceResponse.setAckDt(ack_date);
					einvoiceResponse.setSignedInvoice(signedInvoice);
					einvoiceResponse.setSignedQRCode(signedQRCode);
				     einvoiceResponse.setStatus(status1);
					einvoiceResponse.setEwbNo(ewbNo);
					einvoiceResponse.setEwbDt(ewbDt);
					einvoiceResponse.setEwbValidTill(ewbValidTill);
					einvoiceResponse.setDecodedInvoice(decodedInvoice);
					einvoiceResponse.setDecodedQrCode(decodedQRCode);
					


					
					
					
					System.out.println("response data:"+einvoiceResponse);
					
//					EinvoiceData einvoiceData=einvoiceRepository.save(einvoiceDto);
					
//					String responseData=objectMapper1.writeValueAsString(einvoiceResponse);
//					----------qr generate----------------
					
		
					 

				
//			
				}
				
				return einvoiceResponse;
				
//				else
//				{
//					return jsonObj.getString("ErrorDetails");
//				}
//			
			} catch (IOException e) {

				e.printStackTrace();
			}
			return null;
		}
            
            
         
            public EinvoicePurchaseReturnDto getEncryptedData2(PurchaseReturnDto purchaseReturnDto)throws Exception,JSONException {
//	    	System.out.println(eniEinvoiceDto.getBuyerDtls()+"chittu mcfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//			String encryptData=encryptAsymmentricKey(json);
	    	

//			System.out.println("encrypt data: "+encryptData);
//			String encrptData=encryptBySymmetricKey(json, this.sek);
//			System.out.println("encode data:"+encrptData);

	    	ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = objectMapper.writeValueAsString(purchaseReturnDto);

//			EinvoiceDto einvoiceDto1=objectMapper.convertValue(einvoiceDto, EinvoiceDto.class);
//			System.out.println("read value :" +einvoiceDto1);
			System.out.println("json data :" +json);
			String gstin = "27AABCN9540N1ZY";
			GstLoginDto gstUserLoginDto = new GstLoginDto();
			ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
			gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(gstin);
			LoginDto loginDto = new LoginDto(); // loginDto is empty
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(gstUserLoginDto.getTokenExpiry()+" Asia/Calcutta", formatter);
			
			if(nowZonedDateTime.isAfter(zonedDateTime)) {
				gstUserLoginDto = loginServiceImpl.gstUserLogin(loginDto);
			}
			else 
			{
				gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(gstin);
			}
			
		
			

		
			
//			System.out.println("einvoice dto:"+einvoiceDto);
			String decryptedSek =gstUserLoginDto.getDecryptedSek();
	
			System.out.println("current sek: "+decryptedSek);
//			JSONObject jsonObj1 =new JSONObject();
//			String data= jsonObj1.getString(json);
		
//		String payload= Base64.getEncoder().encodeToString(json.getBytes());
			

//			payload = Base64.getEncoder().encodeToString(json.getBytes());
//			System.out.println("payload data:" +payload);
//			payload = Base64.getMimeEncoder().encodeToString(json.getBytes());

			String payload = "{\"Data\":\"" + encryptBySymmetricKey(json,decryptedSek)+ "\"}";
			
			System.out.println("encrypted data: "+ payload);

		
			HttpPost httpPost =new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice");
			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
			httpPost.setHeader("user_name","API_LNVERP");
//			httpPost.toString(payload,ContentType.APPLICATION_JSON);
			httpPost.setHeader("AuthToken",this.authtoken);
//			httpPost.setEntity(payload);
//			httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		// Change Authtoken here
//		    String contentType = "text/plain;charset=utf-8";
		    
//			httpPost.setEntity(new StringEntity(payload,ContentType.create(contentType)));
			StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_JSON);

			httpPost.setEntity(entity);
			System.out.println("entity" +entity);
			
	

			CloseableHttpClient httpClient = HttpClients.createDefault();
	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

			try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			String responseText ="";
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("responseText: "+responseText);
	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

			JSONObject jsonObj = new JSONObject(responseText);
			
			int status = jsonObj.getInt("Status");
			String data = jsonObj.getString("Data");
//			System.out.println("Response:" + responseText);
			
		

	
			System.out.println("status"+status);
//			System.out.println("Data"+data);
				

		
			System.out.println("not null:" +jsonObj);
////			

			System.out.println("data chittu: "+data);
//			String status = jsonObj.getString("Status");

				
	
			EinvoicePurchaseReturnDto einvoiceResponse=new EinvoicePurchaseReturnDto();
				if(status==1) {
			
				
//			
//					String data=objectMapper.readTree(responseText).get("Data").asText();
					String einvoiceDecrypted =decryptBySymmetricKey(data, decryptedSek);
					
					System.out.println("einvoiceDecrypted:->"+einvoiceDecrypted);
					ObjectMapper objMp = new ObjectMapper();
//					String ackNo = objMp.readTree(responseText).get("AckNo").asText();
					
//					JSONObject encryptedJson = new JSONObject(einvoiceDecrypted);
//					
//					String stringData=encryptedJson.toString();
//					System.out.println("string data:"+stringData);
					
//					String decryptedData=objMp.writeValueAsString(encryptedJson);
					
//					System.out.println("decrypted data :"+decryptedData);
					
					
					
//					System.out.println("encrypted json:"+encryptedJson);
//					long ack_no = encryptedJson.getLong("AckNo");
//					
//					String ack_date=encryptedJson.getString("AckDt");
//					
//					String irn=encryptedJson.getString("Irn");
					
			
					
					long ack_no=objMp.readTree(einvoiceDecrypted).get("AckNo").asLong();
					
					System.out.println("ack no:"+ack_no);
					
					String ack_date=objMp.readTree(einvoiceDecrypted).get("AckDt").asText();
					
					String irn=objMp.readTree(einvoiceDecrypted).get("Irn").asText();
//					
					String signedInvoice=objMp.readTree(einvoiceDecrypted).get("SignedInvoice").asText();
//					
					String signedQRCode=objMp.readTree(einvoiceDecrypted).get("SignedQRCode").asText();
					
		
					
				
					String status1=objMp.readTree(einvoiceDecrypted).get("Status").asText();
			
				
//					
//					long ewbNo=objMp.readTree(einvoiceDecrypted).get("EwbNo").asLong();
////					
//					String ewbDt=objMp.readTree(einvoiceDecrypted).get("EwbDt").asText();
////					
//					String ewbValidTill=objMp.readTree(einvoiceDecrypted).get("EwbValidTill").asText();
					
				
					
//					String status1=objectMapper2.readTree(responseText).get("Data").get("Status").asText();
					
					
					
					System.out.println("signed invoice:"+signedInvoice);
					
//					String irn = objMp.readTree(einvoiceDecrypted).get("Irn").asText();

//					String status = objMp.readTree(einvoiceDecrypted).get("Status").asText();

					String decodedInvoice = decodeSignedJWT(signedInvoice);
					String decodedQRCode = decodeSignedJWT(signedQRCode);
					
					System.out.println("decodedInvoice"+decodedInvoice);
					System.out.println("decodedQRCode"+decodedQRCode);

					ObjectMapper newObjMp = new ObjectMapper();
					
				
					
					
					String content = newObjMp.readTree(decodedInvoice).get("data").asText();
					JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
//	
//				return jsonObject.toString();
				
//					ObjectMapper objectMapper1=new ObjectMapper();
					
		
					
					// Save details into tables
//					JSONObject content1 = new JSONObject(decodedInvoice);
//					String strData = content1.getString("data");
//					
//					EinvoiceResponseDto einvoiceResponse1 = newObjMp.readValue(content, EinvoiceResponseDto.class);
//					
//					newObjMp.readTree(decodedInvoice).get(strData);

//					
					einvoiceResponse.setIrn(irn);
					einvoiceResponse.setAckNo(ack_no);
					einvoiceResponse.setAckDt(ack_date);
					einvoiceResponse.setSignedInvoice(signedInvoice);
					einvoiceResponse.setSignedQRCode(signedQRCode);
				     einvoiceResponse.setStatus(status1);
//					einvoiceResponse.setEwbNo(ewbNo);
//					einvoiceResponse.setEwbDt(ewbDt);
//					einvoiceResponse.setEwbValidTill(ewbValidTill);
					einvoiceResponse.setDecodedInvoice(decodedInvoice);
					einvoiceResponse.setDecodedQrCode(decodedQRCode);
					


					
					
					
					System.out.println("response data:"+einvoiceResponse);
					
//					EinvoiceData einvoiceData=einvoiceRepository.save(einvoiceDto);
					
//					String responseData=objectMapper1.writeValueAsString(einvoiceResponse);
//					----------qr generate----------------
					
		
					 

				
//			
				}
				
				return einvoiceResponse;
				
//				else
//				{
//					return jsonObj.getString("ErrorDetails");
//				}
//			
			} catch (IOException e) {

				e.printStackTrace();
			}
			return null;
		}         
            
      
            public EinvoiceSalesReturnDto getEncryptedData1(SalesReturnDto salesReturnDto)throws Exception,JSONException {
//    	    	System.out.println(eniEinvoiceDto.getBuyerDtls()+"chittu mcfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
    	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//    			String encryptData=encryptAsymmentricKey(json);
    	    	

//    			System.out.println("encrypt data: "+encryptData);
//    			String encrptData=encryptBySymmetricKey(json, this.sek);
//    			System.out.println("encode data:"+encrptData);

    	    	ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
    			String json = objectMapper.writeValueAsString(salesReturnDto);

//    			EinvoiceDto einvoiceDto1=objectMapper.convertValue(einvoiceDto, EinvoiceDto.class);
//    			System.out.println("read value :" +einvoiceDto1);
    			System.out.println("json data :" +json);
    			String gstin = "27AABCN9540N1ZY";
    			GstLoginDto gstUserLoginDto = new GstLoginDto();
    			ZonedDateTime nowZonedDateTime = ZonedDateTime.now();
    			gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(gstin);
    			LoginDto loginDto = new LoginDto(); // loginDto is empty
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    			ZonedDateTime zonedDateTime = ZonedDateTime.parse(gstUserLoginDto.getTokenExpiry()+" Asia/Calcutta", formatter);
    			
    			if(nowZonedDateTime.isAfter(zonedDateTime)) {
    				gstUserLoginDto = loginServiceImpl.gstUserLogin(loginDto);
    			}
    			else 
    			{
    				gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(gstin);
    			}
    			
    		
    			

    		
    			
//    			System.out.println("einvoice dto:"+einvoiceDto);
    			String decryptedSek =gstUserLoginDto.getDecryptedSek();
    	
    			System.out.println("current sek: "+decryptedSek);
//    			JSONObject jsonObj1 =new JSONObject();
//    			String data= jsonObj1.getString(json);
    		
//    		String payload= Base64.getEncoder().encodeToString(json.getBytes());
    			

//    			payload = Base64.getEncoder().encodeToString(json.getBytes());
//    			System.out.println("payload data:" +payload);
//    			payload = Base64.getMimeEncoder().encodeToString(json.getBytes());

    			String payload = "{\"Data\":\"" + encryptBySymmetricKey(json,decryptedSek)+ "\"}";
    			
    			System.out.println("encrypted data: "+ payload);

    		
    			HttpPost httpPost =new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice");
    			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
    			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
    			httpPost.setHeader("user_name","API_LNVERP");
//    			httpPost.toString(payload,ContentType.APPLICATION_JSON);
    			httpPost.setHeader("AuthToken",this.authtoken);
//    			httpPost.setEntity(payload);
//    			httpPost.setHeader("Content-type", "application/json; charset=utf-8");
    		// Change Authtoken here
//    		    String contentType = "text/plain;charset=utf-8";
    		    
//    			httpPost.setEntity(new StringEntity(payload,ContentType.create(contentType)));
    			StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_JSON);

    			httpPost.setEntity(entity);
    			System.out.println("entity" +entity);
    			
    	

    			CloseableHttpClient httpClient = HttpClients.createDefault();
    	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

    			try {
                CloseableHttpResponse response = httpClient.execute(httpPost);
    			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    			String output;
    			String responseText ="";
    			while ((output = br.readLine()) != null) {
    				responseText = output;
    			}
    			System.out.println("responseText: "+responseText);
    	    	System.out.println("status sahil+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");

    			JSONObject jsonObj = new JSONObject(responseText);
    			
    			int status = jsonObj.getInt("Status");
    			String data = jsonObj.getString("Data");
//    			System.out.println("Response:" + responseText);
    			
    		

    	
    			System.out.println("status"+status);
//    			System.out.println("Data"+data);
    				

    		
    			System.out.println("not null:" +jsonObj);
////    			

    			System.out.println("data chittu: "+data);
//    			String status = jsonObj.getString("Status");

    				
    	
    			EinvoiceSalesReturnDto einvoiceResponse=new EinvoiceSalesReturnDto();
    				if(status==1) {
    			
    				
//    			
//    					String data=objectMapper.readTree(responseText).get("Data").asText();
    					String einvoiceDecrypted =decryptBySymmetricKey(data, decryptedSek);
    					
    					System.out.println("einvoiceDecrypted:->"+einvoiceDecrypted);
    					ObjectMapper objMp = new ObjectMapper();
//    					String ackNo = objMp.readTree(responseText).get("AckNo").asText();
    					
//    					JSONObject encryptedJson = new JSONObject(einvoiceDecrypted);
//    					
//    					String stringData=encryptedJson.toString();
//    					System.out.println("string data:"+stringData);
    					
//    					String decryptedData=objMp.writeValueAsString(encryptedJson);
    					
//    					System.out.println("decrypted data :"+decryptedData);
    					
    					
    					
//    					System.out.println("encrypted json:"+encryptedJson);
//    					long ack_no = encryptedJson.getLong("AckNo");
//    					
//    					String ack_date=encryptedJson.getString("AckDt");
//    					
//    					String irn=encryptedJson.getString("Irn");
    					
    			
    					
    					long ack_no=objMp.readTree(einvoiceDecrypted).get("AckNo").asLong();
    					
    					System.out.println("ack no:"+ack_no);
    					
    					String ack_date=objMp.readTree(einvoiceDecrypted).get("AckDt").asText();
    					
    					String irn=objMp.readTree(einvoiceDecrypted).get("Irn").asText();
//    					
    					String signedInvoice=objMp.readTree(einvoiceDecrypted).get("SignedInvoice").asText();
//    					
    					String signedQRCode=objMp.readTree(einvoiceDecrypted).get("SignedQRCode").asText();
    					
    		
    					
    				
    					String status1=objMp.readTree(einvoiceDecrypted).get("Status").asText();
    			
    				
//    					
//    					long ewbNo=objMp.readTree(einvoiceDecrypted).get("EwbNo").asLong();
////    					
//    					String ewbDt=objMp.readTree(einvoiceDecrypted).get("EwbDt").asText();
////    					
//    					String ewbValidTill=objMp.readTree(einvoiceDecrypted).get("EwbValidTill").asText();
    					
    				
    					
//    					String status1=objectMapper2.readTree(responseText).get("Data").get("Status").asText();
    					
    					
    					
    					System.out.println("signed invoice:"+signedInvoice);
    					
//    					String irn = objMp.readTree(einvoiceDecrypted).get("Irn").asText();

//    					String status = objMp.readTree(einvoiceDecrypted).get("Status").asText();

    					String decodedInvoice = decodeSignedJWT(signedInvoice);
    					String decodedQRCode = decodeSignedJWT(signedQRCode);
    					
    					System.out.println("decodedInvoice"+decodedInvoice);
    					System.out.println("decodedQRCode"+decodedQRCode);

    					ObjectMapper newObjMp = new ObjectMapper();
    					
    				
    					
    					
    					String content = newObjMp.readTree(decodedInvoice).get("data").asText();
    					JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
    //	
//    				return jsonObject.toString();
    				
//    					ObjectMapper objectMapper1=new ObjectMapper();
    					
    		
    					
    					// Save details into tables
//    					JSONObject content1 = new JSONObject(decodedInvoice);
//    					String strData = content1.getString("data");
//    					
//    					EinvoiceResponseDto einvoiceResponse1 = newObjMp.readValue(content, EinvoiceResponseDto.class);
//    					
//    					newObjMp.readTree(decodedInvoice).get(strData);

//    					
    					einvoiceResponse.setIrn(irn);
    					einvoiceResponse.setAckNo(ack_no);
    					einvoiceResponse.setAckDt(ack_date);
    					einvoiceResponse.setSignedInvoice(signedInvoice);
    					einvoiceResponse.setSignedQRCode(signedQRCode);
    				     einvoiceResponse.setStatus(status1);
//    					einvoiceResponse.setEwbNo(ewbNo);
//    					einvoiceResponse.setEwbDt(ewbDt);
//    					einvoiceResponse.setEwbValidTill(ewbValidTill);
    					einvoiceResponse.setDecodedInvoice(decodedInvoice);
    					einvoiceResponse.setDecodedQrCode(decodedQRCode);
    					


    					
    					
    					
    					System.out.println("response data:"+einvoiceResponse);
    					
//    					EinvoiceData einvoiceData=einvoiceRepository.save(einvoiceDto);
    					
//    					String responseData=objectMapper1.writeValueAsString(einvoiceResponse);
//    					----------qr generate----------------
    					
    		
    					 

    				
//    			
    				}
    				
    				return einvoiceResponse;
    				
//    				else
//    				{
//    					return jsonObj.getString("ErrorDetails");
//    				}
//    			
    			} catch (IOException e) {

    				e.printStackTrace();
    			}
    			return null;
    		}
            
            
            
            
            
            
            
	    
	    
		private EinvoiceResponseDto stringToDto(String content) {
			ObjectMapper newObjMp = new ObjectMapper();
			try {
				String ackNo = newObjMp.readTree(content).get("AckNo").asText();
//				JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
				
			} catch (JsonProcessingException e) {
				
			}
			return null;
		}
	    
		
		
		public CancelEinvoiceDto cancelInvoice(EinvoiceCancelDto cancelDto) throws JsonProcessingException, JSONException, Exception
		{
			ObjectWriter objectMapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
			
			String json=objectMapper.writeValueAsString(cancelDto);
			
			System.out.println("json data:"+json);
		
		    GstLoginDto gstUserLoginDto=new GstLoginDto();
		    gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
		  String decryptedSek= gstUserLoginDto.getDecryptedSek();
		    
		    System.out.println("decrptred sek: "+decryptedSek);
		    
		    HttpPost httpPost=new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice/Cancel");
		    
			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
			httpPost.setHeader("user_name","API_LNVERP");
			httpPost.setHeader("AuthToken",this.authtoken);
			
			String payload =encryptBySymmetricKey(json, decryptedSek);
			
		 payload = "{\"Data\":\"" + payload + "\"}";	
		 System.out.println("encrypted data :"+ payload);
			
			StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			String responseText ="";
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("responseText: "+responseText);
			
			JSONObject jsonObj = new JSONObject(responseText);
			
			int status = jsonObj.getInt("Status");
			String data = jsonObj.getString("Data");
			
			ObjectMapper objectMapper2=new ObjectMapper();
		CancelEinvoiceDto cancelEinvoiceDto=new CancelEinvoiceDto();
			if(status==1)
			{
				String decryptedData=decryptBySymmetricKey(data, decryptedSek);
				
				System.out.println("decrypted data: "+decryptedData);
				
				String irn=objectMapper2.readTree(decryptedData).get("Irn").asText();
				
				String cancelDate=objectMapper2.readTree(decryptedData).get("CancelDate").asText();
				
				
				
				
				JsonObject jsonObject = JsonParser.parseString(decryptedData).getAsJsonObject();
				
				
				cancelEinvoiceDto.setIrn(irn);
				cancelEinvoiceDto.setCancelDate(cancelDate);
			cancelEinvoiceDto.setStatus("CNL");
	
				
//				cancelResponseDto.setIrn(irn);
//				cancelResponseDto.setCancelDate(cancelDate);
				
			}
			
			return cancelEinvoiceDto;
			
	
			}
			catch (IOException e) {

				Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, e);
				}
			return null;
			
		
		
			
		
	
		}
		
		
		
		public CancelEinvoiceSalesReturnDto cancelInvoiceSalesReturn(EinvoiceCancelDto cancelDto) throws JsonProcessingException, JSONException, Exception
		{
			ObjectWriter objectMapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
			
			String json=objectMapper.writeValueAsString(cancelDto);
			
			System.out.println("json data:"+json);
		
		    GstLoginDto gstUserLoginDto=new GstLoginDto();
		    gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
		  String decryptedSek= gstUserLoginDto.getDecryptedSek();
		    
		    System.out.println("decrptred sek: "+decryptedSek);
		    
		    HttpPost httpPost=new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice/Cancel");
		    
			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
			httpPost.setHeader("user_name","API_LNVERP");
			httpPost.setHeader("AuthToken",this.authtoken);
			
			String payload =encryptBySymmetricKey(json, decryptedSek);
			
		 payload = "{\"Data\":\"" + payload + "\"}";	
		 System.out.println("encrypted data :"+ payload);
			
			StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			String responseText ="";
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("responseText: "+responseText);
			
			JSONObject jsonObj = new JSONObject(responseText);
			
			int status = jsonObj.getInt("Status");
			String data = jsonObj.getString("Data");
			
			ObjectMapper objectMapper2=new ObjectMapper();
			CancelEinvoiceSalesReturnDto cancelEinvoiceSalesReturnDto=new CancelEinvoiceSalesReturnDto();
			if(status==1)
			{
				String decryptedData=decryptBySymmetricKey(data, decryptedSek);
				
				System.out.println("decrypted data: "+decryptedData);
				
				String irn=objectMapper2.readTree(decryptedData).get("Irn").asText();
				
				String cancelDate=objectMapper2.readTree(decryptedData).get("CancelDate").asText();
				
				
				
				
				JsonObject jsonObject = JsonParser.parseString(decryptedData).getAsJsonObject();
				
				
				cancelEinvoiceSalesReturnDto.setIrn(irn);
				cancelEinvoiceSalesReturnDto.setCancelDate(cancelDate);
				cancelEinvoiceSalesReturnDto.setStatus("CNL");
	
				
//				cancelResponseDto.setIrn(irn);
//				cancelResponseDto.setCancelDate(cancelDate);
				
			}
			
			return cancelEinvoiceSalesReturnDto;
			
	
			}
			catch (IOException e) {

				Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, e);
				}
			return null;
			
		
		
			
		
	
		}	
		
		
		
		public CancelPurchaseReturnDto cancelInvoicePurchaseReturn(EinvoiceCancelDto cancelDto) throws JsonProcessingException, JSONException, Exception
		{
			ObjectWriter objectMapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
			
			String json=objectMapper.writeValueAsString(cancelDto);
			
			System.out.println("json data:"+json);
		
		    GstLoginDto gstUserLoginDto=new GstLoginDto();
		    gstUserLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
		  String decryptedSek= gstUserLoginDto.getDecryptedSek();
		    
		    System.out.println("decrptred sek: "+decryptedSek);
		    
		    HttpPost httpPost=new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice/Cancel");
		    
			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
			httpPost.setHeader("user_name","API_LNVERP");
			httpPost.setHeader("AuthToken",this.authtoken);
			
			String payload =encryptBySymmetricKey(json, decryptedSek);
			
		 payload = "{\"Data\":\"" + payload + "\"}";	
		 System.out.println("encrypted data :"+ payload);
			
			StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String output;
			String responseText ="";
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("responseText: "+responseText);
			
			JSONObject jsonObj = new JSONObject(responseText);
			
			int status = jsonObj.getInt("Status");
			String data = jsonObj.getString("Data");
			
			ObjectMapper objectMapper2=new ObjectMapper();
			CancelPurchaseReturnDto cancelPurchaseReturnDto=new CancelPurchaseReturnDto();
			if(status==1)
			{
				String decryptedData=decryptBySymmetricKey(data, decryptedSek);
				
				System.out.println("decrypted data: "+decryptedData);
				
				String irn=objectMapper2.readTree(decryptedData).get("Irn").asText();
				
				String cancelDate=objectMapper2.readTree(decryptedData).get("CancelDate").asText();
				
				
				
				
				JsonObject jsonObject = JsonParser.parseString(decryptedData).getAsJsonObject();
				
				
				cancelPurchaseReturnDto.setIrn(irn);
				cancelPurchaseReturnDto.setCancelDate(cancelDate);
				cancelPurchaseReturnDto.setStatus("CNL");
	
				
//				cancelResponseDto.setIrn(irn);
//				cancelResponseDto.setCancelDate(cancelDate);
				
			}
			
			return cancelPurchaseReturnDto;
			
	
			}
			catch (IOException e) {

				Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, e);
				}
			return null;
			
		
		
			
		
	
		}	
		
		
		
		
		
		
		
		
		 
		
	public EwayBillByIrnDto EwayBillGenerate(EwayBillLoginDto billLoginDto) throws JsonProcessingException, JSONException,Exception
		{
	ObjectWriter objectMapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
			
	String json=objectMapper.writeValueAsString(billLoginDto);
	
	GstLoginDto gstUserLoginDto=new GstLoginDto();
	
	gstUserLoginDto=gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
	
	String decryptedSek=gstUserLoginDto.getDecryptedSek();
	
	System.out.println("decrpted sek: "+decryptedSek);
	
	HttpPost httpPost=new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eiewb/v1.03/ewaybill");
	
	httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
	httpPost.setHeader("Gstin","27AABCN9540N1ZY");
	httpPost.setHeader("user_name","API_LNVERP");
	httpPost.setHeader("AuthToken",this.authtoken);
	
	String payload=encryptBySymmetricKey(json, decryptedSek);
	
	 payload = "{\"Data\":\"" + payload + "\"}";	
	 
	 System.out.println("encrypted Data: "+payload);
	
	StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
	
	httpPost.setEntity(stringEntity);
	
	CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
	
	try
	{
		CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpPost);
		
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
		
		String output;
		
		String responseText="";
		
		while ((output = bufferedReader.readLine()) != null) {
			responseText = output;
		}
		
		System.out.println("responseText: "+responseText);
		
		JSONObject jsonObject=new JSONObject(responseText);
		
		String data=jsonObject.getString("Data");
		
		System.out.println("response Data :"+data);
		
		int status=jsonObject.getInt("Status");
		
		System.out.println("response status:" +status);
		
	
		EwayBillByIrnDto billByIrnDto=new EwayBillByIrnDto();
		if(status==1)
		{
			String decryptedData=decryptBySymmetricKey(data, decryptedSek);
			
			System.out.println("decrypted data:"+decryptedData);
			
		
			
			ObjectMapper mapper=new ObjectMapper();
			
			long ewbNo=mapper.readTree(decryptedData).get("EwbNo").asLong();
//			
			String ewbDt=mapper.readTree(decryptedData).get("EwbDt").asText();
//			
			String ewbValidTill=mapper.readTree(decryptedData).get("EwbValidTill").asText();
			
			JsonObject jsonObject2=JsonParser.parseString(decryptedData).getAsJsonObject();
			
			
			billByIrnDto.setEwbNo(ewbNo);
			billByIrnDto.setEwbDt(ewbDt);
			billByIrnDto.setEwbValidTill(ewbValidTill);
			billByIrnDto.setStatus("ACT");
			
		
			
		}
		return billByIrnDto;
	
	}
	
	catch (IOException e) {

		Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, e);
		}
	return null;
		}
		
		
		
		public GetEinvoiceDto getEinvoiceByIrn(String irn) throws JSONException,Exception
		{
			GstLoginDto gstLoginDto=new GstLoginDto();
			gstLoginDto = gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
			String decryptedSek=gstLoginDto.getDecryptedSek();
			
			System.out.println("decrypted sek:"+decryptedSek);
			
			String url = "https://www.alankitgst.com/eInvoiceGateway/eicore/v1.03/Invoice/irn/"+irn;
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpGet.setHeader("Gstin","27AABCN9540N1ZY");
			httpGet.setHeader("user_name","API_LNVERP");
			httpGet.setHeader("AuthToken",this.authtoken);// Change Authtoken here
			CloseableHttpClient client = HttpClients.createDefault();
			try {
				CloseableHttpResponse response = client.execute(httpGet);
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String output;
				String responseText ="";
				while((output = br.readLine()) != null) {
					responseText = output;
				}
				JSONObject jsonObj =new JSONObject(responseText);
				System.out.println(jsonObj);
				int status=jsonObj.getInt("Status");
				

			GetEinvoiceDto einvoiceDto=new GetEinvoiceDto();
				if(status==1) {
					String data = jsonObj.getString("Data");
					String encryptedDetails=encryptBySymmetricKey(data, decryptedSek);
					System.out.println("enccypted data:"+encryptedDetails);
					String decryptedDetails = decryptBySymmetricKey(data,decryptedSek);
					
					ObjectMapper objectMapper=new ObjectMapper();
					
	               long ack_no=objectMapper.readTree(decryptedDetails).get("AckNo").asLong();
					
					System.out.println("ack no:"+ack_no);
					
					String ack_date=objectMapper.readTree(decryptedDetails).get("AckDt").asText();
					
					String irn1=objectMapper.readTree(decryptedDetails).get("Irn").asText();
//					
					String signedInvoice=objectMapper.readTree(decryptedDetails).get("SignedInvoice").asText();
//					
					String signedQRCode=objectMapper.readTree(decryptedDetails).get("SignedQRCode").asText();
					
		
					
				
					String status1=objectMapper.readTree(decryptedDetails).get("Status").asText();
			
				
//					
					long ewbNo=objectMapper.readTree(decryptedDetails).get("EwbNo").asLong();
//					
					String ewbDt=objectMapper.readTree(decryptedDetails).get("EwbDt").asText();
//					
					String ewbValidTill=objectMapper.readTree(decryptedDetails).get("EwbValidTill").asText();
					
					
					
					JsonObject jsonObject = JsonParser.parseString(decryptedDetails).getAsJsonObject();
					
					
					einvoiceDto.setAckNo(ack_no);
					einvoiceDto.setAckDt(ack_date);
					einvoiceDto.setIrn(irn1);
					einvoiceDto.setSignedInvoice(signedInvoice);
					einvoiceDto.setSignedQRCode(signedQRCode);
					einvoiceDto.setStatus(status1);
					einvoiceDto.setEwbNo(ewbNo);
					einvoiceDto.setEwbDt(ewbDt);
					einvoiceDto.setEwbValidTill(ewbValidTill);
					
				}
				
				return einvoiceDto;
				}
			
			catch (IOException e) {

				Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, e);
				}
			return null;
				}
		
		


		
		public EwayBillCancelResponseDto cancelEwayBill(EwayBillCancelDto ewayBillCancelDto) throws JSONException, IOException, Exception
		{
	        ObjectWriter objectMapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
			
			String json=objectMapper.writeValueAsString(ewayBillCancelDto);
			
			System.out.println("json data:"+json);
			
            GstLoginDto gstLoginDto=new GstLoginDto();
			
			gstLoginDto=gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
			
			String decryptedSek=gstLoginDto.getDecryptedSek();
			
			System.out.println("decrypted sek: "+decryptedSek);
			
			HttpPost httpPost=new HttpPost("https://www.alankitgst.com/eInvoiceGateway/v1.03/ewayapi");
			
			httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
			httpPost.setHeader("Gstin","27AABCN9540N1ZY");
			httpPost.setHeader("user_name","API_LNVERP");
			httpPost.setHeader("AuthToken",this.authtoken);
			
			String payload=encryptBySymmetricKey(json, decryptedSek);
			
			String action="CANEWB";
			
			
			payload="{\"Action\":\"" + action + "\" ,\"Data\":\"" + payload + "\"}";
			
			System.out.println("payload data:"+payload);
			
			StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
			
			httpPost.setEntity(stringEntity);
			
			CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
			try
			{
				CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpPost);
				
				BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
						
				String ouput;
				
				String responseText="";
				
				while((ouput=bufferedReader.readLine()) != null)
				{
					responseText=ouput;
				}
				System.out.println("responseText: "+responseText);
				
				JSONObject jsonObject=new JSONObject(responseText);
				
				System.out.println("json object: "+jsonObject);
				
				int status=jsonObject.getInt("status");
				
				System.out.println("sataus: "+status);
				
				String data=jsonObject.getString("data");
				
				System.out.println("response data: " + data);
				
				EwayBillCancelResponseDto billCancelResponseDto=new EwayBillCancelResponseDto();
				if(status==1)
						{
					String decryptedData=decryptBySymmetricKey(data, decryptedSek);
					
			System.out.println("decrypted data:" + decryptedData);
					
ObjectMapper objectMapper2=new ObjectMapper();
					
				
					
					long ewbNo=objectMapper2.readTree(decryptedData).get("ewayBillNo").asLong();
					
					String cancelDate=objectMapper2.readTree(decryptedData).get("cancelDate").asText();
					
                    billCancelResponseDto.setEwayBillNo(ewbNo);
					billCancelResponseDto.setCancelDate(cancelDate);
					billCancelResponseDto.setStatus("CNL");
					
				return billCancelResponseDto;
					
						}
		
			}
			catch (JSONException e) {

				Logger.getLogger(EinvoiceAuth.class.getName()).log(Level.SEVERE, null, e);
				}
			return null;
				}
		

		public String getEwayBillByIrn(String irn)
		{
			GstLoginDto gstLoginDto=new GstLoginDto();
			
			gstLoginDto=gstUserLoginServiceImpl.getGstUserLoginByGstin(Gstin);
			
			String decryptedSek=gstLoginDto.getDecryptedSek();
			
			System.out.println("Decrypted Sek:"+decryptedSek);
			
			HttpGet httpGet=new HttpGet("https://www.alankitgst.com/eInvoiceGateway/eiewb/v1.03/ewaybill/irn/"+irn);
			
			httpGet.setHeader("Ocp-Apim-Subscription-Key", "AL5i1p6T7z1D9K7z3d");
			httpGet.setHeader("Gstin","27AABCN9540N1ZY");
			httpGet.setHeader("user_name","API_LNVERP");
			httpGet.setHeader("AuthToken",this.authtoken);
			
			CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
			
			try
			{
				CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpGet);
				
				BufferedReader reader=new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
				
				String output;
				
				String responseText="";
				
				while((output=reader.readLine())!=null)
				{
					responseText=output;
				}
				
				JSONObject jsonObject=new JSONObject(responseText);
				
			
				
				String data=jsonObject.getString("Data");
				
				System.out.println("Data:"+ data);
				
				int status=jsonObject.getInt("Status");
				
				System.out.println("Status: "+ status);
			
				if(status==1)
				{
					String decryptedData=decryptBySymmetricKey(data, decryptedSek);
					
					System.out.println("decrypted data: "+ decryptedData);
					
					JsonObject jsonObject2=JsonParser.parseString(decryptedData).getAsJsonObject();
					
					return jsonObject2.toString();
					
				}
				else
				{
					return jsonObject.getString("ErrorDetails");
				}
				
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
			
			
		}
		
		


		
		

}
