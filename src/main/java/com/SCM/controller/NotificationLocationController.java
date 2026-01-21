package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.SCM.dto.StaffLiveLocationDto;
import com.SCM.model.StaffLocation;
import com.SCM.repository.StaffLocationRepo;
import com.SCM.repository.StaffRepo;

@Controller
public class NotificationLocationController {

	// server application 
	
	
	// /app/sendMessage  appp is prefix here 
//	@MessageMapping("/sendMessage")
//	@SendToUser("/topic/notification" )
//	public String sendMessage(String message) {
//	System.out.println("message :"+message);
//		return message;
//	}
	
	
	@Autowired
	private StaffRepo staffRepo;
	
	@Autowired
	private StaffLocationRepo staffLocationRepo;
	    
	
		@MessageMapping("/live-location")
	    @SendTo("/topic/locations")
	    public StaffLocation handleMessage(@Payload StaffLiveLocationDto liveLocationDto) {
			
			System.out.println("i got call");
	    	Long staffid = liveLocationDto.getStaffid();
	    	System.out.println("igot call   "+staffid);
	    	Double lattitude = liveLocationDto.getLattitude();
	    	Double longitude = liveLocationDto.getLongitude();
	    	
	    	System.out.println("lat "+lattitude+ "    long "+longitude);
	    	staffLocationRepo.updateStaffLocationWebSocket(lattitude, longitude, staffid);
	    	
	    	StaffLocation staffLocation = staffLocationRepo.findByStaffId(staffid).get();
	    	System.out.println("ye deeeekhhh"+staffLocation);
	    	StaffLocation updatedLocation=new StaffLocation();
	    	updatedLocation.setLongitude(staffLocation.getLongitude());
	    	updatedLocation.setLatitude(staffLocation.getLatitude());
	    	updatedLocation.setId(updatedLocation.getId());
	    	updatedLocation.setStaff(updatedLocation.getStaff());
	    	return updatedLocation;


	    }
	
	
}
