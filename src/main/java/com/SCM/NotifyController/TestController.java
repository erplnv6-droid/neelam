//package com.SCM.NotifyController;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.SCM.NotificationRequest.Tokens;
//import com.SCM.NotifyRepository.TokenProjection;
//import com.SCM.NotifyRepository.TokenRepository;
//
//@RestController
//@RequestMapping("/test")
//public class TestController {
//
//	@Autowired
//	private TokenRepository repository;
//	
//	@GetMapping("/get/{ret_id}")
//	public ResponseEntity<?> getTokens(@PathVariable Integer ret_id)
//	{
//		 TokenProjection notificationRetailer = repository.getNotificationRetailer(ret_id);
//		 
//		 return new ResponseEntity<>(notificationRetailer,HttpStatus.OK);
//	}
//}
