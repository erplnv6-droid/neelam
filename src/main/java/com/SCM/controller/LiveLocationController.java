//package com.SCM.controller;
//
//import java.security.Principal;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//import com.SCM.dto.StaffLocationMessage;
//import com.SCM.model.Staff;
//import com.SCM.repository.StaffRepo;
//
//@Controller
//public class LiveLocationController {
//	
//	
//	
//	 private final SimpMessagingTemplate messagingTemplate;
//	    private final StaffRepo staffRepo;
//
//	    public LiveLocationController(SimpMessagingTemplate messagingTemplate, StaffRepo staffRepo) {
//	        this.messagingTemplate = messagingTemplate;
//	        this.staffRepo = staffRepo;
//	    }
//
//	    @MessageMapping("/location.update")
//	    public void updateLocation(StaffLocationMessage msg, Principal principal) {
//	        System.out.println("Inside updateLocation, principal = " + (principal != null ? principal.getName() : "null"));
//
//	        // optional: map principal -> staff entity
//	        // String username = principal.getName();
//	        // Staff staff = staffRepo.findByEmailOrStaffCode(username).orElse(...);
//
//	        // broadcast to admins
//	        messagingTemplate.convertAndSend("/topic/test", "Admin Test: socket call received");
//
//	        // send private message back to the same user
//	        if (principal != null) {
//	            messagingTemplate.convertAndSendToUser(
//	                principal.getName(), // this is the principal's name
//	                "/queue/test",
//	                "User Test: your socket call received"
//	            );
//	        }
//	    }
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////
////	
////	private final SimpMessagingTemplate messagingTemplate;
////	private final StaffRepo staffRepo;
////	
////	public LiveLocationController(SimpMessagingTemplate messagingTemplate,StaffRepo staffRepo) {
////		this.staffRepo=staffRepo;
////		this.messagingTemplate=messagingTemplate;
////	}
////	
////	
////	@MessageMapping("/location.update")
////    public void updateLocation(StaffLocationMessage msg) {
////        System.out.println("Inside test updateLocation controller");
////
////        // ===== Test broadcast for admin =====
////        messagingTemplate.convertAndSend("/topic/test", "Admin Test: socket call received");
////
////        // ===== Test per-user (self) message =====
////        if(msg != null && msg.getStaffid() != 0) {
////            messagingTemplate.convertAndSendToUser(
////                    msg.getStaffid().toString(),
////                    "/queue/test",
////                    "User Test: your socket call received"
////            );
////        }
////    }
////	
////	
//	
//}
