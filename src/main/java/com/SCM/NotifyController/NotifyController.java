package com.SCM.NotifyController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.NotificationRequest.NotificationDataCreate;
import com.SCM.NotificationRequest.NotificationRequest;
import com.SCM.NotificationRequest.Tokens;
import com.SCM.NotificationService.NotifyServiceImpl;

import com.google.firebase.messaging.FirebaseMessagingException;



@RestController
@RequestMapping("/api/notify")
@CrossOrigin(origins = "*")
public class NotifyController {
	
	@Autowired
	public NotifyServiceImpl notifyServiceImpl;
	
	@Autowired
	public NotifyController(NotifyServiceImpl notificationService)
	{
		this.notifyServiceImpl=notificationService;
	}
	
	
//	@PostMapping("/post")
//	
//	public String sendNotificationByToken(@RequestBody List<String> tokens,@RequestBody String title,@RequestBody String body) throws FirebaseMessagingException
//	{
//		return notifyServiceImpl.sendNotificationByToken(tokens,body,title);
//	}

	
	@PostMapping("/saveData/post")
	
	public NotificationDataCreate saveNotificationData(@RequestBody NotificationDataCreate notificationDataCreate)
	{
		return notifyServiceImpl.saveNotificationData(notificationDataCreate);
	}
	
	@GetMapping("/get/saveData")
	
	public List<NotificationDataCreate> getNotificationData()
	{
		return notifyServiceImpl.getAllNotificationSaveData();
	}
	
	@GetMapping("/get/saveData/{id}")
	
	public ResponseEntity<List<Tokens>> findByNotificationData(@PathVariable long id)
	{
		List<Tokens> token=notifyServiceImpl.findbyNotificationData(id);
		
		return ResponseEntity.ok(token);
	}
	
	
	@PostMapping("/post/tokens")
	
	public Tokens getToken(@RequestBody Tokens tokens)
	{
		return notifyServiceImpl.saveTokens(tokens);
	}
}
