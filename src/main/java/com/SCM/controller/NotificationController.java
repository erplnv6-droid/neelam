//package com.SCM.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.SCM.notification.request.NotificationRequest;
//
//import com.SCM.notification.service.NotificationService;
//
//@RestController
//@RequestMapping("/api/notification")
//public class NotificationController {
//
//	
//	@Autowired
//	private NotificationService notificationService;
//	
//	
//	@Autowired
//	public NotificationController(NotificationService notificationService)
//	{
//		this.notificationService=notificationService;
//	}
//	
//	
//	@PostMapping("/post")
//	
//	public String sendNotificationByToken(@RequestBody NotificationRequest notificationRequest)
//	{
//		return notificationService.sendNotificationByToken(notificationRequest);
//	}
//	
//	
//}
