package com.SCM.serviceimpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstLoginUserDto.LoginDto;
import com.SCM.service.GstLoginService;
import com.SCM.service.LoginService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoginServiceImpl implements LoginService {
//	static byte[] secretKey = generateAESKey();
//	static String strSecretKey = Base64.getEncoder().encodeToString(secretKey);

	private static String app_key ="/gezwTOgAwvW2fmu3nXDSoTHW/DmtbllsVM5skshn9k=";
	
	static String strSecretKey = app_key;
//	static String appKeyString = "[B@8ba1a1d";
//	static byte[] appKey = appKeyString.getBytes();
	
	
	@Autowired
	GstLoginService gstUserLoginService;
	
	   private static String publickeylocationString;
	   

	   private Path publickeylocation;

	 
	   
	    @Value("${FILE_PATH}")
	    private String FILE_PATH;
	
	GstLoginDto gstUserLoginDto = new GstLoginDto();	
	
	
	 @Autowired
	   	public LoginServiceImpl(Environment env) throws IOException {
	   	
//	   		this.publickeylocation = Paths.get(env.getProperty("app.file.upload-dir1","einv_production.pem")).toAbsolutePath()
//	   				.normalize();
//	   		Files.createDirectories(this.publickeylocation);
//	   		System.out.println("publickeylocation" + publickeylocation);
//	   		this.publickeylocationString = Paths.get(env.getProperty("app.file.upload-dir1","einv_production.pem")).toAbsolutePath()
//	   				.normalize().toString();
//	    	this.publickeylocation = Paths.get(env.getProperty("app.file.upload-dir1","/einv_production.pem")).toAbsolutePath()
//	   				.normalize();
////	   		Files.createDirectories(this.publickeylocation);
//	   		System.out.println("publickeylocation" + publickeylocation);
//	   		this.publickeylocationString = publickeylocation.toString();
//			
//	   		System.out.println("publickeylocationString" + publickeylocationString);
	    	try {
	    		
	    String relativePath = env.getProperty("app.file.upload-dir1","/einv_production.pem");
	        Path relativeDirectory = Paths.get(relativePath).toAbsolutePath().normalize();
	        
	        // Construct the full file path for the public key
	        this.publickeylocation = relativeDirectory.resolve("einv_production.pem");
	        

	       
	        // Ensure the file exists and is readable
//	        if (!Files.exists(this.publickeylocation) || !Files.isReadable(this.publickeylocation)) {
//	            throw new IOException("File not accessible: " + this.publickeylocation);
//	        }
	        
	        this.publickeylocationString = this.publickeylocation.toString();
	        System.out.println("publickeylocationString: " + publickeylocationString);
	        
	        

	        
	        
	  }
	    	
	    	catch (Exception e) {
			e.printStackTrace();
			}
	   		
	 }
	
	
	
	
	

	public GstLoginDto gstUserLogin(LoginDto loginDto) throws Exception {
//		String UserName = loginDto.getUserName().toString();
//		String Password = loginDto.getPassword().toString();
		String authtoken ="";
		String sek="";
		String decryptedSek="";
		String tokenExpiry ="";
		String clientId ="";
		String UserName = "API_LNVERP"; // username given by Alankint
		String Password = "Lnverp@123"; // Password given by Alankit
		String gstin = "27AABCN9540N1ZY";
		ObjectMapper objectMapper = new ObjectMapper();
		GstLoginDto newGstUserLoginDto = new GstLoginDto();
		boolean ForceRefreshAccessToken = loginDto.isForceRefreshAccessToken();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		
		String appKey = strSecretKey;
		System.out.println("appKey"+appKey);
//		String appKey = "uWHGf+vhQWCTgaZirD6ayg==";
//		String appKey = "s5W/W2gjV+hCk/XjBhdG105ehsucKBXBc/5JwWz2l9U="; // shitij appkey
//		String payload = "{\"UserName\":\""+UserName+"\",\"Password\":\"" + Password + "\",\"appKey\":\""+appKey+"\",\"ForceRefreshAccessToken\": true}";
		String payload = "{\"UserName\":\""+UserName+"\",\"Password\":\"" + Password + "\",\"appKey\":\""+appKey+"\",\"ForceRefreshAccessToken\": true}";
		System.out.println("PAYLOAD: "+ payload);
		payload = Base64.getEncoder().encodeToString(payload.getBytes());
		payload = "{\"Data\":\"" + encryptAsymetricKey(payload) + "\"}";
//		System.out.println("Payload: Encrypted: " + payload);
//		HttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost("https://www.alankitgst.com/eInvoiceGateway/eivital/v1.04/auth");
//		httpPost.setHeader("client-id","<Client id>");
//		httpPost.setHeader("client-secret","Client Secret");
//		httpPost.setHeader("gstin","gstin");
//		httpPost.setHeader("KeepAlive","true");
//		httpPost.setHeader("AllowAutoRedirect","false");
		httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
		httpPost.setHeader("Gstin","27AABCN9540N1ZY");
		
		
		StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_JSON);
		
		httpPost.setEntity(entity);
		
//		HttpResponse response = httpClient.executeOpen(null, httpPost, null); // execute deprecated find other method
		CloseableHttpResponse response = httpClient.execute(httpPost);		
		
		if(response.getCode() != 200) {
//			System.out.println(response);
			throw new RuntimeException("Failed : HTTP error code : " + response.getCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String output;
		String responseText ="";
		while((output = br.readLine()) != null) {
			responseText = output;
		}
		System.out.println("Response:"+responseText);
		String status = objectMapper.readTree(responseText).get("Status").asText();
		if(status.equals("0")) {
			String errDesc = "";
			errDesc = objectMapper.readTree(responseText).get("error").asText();
//			System.out.println("Error: " + errDesc);
		}
		
		
		if(status.equals("1")) {
			if(gstUserLoginService.getGstUserLoginByGstin(gstin) != null) {
				gstUserLoginService.deleteGstUserLoginByGstin(gstin);
			}
			authtoken = objectMapper.readTree(responseText).get("Data").get("AuthToken").asText();
			sek = objectMapper.readTree(responseText).get("Data").get("Sek").asText();
			tokenExpiry = objectMapper.readTree(responseText).get("Data").get("TokenExpiry").asText();
			clientId = objectMapper.readTree(responseText).get("Data").get("ClientId").asText();
//			System.out.println("Authtoken: " + authtoken);
//			System.out.println("Encrypted SEK: " + sek);
			decryptedSek = decryptBySymmetricKeySEK(sek);
//			System.out.println("Decrypted SEK: " + decryptedSek);
//			SecurityContextHolder.getContext().getAuthentication().getCredentials(); 
//			System.out.println(SecurityContextHolder.getContext().getAuthentication());
			gstUserLoginDto.setEncryptedSek(sek);
			gstUserLoginDto.setDecryptedSek(decryptedSek);
			gstUserLoginDto.setAuthToken(authtoken);
			
			gstUserLoginDto.setTokenExpiry(tokenExpiry);
			gstUserLoginDto.setClientId(clientId);
			gstUserLoginDto.setUserName(UserName);
			gstUserLoginDto.setGstin(gstin);
			
			System.out.println("appkeyDTO: "+appKey);
			newGstUserLoginDto = gstUserLoginService.saveToken(gstUserLoginDto);
			
		}
		
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(new URI("https://developers.eraahi.com/eInvoiceGateway/eivital/v1.04/auth"))
////				.headers("client-id", "<Client Id>","client-secret", "Client Secret","gstin", "GSTIN","KeepAlive", "true","AllowAutoRedirect", "false")
//				.headers("Ocp-Apim-Subscription-Key","AL8q0S1C2Q5o2m0E0o","Gstin","07AGAPA5363L002")
//				.POST(HttpRequest.BodyPublishers.ofString(payload))
//				.build();
//		HttpResponse<String> response = HttpClient
//				.newBuilder()
//				.proxy(ProxySelector.getDefault())
//				.build()
//				.send(request, BodyHandlers.ofString());
//		int statusCode = response.statusCode();
//		
//		if(statusCode != 200) {
//			throw new RuntimeException("Failed: Http Error Code:"+ statusCode);
//		}
//		String body = response.body();
//		
//		System.out.println(response.body());
//		
//	
//		String input = payload;
		return newGstUserLoginDto;
	}

	

	private static String encryptAsymetricKey(String text) throws Exception {
		PublicKey publicKeys = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, publicKeys);
		byte[] encryptedText = cipher.doFinal(text.getBytes());
		String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
		
		return encryptedPassword;
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
	
public static String decryptBySymmetricKeySEK(String encryptedSek) {
		
		byte[] decodedSecret = Base64.getDecoder().decode(strSecretKey);
		Key aesKey = new SecretKeySpec(decodedSecret, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek);
			byte[] decryptedSekBytes = cipher.doFinal(encryptedSekBytes);
			byte[] sekBytes = decryptedSekBytes;
			String decryptedSek = Base64.getEncoder().encodeToString(decryptedSekBytes);
			return decryptedSek;
		}catch(Exception e) {
			return "Exception: " + e;
		}
	}
	
	
	
}
