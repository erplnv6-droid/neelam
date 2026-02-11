package com.SCM.controller;

import java.util.Optional;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.model.StaffLocation;
import com.SCM.repository.StaffLocationRepo;

@RestController
@RequestMapping("/api/stafflocation")
public class StaffLocationController {
	
	private final StaffLocationRepo staffLocationRepo;
	
	public StaffLocationController(StaffLocationRepo staffLocationRepo) {
		this.staffLocationRepo=staffLocationRepo;
	}
	
	
	@GetMapping("/staff/{staffId}")
	public ResponseEntity<?> getStaffLocationByStaffId(@PathVariable Long staffId){
	Optional<StaffLocation> staff = staffLocationRepo.findByStaffId(staffId);	
		return new ResponseEntity<>(staff.get(),org.springframework.http.HttpStatus.OK);
	}
	
}
