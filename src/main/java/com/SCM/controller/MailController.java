//package com.SCM.controller;
//
//import java.io.IOException;
//
//import javax.mail.MessagingException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.SCM.service.MailService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//
//@RestController
//@RequestMapping("/mail")
//public class MailController {
//	
//	@Autowired
//	private MailService mailService;
//	
//	
//	@PostMapping("/send")
//	public ResponseEntity<String> sendMail(@RequestParam ("file") MultipartFile [] file , 
//			                               @RequestParam ("to") String [] to ,
//			                               @RequestParam ("cc") String [] cc ,
//			                               @RequestParam ("mailAttachment") String mailAttachment) throws JsonMappingException, JsonProcessingException, MessagingException, IOException
//	{
//		mailService.sendmail(file , to , cc, mailAttachment);
//		
//		return new ResponseEntity<String>("Successfully send mail" , HttpStatus.OK);
//		
//	}
//
//}
