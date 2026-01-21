package com.SCM.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface MailService {
	
	
	public String sendmail(MultipartFile[] file , String [] to , String [] cc , String mailAttachment) throws  JsonMappingException, JsonProcessingException, IOException, MessagingException;
	

}
