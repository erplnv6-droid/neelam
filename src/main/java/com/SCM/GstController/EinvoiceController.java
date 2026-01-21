//package com.SCM.GstController;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.http.HttpRequest;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.security.PublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.util.Base64;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.KeyGenerator;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.http.client.methods.HttpPost;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.SCM.GstModel.GstUserLogin;
//import com.SCM.GstService.EinvoiceAuth;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//
//
//@RestController
//@RequestMapping("/data")
//public class EinvoiceController {
//
////	String url="https://developers.eraahi.com/eInvoiceGateway/eivital/v1.04/auth";
//	
//	@Autowired
//	
//	private  EinvoiceAuth einvoiceAuth;
//	
//
//	
//	
//	@GetMapping("/getAppKey")
//public  String createAESKey()
//{
////		String appKey = einvoiceAuth.createAESKey();
//		String appKey = Base64.getEncoder().encodeToString(einvoiceAuth.generateAESKey());
//		
//		return appKey; 
//}
//	
//@GetMapping("/get/")
//
//public String getPublickey() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, IOException
//{
//PublicKey publicKey= einvoiceAuth.getPublicKey();
//	
//	return publicKey.toString();
//}
//
//@GetMapping("/getKey/{appKey}")
//
//public String encryptAsymmentricKey(@PathVariable("appKey") String appKey) throws Exception
//{
//	String UserName = "AL001"; // username given by Alankint
//	String Password = "Alankit@123"; // Password given by Alankit
//	
//	System.out.println(appKey+"appKey");
//
//	
//	String payload = "{\"UserName\":\""+UserName+"\",\"Password\":\"" + Password + "\",\"appKey\":\""+appKey+"\",\"ForceRefreshAccessToken\": true}";
//	 payload = Base64.getEncoder().encodeToString(payload.getBytes());
//	payload = "{\"Data\":\"" + einvoiceAuth.encryptAsymmentricKey(payload) + "\"}";
//
//	return payload;
//
//}
//
////@PostMapping("/get/Sek")
////
////public String getSek(@RequestParam("Sek") String Sek) throws JsonMappingException, JsonProcessingException
////{
////	System.out.println(Sek);
////	String Sek1=einvoiceAuth.decrptBySymmetricKeySEK(Sek);
////	String sek2=einvoiceAuth.getGSTINDetails(Sek, Sek1);
////	
////	return sek2;
////
////}
//
//@PostMapping("/encrptedData")
//
//public ResponseEntity<String> encryptedData(@RequestParam("json") String json,@RequestParam("decryptedSek") String decryptedSek)
//{
//	
//	
//	String encryptedSymmentricKey=einvoiceAuth.encryptBySymmetricKey(json, decryptedSek);
//	
//	return ResponseEntity.ok(encryptedSymmentricKey);
//}
//
//
//
//@PostMapping("/decryptedData")
//
//public String convertDecryptedData(@Param("data") String data,@Param("decryptedSek") String decryptedSek) throws Exception 
//{
//data=einvoiceAuth.encryptAsymmentricKey(data);
//
//	
//	byte[] decryptedSymmentricKey=einvoiceAuth.decryptBySymmentricKey(data, decryptedSek).getBytes(StandardCharsets.UTF_8);
//
//
//
//	return new String(decryptedSymmentricKey);
//}
//	
////@PostMapping("/getJwt")
////
////public String getDecodeJwt(@RequestParam ("signedText") String signedText) throws IOException
////{
////	 Key key = generateKey()
////	byte[] decrptedData=Base64.getDecoder().decode(signedText);
////	
////	decrptedData =einvoiceAuth.decodeSignedJWT(signedText).getBytes();
////	
////	return decrptedData.toString();
////
////	  
////}
//
//@GetMapping("/getDecode")
//public String decodeSignedJWT(@RequestParam ("signedText") String signedText) throws IOException
//{
//  Base64.Decoder decoder = Base64.getUrlDecoder();
//	  
//	
//    String[]  splitSignedText = signedText.split("\\.");
//    String decodedSigned =new String(decoder.decode(splitSignedText[0]));
//    decodedSigned = decodedSigned +"\n Content:"+(new String(decoder.decode(splitSignedText[1])));
//    decodedSigned.replaceAll("\\\"", "\"");
//    System.out.println("\nDecoded Text:" + decodedSigned);
//	return decodedSigned;
// }
//
//
//
//
////@GetMapping("/getGstData")
////
////public String getGstDetails(@RequestParam ("gstin") String gstin,@RequestParam("encryptedSek") String encryptedSek)
////{
////	String decryptedkey=einvoiceAuth.decrptBySymmetricKeySEK(encryptedSek);
//////	System.out.println("Decrypted key:"+decryptedkey);
////	String gstDataByDetails=einvoiceAuth.getGSTINDetails(gstin, decryptedkey);
////	
////	return gstDataByDetails;
////	
////}
//
//
//@GetMapping("/getJsonJwt")
//
//public String getJwt(@RequestParam ("signedText") String signedText) throws IOException
//{
//	String jwtdata=einvoiceAuth.decodeSignedJWT(signedText);
//	
//	return jwtdata;
//}
//
//@PostMapping("/get/encryptedsek")
//
//public String decryptedKey(@RequestParam ("encryptedSek") String encryptedSek)
//{
//
//	String data=EinvoiceAuth.decrptBySymmetricKeySEK(encryptedSek);
//	
//	return data;
//}
//
//@PostMapping("/post/encryptedsek")
//
//public String encryptedData(@RequestParam ("clearText") String clearText) throws Exception
//{
//	String data1=einvoiceAuth.encryptAsymmentricKey(clearText);
//	
//	return data1;
//}
//
//@PostMapping("/post/decryptedskey")
//
//public String encryptBySymmetricKey(@RequestParam ("data") String data,@RequestParam ("decryptedSek") String decryptedSek)
//{
//	String esdata=einvoiceAuth.decryptBySymmentricKey(data, decryptedSek);
//	
//	return esdata;
//}
//
//
//
//@PostMapping("/post/encryptedSkey")
//
//public String encryptData(@RequestParam ("json") String json,@RequestParam ("decryptedSek") String decryptedSek)
//{
//	String encryptedData=einvoiceAuth.encryptBySymmetricKey(json, decryptedSek);
//	
//	return encryptedData;
//}
//
//@PostMapping("/post/payload")
//
//public String payloadData()
//{
//	String UserName = "AL001"; // username given by Alankint
//	String Password = "Alankit@123"; // Password given by Alankit
//	String appKey="FO4qvo19bwCRHdj3GXMz5w==";
//	System.out.println(appKey+"appKey");
//	String payload = "{\"username\":\"" + UserName + "\",\"password\":\"" + Password + "\",\"appkey\":\""
//			+ appKey + "\",\"ForceRefreshAccessToken\": true}";
//	System.out.println("Payload: Plain: " + payload);
//	payload = Base64.getEncoder().encodeToString(payload.getBytes());
//	
//	return payload;
//}
//
//@PostMapping("/post/encodetext")
//
//public String encodeEncrypt(@RequestParam ("clearText") String clearText) throws Exception
//{
//	String encodedata=einvoiceAuth.encryptAsymmentricKey(clearText);
//	
//	return encodedata;
//}
//
//@GetMapping("/getappkey")
//
//public String getappkey()
//{
//	String getapp=Base64.getEncoder().encodeToString(einvoiceAuth.generateAESKey());
//	
//	return getapp;
//}
//
////@PostMapping("/userdata")
////public String userData() throws JsonMappingException, JsonProcessingException
////{
////
////	String userLogin = einvoiceAuth.userLogin();
////	
////	
////	
////	
////	return userLogin;
////}
//
//
//
//
//@GetMapping("/get/invoicedata/{gstin}")
//
//public String getInvoiceData(@PathVariable ("gstin") String gstin)
//{
//	String getData=einvoiceAuth.getGSTINDetails(gstin);
//	
//	return getData;
//}
//
//
////@PostMapping("/userdata")
////public ResponseEntity<GstData> getGstData(@RequestParam ("gstData") String gstData) throws JsonMappingException, JsonProcessingException
////{
////
////	GstData userLogin = ;
////	
////	
////	
////	
////	return ResponseEntity.ok(userLogin);
////}
//
//}
