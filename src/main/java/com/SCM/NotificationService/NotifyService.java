package com.SCM.NotificationService;

import java.util.List;

import com.SCM.NotificationRequest.NotificationDataCreate;
import com.SCM.NotificationRequest.Tokens;
import com.google.firebase.messaging.FirebaseMessagingException;

public interface NotifyService {

	public NotificationDataCreate saveNotificationData(NotificationDataCreate notificationDataCreate);
	
	public List<NotificationDataCreate> getAllNotificationSaveData();
	
	public List<Tokens> findbyNotificationData(long id);
	
	public String sendNotificationByToken(List<String> token,String body,String title,int id) throws FirebaseMessagingException;
	
	public String sendNotificationByDistributor(List<String> token,String body,String title,int id) throws FirebaseMessagingException;
	
	public Tokens saveTokens(Tokens tokens);
}
