package com.SCM.serviceimpl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.model.Distributor;
import com.SCM.model.MailAttachment;
import com.SCM.repository.UserRepository;
import com.SCM.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;

@Service
public class MailServiceImpl implements MailService{
	
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("{spring.mail.username}")
	private String fromMail;

	@Override
	public String sendmail(MultipartFile[] file, String [] to, String[] cc, String mailAttachment)
			throws MessagingException, JsonMappingException, JsonProcessingException, IOException {
		
		
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage , true);
		
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        
      //  User email = userRepository.findByEmail(userDetails.getEmail()).get();
        
//        String email = userDetails.getEmail();
		
		messageHelper.setTo(to);
		
		messageHelper.setCc(cc);
		
		messageHelper.setFrom("fromMail");
		
		MailAttachment value = objectMapper.readValue(mailAttachment, MailAttachment.class);
		
		
		
		   messageHelper.setSubject(value.getSubject());
		
		   messageHelper.setText(value.getBody());
		
		
		for(int i=0;i<file.length;i++)
		{
			messageHelper.addAttachment(
					
					file[i].getOriginalFilename(),
					new ByteArrayResource(file[i].getBytes())
					
					);
		}
		
		javaMailSender.send(mimeMessage);
		
		return "";
		
		
	}

}
