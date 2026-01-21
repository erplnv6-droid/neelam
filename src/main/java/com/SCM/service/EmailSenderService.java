package com.SCM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailsernder;
	
	
	public void sendEmail(String toEmail,String subject,String body) {
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("erp.lnv2@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailsernder.send(message);
		System.out.println("message sent succesfully");
		
		
	}
	
	
}
