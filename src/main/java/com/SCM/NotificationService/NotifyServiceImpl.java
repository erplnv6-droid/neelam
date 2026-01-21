package com.SCM.NotificationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.NotificationRequest.NotificationDataCreate;
import com.SCM.NotificationRequest.NotificationRequest;
import com.SCM.NotificationRequest.Tokens;
import com.SCM.NotifyRepository.NotifiySaveRepository;
import com.SCM.NotifyRepository.NotifyRepository;
import com.SCM.NotifyRepository.TokenRepository;
import com.SCM.config.UserId;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.ERole;
import com.SCM.model.Retailer;
import com.SCM.model.Role;
import com.SCM.model.Staff;
import com.SCM.model.WorkOrder;
import com.SCM.repository.RoleRepository;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;



@Service
public class NotifyServiceImpl implements NotifyService {
	
@Autowired
	
	private FirebaseMessaging firebaseMessaging;
	
	
	@Autowired
	private NotifyRepository notifyRepository;
	
	@Autowired
	private NotifiySaveRepository notifiySaveRepository;
	
	
	@Autowired
	private RoleRepository repository;
	
	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Autowired
	private TokenRepository tokenRepository;

	


	
	

	
	public NotificationDataCreate saveNotificationData(NotificationDataCreate notificationDataCreate)
	{

		
		

		
		return notifiySaveRepository.save(notificationDataCreate);
	}
	
	
	public List<NotificationDataCreate> getAllNotificationSaveData()
	{
		return notifiySaveRepository.findAll();
	}
	
	@Transactional(readOnly = true)
public List<Tokens> findbyNotificationData(long id)
{
	Optional<NotificationDataCreate> optional=notifiySaveRepository.findById(id);
	
	if(optional.isPresent())
	{
		NotificationDataCreate create=optional.get();
		
		return create.getTokens();
	}
	return Collections.emptyList();
}
	
	


	
	public String sendNotificationByToken(List<String> token,String body,String title,int id) throws FirebaseMessagingException
	{
//		Notification notification=Notification.builder()
//				.setTitle(title)
//				.setBody(body)
//				
//				.build();
		
		MulticastMessage message=MulticastMessage
				.builder()
				.addAllTokens(token)
				.setNotification(new Notification(title, body))
				 .putData("workorderid", String.valueOf(id))
			     .build();
		
		
		
		  try {
		 
			  FirebaseMessaging.getInstance().sendMulticast(message); 
			  
			  return "Sending Successfully Notification";
	
		}
		catch(FirebaseMessagingException e)
		{
			e.printStackTrace();
			
		return "notification failed";
			
		}
	

		
	}

	
	
	public String sendNotificationByDistributor(List<String> token,String body,String title,int id) throws FirebaseMessagingException
	{
//		Notification notification=Notification.builder()
//				.setTitle(title)
//				.setBody(body)
//				
//				.build();
		
		MulticastMessage message=MulticastMessage
				.builder()
				.addAllTokens(token)
				.setNotification(new Notification(title, body))
				 .putData("primaryorderid", String.valueOf(id))
			     .build();
		
		
		
		  try {
		 
			  FirebaseMessaging.getInstance().sendMulticast(message); 
			  
			  return "Sending Successfully Notification";
	
		}
		catch(FirebaseMessagingException e)
		{
			e.printStackTrace();
			
		return "notification failed";
			
		}
	

		
	}
	
	
	
	
	
	

	public Tokens saveTokens(Tokens tokens)
	{

		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		
	tokens.setRolename(role);
	tokens.setStaffid(uid);
	tokens.setStaffname(uname);
	
	
		tokenRepository.deletNotification(uid);
		
		return tokenRepository.save(tokens);
		
		
		
		
	}
	

		





}
