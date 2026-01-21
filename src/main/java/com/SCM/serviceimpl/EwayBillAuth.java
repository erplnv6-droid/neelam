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
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.yaml.snakeyaml.reader.StreamReader;

import com.SCM.GstLoginUserDto.CancelEwayBillPartB;
import com.SCM.GstLoginUserDto.CancelEwayBillPartBResponse;
import com.SCM.GstLoginUserDto.EwayBillAuthDto;
import com.SCM.GstLoginUserDto.EwayBillAuthResponseDto;
import com.SCM.GstLoginUserDto.EwayBillByIrnDto;
import com.SCM.GstLoginUserDto.EwayBillExtendValidityDto;
import com.SCM.GstLoginUserDto.EwayBillGenerateResDto;
import com.SCM.GstLoginUserDto.EwayBillPartBGenerateDto;
import com.SCM.GstLoginUserDto.EwayBillUpdatePartBDto;
import com.SCM.GstLoginUserDto.GstLoginDto;
import com.SCM.GstModel.EwayBillData;
import com.SCM.GstRepository.EwayBillAuthRepository;
import com.SCM.repository.SalesRepo;
import com.SCM.service.EwayBillService;
import com.SCM.serviceimpl.GstUserLoginServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service

public class EwayBillAuth implements EwayBillService {
	
	@Autowired
	private GstUserLoginServiceImpl gstUserLoginServiceImpl;
	

	
	
	
	   private static String publickeylocationString;
	   
	   private static Path publickeylocation;

	    @Value("${FILE_PATH}")
	    private static String FILE_PATH;
	    
	    
	    
	    @Autowired
		public EwayBillAuth(Environment env) throws IOException {
		
	    	try {
	    		
	    	    String relativePath = env.getProperty("app.file.upload-dir1","/certificate_publickey.pem");
	    	        Path relativeDirectory = Paths.get(relativePath).toAbsolutePath().normalize();
	    	        
	    	        // Construct the full file path for the public key
	    	        this.publickeylocation = relativeDirectory.resolve("certificate_publickey.pem");
	    	        

	    	       
	    	        // Ensure the file exists and is readable
//	    	        if (!Files.exists(this.publickeylocation) || !Files.isReadable(this.publickeylocation)) {
//	    	            throw new IOException("File not accessible: " + this.publickeylocation);
//	    	        }
	    	        
	    	        this.publickeylocationString = this.publickeylocation.toString();
	    	        System.out.println("publickeylocationString: " + publickeylocationString);
	    	        
	    	        

	    	        
	    	        
	    	  }
	    	    	
	    	    	catch (Exception e) {
	    			e.printStackTrace();
	    			}
	    	   		
	    			
	    		}
		
	
	
	
	
	static String folderPath = "";
//	static byte[] appKey =  generateAESKey();
 static String app_key ="/gezwTOgAwvW2fmu3nXDSoTHW/DmtbllsVM5skshn9k=";
	
	static String strSecretKey = app_key;

	static String username = "API_LNVERP";
	static String password = "Lnverp@123";
	static String Gstin = "27AABCN9540N1ZY";
	static String subkey = "AL5i1p6T7z1D9K7z3d";
	static String encPayload = "";
	static String authtoken = "";
	static String sek = "";

	static ObjectMapper objectMapper;
	
	
	
	
	public static PublicKey getEwayPublicKey()throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		System.out.println(publickeylocationString +"+++++++++ publickeylocationString ");
		try
		{
			FileInputStream fileInputStream=new FileInputStream(publickeylocationString);
			byte[] keyBytes=new byte[fileInputStream.available()];
			fileInputStream.read(keyBytes);
			fileInputStream.close();
			String pubKey=new String(keyBytes,"UTF-8");
			pubKey=pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
			keyBytes=Base64.getMimeDecoder().decode(pubKey);
			X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
		    PublicKey publicKey=keyFactory.generatePublic(keySpec);
		    return publicKey;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	public static String getPath() {
		String folderPath = "";
		try {
			File tempFile = new File(
					EwayBillAuth.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			folderPath = tempFile.getParentFile().getPath() + "\\";
			return folderPath;
		} catch (URISyntaxException ex) {
			Logger.getLogger(EwayBillAuth.class.getName()).log(Level.SEVERE, null, ex);
		}
		return folderPath;
	}
	
	
	
	public static String encryptAsymmentricKeyEway(String clearText) throws Exception
	{
		PublicKey publicKey=getEwayPublicKey();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptedText = cipher.doFinal(clearText.getBytes());
		String encryptedPassword = Base64.getEncoder().encodeToString(encryptedText);
		return encryptedPassword;
	}
	
	public static String decrptBySymmetricKeySEK(String encryptedSek) {
//		byte[] appKey ="[B@2a451ca".getBytes();
		

//			String oldAppKey = "FO4qvo19bwCRHdj3GXMz5w==";
//			generateAESKey();
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
	
	
	
	
	
	public EwayBillAuthResponseDto ewayAuthentication() throws Exception

	{
		authtoken = "";
		folderPath = getPath();
		objectMapper = new ObjectMapper();


	
			String app_key = strSecretKey;
			System.out.println(app_key);
			String action="ACCESSTOKEN";

			String payload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"app_key\":\""
					+ app_key + "\",\"action\":\"" + action + "\"}";
			
			
//			ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
//			String json = objectMapper.writeValueAsString(ewayBillAuthDto);
			

            payload=Base64.getEncoder().encodeToString(payload.getBytes());
		payload = "{\"Data\":\"" + encryptAsymmentricKeyEway(payload) + "\"}";
			System.out.println("Payload: Encrypted:" + payload);
		
			CloseableHttpClient httpClient = HttpClients.createDefault();
		   HttpPost postRequest = new HttpPost("https://newewaybill.alankitgst.com/ewaybillgateway/v1.03/auth");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setHeader("Gstin", "27AABCN9540N1ZY");
			postRequest.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");

//
//	            postRequest.setHeader("appKey","[B@f601bd");
//			postRequest.addHeader("KeepAlive", "true");
//			postRequest.addHeader("AllowAutoRedirect", "false");
			StringEntity input = new StringEntity(payload,ContentType.APPLICATION_JSON);
			
			System.out.println("input:"+input);
//			input.setContentType("application/json");
			postRequest.setEntity(input);
			
		try {
			
			CloseableHttpResponse response = httpClient.execute(postRequest);
//			if (response.getCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + response.getCode());
//			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String responseText="";
		String output;
			while ((output = br.readLine()) != null) {
				responseText = output;
			}
			System.out.println("Response:" + responseText);
			String status = objectMapper.readTree(responseText).get("status").asText();
			System.out.println("status"+status);
         EwayBillAuthResponseDto responseDto=new EwayBillAuthResponseDto();
			if (status.equals("1")) {
				
	
				 authtoken = objectMapper.readTree(responseText).get("authtoken").asText();
			
				 sek = objectMapper.readTree(responseText).get("sek").asText();
				
				 String decryptedSek=decrptBySymmetricKeySEK(sek);
				 
		
				 
				 System.out.println("decrypted sek:"+decryptedSek);
			
//				JsonObject jsonObject1 = JsonParser.parseString(responseText).getAsJsonObject();
				
			
				responseDto.setAuthToken(authtoken);
				responseDto.setEncryptedSek(sek);
				responseDto.setDecryptedSek(decryptedSek);
				responseDto.setGstin(Gstin);
				
				
				
				
			}
			return responseDto;

		} catch (Exception ex) {
			Logger.getLogger(EwayBillAuth.class.getName()).log(Level.SEVERE, null, ex);
			
		}
		return null;
	}
	
	
	
public EwayBillGenerateResDto generateEwayPartB(@RequestBody EwayBillPartBGenerateDto partBGenerateDto,@PathVariable int joId) throws JsonProcessingException, JSONException,Exception
{
	folderPath = getPath();
	objectMapper = new ObjectMapper();
	ObjectWriter objectMapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
	
//	ObjectMapper objectMapper=new ObjectMapper();
	String json =objectMapper.writeValueAsString(partBGenerateDto);
	
	System.out.println("json data:"+json);

	

	
	HttpPost httpPost=new HttpPost("https://newewaybill.alankitgst.com/ewaybillgateway/v1.03/ewayapi");
	
	httpPost.setHeader("Content-Type", "application/json");
	httpPost.setHeader("gstin", "27AABCN9540N1ZY");
	httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
	httpPost.setHeader("authtoken",this.authtoken);
	
	System.out.println("authtoken:"+authtoken);
	
	EwayBillAuthResponseDto ewayBillAuthResponseDto=new EwayBillAuthResponseDto();
	
//	String gstin="07AGAPA5363L002";
	
	
	
	ewayBillAuthResponseDto=gstUserLoginServiceImpl.getEwayBillByGstin(authtoken);
	

	
String decryptedSek=ewayBillAuthResponseDto.getDecryptedSek();
	
	System.out.println("decrypted sek:"+decryptedSek);
	
	
	
//	String payload=Base64.getEncoder().encodeToString(json.getBytes());
	


	String action="GENEWAYBILL";
	String payload = "{\"action\":\"" + action + "\" ,\"Data\":\"" + encryptBySymmetricKey(json, decryptedSek) + "\"}";	
	 
	 
	
	System.out.println("payload data:"+payload);
	
	StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
	
	
	System.out.println("string entity"+stringEntity);
	
    httpPost.setEntity(stringEntity);
	

	CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
	try 
	{
		CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpPost);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
		
		String responseText ="";
		
		String output;
		

		
		while ((output=br.readLine()) != null)
		{
			responseText = output;
		}
		System.out.println("response text:"+responseText);
		

		
		JSONObject jsonObj = new JSONObject(responseText);
		
		System.out.println("json object:"+jsonObj);
		
		int status = jsonObj.getInt("status");
//		
		String data = jsonObj.getString("data");
//		
		System.out.println("data:"+data);
		

		
//		String status = objectMapper.readTree(responseText).get("status").asText();
//		
//		String data=objectMapper.readTree(responseText).get("data").asText();
//		
//		System.out.println("status:"+status);
		

		
EwayBillGenerateResDto billGenerateResDto=new EwayBillGenerateResDto();
	     if(status==1)
		{
			String decryptedData=decryptBySymmetricKey(data, decryptedSek);
			
			System.out.println("decrypted data:"+decryptedData);
//			
			ObjectMapper objMp = new ObjectMapper();
	
		long ewbNo=objMp.readTree(decryptedData).get("ewayBillNo").asLong();
		
		String ewbDate=objMp.readTree(decryptedData).get("ewayBillDate").asText();
		
		String ewbTill=objMp.readTree(decryptedData).get("validUpto").asText();
		
		String alert=objMp.readTree(decryptedData).get("alert").asText();
		
		
			JsonObject jsonObject2=JsonParser.parseString(responseText).getAsJsonObject();
			
			billGenerateResDto.setEwayBillNo(ewbNo);
			billGenerateResDto.setEwayBillDate(ewbDate);
			billGenerateResDto.setValidUpto(ewbTill);
			billGenerateResDto.setAlert(alert);
			billGenerateResDto.setStatus("Generated");
			
//			JsonObject jsonObject2=JsonParser.parseString(decryptedData).getAsJsonObject();
//			
//			System.out.println("json data:"+jsonObject2);
//			
//			return jsonObject2.toString();
			

		}
	     
	     return billGenerateResDto;
	     


	} catch (Exception ex) {
		Logger.getLogger(EwayBillAuth.class.getName()).log(Level.SEVERE, null, ex);
		
	}
	return null;
}
	
public CancelEwayBillPartBResponse cancelEwayBill(CancelEwayBillPartB cancelEwayBillPartB,@PathVariable int joId) throws JsonProcessingException
{
	ObjectMapper objectMapper=new ObjectMapper();
	
	String json=objectMapper.writeValueAsString(cancelEwayBillPartB);
	
	System.out.println("json data:"+json);
	
	EwayBillAuthResponseDto authResponseDto=new EwayBillAuthResponseDto();
	
	authResponseDto=gstUserLoginServiceImpl.getEwayBillByGstin(authtoken);
	
	String decryptedSek=authResponseDto.getDecryptedSek();
	
	System.out.println("decrypted sek: "+decryptedSek);
	
	HttpPost httpPost=new HttpPost("https://newewaybill.alankitgst.com/ewaybillgateway/v1.03/ewayapi");
	
	httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
	
	httpPost.setHeader("Gstin","27AABCN9540N1ZY");
	
	httpPost.setHeader("Authtoken",this.authtoken);
	
	String action="CANEWB";
	
	String payload="{\"Action\":\"" + action + "\" ,\"Data\":\"" + encryptBySymmetricKey(json, decryptedSek) + "\"}";	
	
	System.out.println("payload data:"+payload);
	
	
	StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
	
	httpPost.setEntity(stringEntity);
	
	CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
	
	try
	{
		CloseableHttpResponse closeableHttpResponse=closeableHttpClient.execute(httpPost);
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
		
		String output;
		
		String responseTexr="";
		
		while ((output=reader.readLine())!=null) {
			responseTexr=output;
			
		}
		
		System.out.println("response data:"+responseTexr);
		
		JSONObject jsonObject=new JSONObject(responseTexr);
		
		
		
		int status=jsonObject.getInt("status");
		
		String data=jsonObject.getString("data");
		
		
		CancelEwayBillPartBResponse billPartBResponse=new CancelEwayBillPartBResponse();
		if(status==1)
		{
			String decryptedData=decryptBySymmetricKey(data, decryptedSek);
			
			JsonObject jsonObject2=JsonParser.parseString(decryptedData).getAsJsonObject();
			
			ObjectMapper objectMapper2=new ObjectMapper();
			
			long ewaybillNo=objectMapper2.readTree(decryptedData).get("ewayBillNo").asLong();
			
			String cancelDate=objectMapper2.readTree(decryptedData).get("cancelDate").asText();
			
		
			
			billPartBResponse.setEwayBillNo(ewaybillNo);
			billPartBResponse.setCancelDate(cancelDate);
			billPartBResponse.setStatus("Cancel");
			
			
			
		
		}
		
		return billPartBResponse;
	
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
//	EwayBillByIrnDto billByIrnDto=new EwayBillByIrnDto();
//	
//	salesRepo.deleteByEwbValidTill(billByIrnDto.getEwbNo(),billByIrnDto.getEwbDt(),billByIrnDto.getEwbValidTill());
	
	return null;
	
	
}


public String getTransportNo(String trn_no)throws JSONException
{
	
	EwayBillAuthResponseDto authResponseDto=new EwayBillAuthResponseDto();
	
	authResponseDto=gstUserLoginServiceImpl.getEwayBillByGstin(authtoken);
	

	
	String decryptedSek=authResponseDto.getDecryptedSek();
	
	String authString=authResponseDto.getAuthToken();
	
	System.out.println("auth token"+authString);
	
	System.out.println("decrypted sek: "+decryptedSek);
	
	
	String url="https://newewaybill.alankitgst.com/ewaybillgateway/v1.03/Master/GetTransporterDetails";
	
HttpGet httpGet=new HttpGet(url);

	httpGet.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
	
	httpGet.setHeader("gstin","27AABCN9540N1ZY");
	
	httpGet.setHeader("authtoken",this.authtoken);
	
	
	CloseableHttpClient client=HttpClients.createDefault();
	
	try
	{
		CloseableHttpResponse httpResponse=client.execute(httpGet);
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		
		String output;
		
		String responseText ="";
		
		while((output=reader.readLine()) != null)
		{
			responseText=output;
		}
		
		System.out.println("response data: "+responseText);
		
		JSONObject jsonObject=new JSONObject(responseText);
		System.out.println("json data"+jsonObject);
		int status=jsonObject.getInt("status");
		

		
		if(status==1)
			
			
		{
			String data=jsonObject.getString("data");
			
			System.out.println("data"+data);
			
			String encryptedData=encryptBySymmetricKey(data, decryptedSek);
			
			System.out.println("encrypted data:"+encryptedData);
			
			String decryptedData=decryptBySymmetricKey(data, decryptedSek);
			
			System.out.println("decrypted data"+decryptedData);

		
			
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
		e.toString();
	}
	
	return null;
	
}


//public void GetEwayBill()
//{
//HttpPost request = 
//(HttpPost)WebRequest.Create("<URL>/EwayApi/GetEwayBill?ewbNo=191000001846");
//request.Method = "GET";
//request.KeepAlive = true;
//request.AllowAutoRedirect = false;
//request.Accept = "*/*";
//request.ContentType = "application/json";
//request.Headers.Add("client-id", "TESTCLIENTID");
//request.Headers.Add("client-secret", "CLIENTSECRET");
//request.Headers.Add("gstin", "29AAACGIIIII1Z3");
//request.Headers.Add("authtoken", "0aAjBKdo7rcNYJB30g5DS2u8z");
//WebResponse response = request.GetResponse();
//String result = new StreamReader(response.GetResponseStream()).ReadToEnd();
//JavaScriptSerializer serial1 = new JavaScriptSerializer();
//ewayapi.Entities.EwayBillApiResponseForGet ewbres = serial1.Deserializ(result);
//string rek = encdec.DecryptBySymmetricKey (ewbres.rek, Convert.FromBase64String(sek));
//string data = encdec.DecryptBySymmetricKey (ewbres.data, Convert.FromBase64String(rek));
//byte[] reqDatabytes = Convert.FromBase64String(data);
////byte[] reqDatabytes = Convert.FromBase64String(ewbres.data);
//String requestData = System.Text.Encoding.UTF8.GetString(reqDatabytes);
////string hmac = encdec.GenerateHMAC(requestData, Convert.FromBase64String(rek));
//String hmac = encdec.GenerateHMAC(data, Convert.FromBase64String(rek));
//if (ewbres.hmac == hmac){
//}
//}


public String updatePartB(EwayBillUpdatePartBDto billUpdatePartBDto) throws JsonProcessingException
{
	ObjectMapper objectMapper=new ObjectMapper();
	
	String json=objectMapper.writeValueAsString(billUpdatePartBDto);
	
	System.out.println("json: "+json);
	
	EwayBillAuthResponseDto authResponseDto=new EwayBillAuthResponseDto();
	
	authResponseDto=gstUserLoginServiceImpl.getEwayBillByGstin(authtoken);
	
	String decryptedSek=authResponseDto.getDecryptedSek();
	
	System.out.println("decrypted sek:"+decryptedSek);
	
	HttpPost httpPost=new HttpPost("https://newewaybill.alankitgst.com/ewaybillgateway/v1.03/ewayapi");
	
	httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");
	
	httpPost.setHeader("Gstin","27AABCN9540N1ZY");
	
	httpPost.setHeader("Authtoken",this.authtoken);
	
	String action="VEHEWB";
	
	String payload="{\"Action\":\"" + action + "\" ,\"Data\":\"" + encryptBySymmetricKey(json, decryptedSek) + "\"}";
	
	System.out.println("payload data:"+payload);
	
	
	StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);
	
	httpPost.setEntity(stringEntity);
	
	CloseableHttpClient closeableHttpClient=HttpClients.createDefault();
	try
	{
		CloseableHttpResponse response=closeableHttpClient.execute(httpPost);
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String output;
		
		String responseText="";
		
		while((output= reader.readLine())!=null)
		{
			responseText=output;
		}
		
		System.out.println("response Data:"+responseText);
		
		JSONObject jsonObject=new JSONObject(responseText);
		
		int status=jsonObject.getInt("status");
		
		String data=jsonObject.getString("data");
		
		if(status==1)
		{
			String decryptedData=decryptBySymmetricKey(data, decryptedSek);
			
			System.out.println("decrypted data:"+decryptedData);
			
			JsonObject jsonObject2=JsonParser.parseString(decryptedData).getAsJsonObject();
			
			return jsonObject2.toString();
		}
	
		
		else
		{
			JsonObject object=JsonParser.parseString(json.toString()).getAsJsonObject();
			
			return object.toString();
		}
	}
		
		catch(Exception e)
		{
			e.toString();
		}
	
	
	return null;
	
	
}


public String extendValidityEway(EwayBillExtendValidityDto billExtendValidityDto) throws JsonProcessingException
{
ObjectMapper objectMapper=new ObjectMapper();
	
String json=objectMapper.writeValueAsString(billExtendValidityDto);
	
System.out.println("json data: "+json);
	
EwayBillAuthResponseDto ewayBillAuthResponseDto=new EwayBillAuthResponseDto();
	
ewayBillAuthResponseDto=gstUserLoginServiceImpl.getEwayBillByGstin(authtoken);

String decryptedSek=ewayBillAuthResponseDto.getDecryptedSek();

System.out.println("decrypted sek: "+decryptedSek);

HttpPost httpPost=new HttpPost("https://newewaybill.alankitgst.com/ewaybillgateway/v1.03/ewayapi");

httpPost.setHeader("Ocp-Apim-Subscription-Key","AL5i1p6T7z1D9K7z3d");

httpPost.setHeader("Gstin","27AABCN9540N1ZY");

httpPost.setHeader("Authtoken",this.authtoken);

String action="EXTENDVALIDITY";

String payload="{\"Action\":\"" +action+ "\",\"Data\":\""+encryptBySymmetricKey(json, decryptedSek)+"\"}";

System.out.println("payload data: "+payload);

StringEntity stringEntity=new StringEntity(payload,ContentType.APPLICATION_JSON);

httpPost.setEntity(stringEntity);

CloseableHttpClient httpClient=HttpClients.createDefault();

try
{
	CloseableHttpResponse httpResponse=httpClient.execute(httpPost);
	
	BufferedReader reader=new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
	
	String output;
	
	String responseText="";
	while((output=reader.readLine())!=null)
	{
		responseText=output;
	}
	
	System.out.println("response data:"+responseText);
	
	JSONObject jsonObject=new JSONObject(responseText);
	
	int status=jsonObject.getInt("status");
	
	String data=jsonObject.getString("data");
	
	if(status==1)
	{
		String decryptedData=decryptBySymmetricKey(data, decryptedSek);
		
		System.out.println("decrypted data:"+decryptedData);
		
		JsonObject jsonObject2=JsonParser.parseString(decryptedData).getAsJsonObject();
		
		return jsonObject2.toString();
		
		
	}
	else
	{
		JsonObject jsonObject2=JsonParser.parseString(json.toString()).getAsJsonObject();
		
		return jsonObject2.toString();
	}
}
	catch(Exception e)
	{
		e.toString();
	}

	
	return null;
}
{
	
}

	
	
		    
}

