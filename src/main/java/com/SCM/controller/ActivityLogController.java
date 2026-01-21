package com.SCM.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.model.ActivityLog;
import com.SCM.repository.ActivityLogRepo;

@RestController
@RequestMapping("/api/log")
public class ActivityLogController {
	
	@Autowired
	private ActivityLogRepo activityLogRepo;
	
	
	@GetMapping("/")
	public ResponseEntity<?> activitylog()
	{
		List<ActivityLog>  activityLog =   activityLogRepo.findAll();	
		return new ResponseEntity<>(activityLog,HttpStatus.OK);
	}
	
	@GetMapping("/{activitylogid}")
	public ResponseEntity<?> activitylogbyuserId(@PathVariable("activitylogid")int activitylogid)
	{
		List<ActivityLog>  activityLog =   activityLogRepo.fetchActivitylogdetails(activitylogid);
	
		return new ResponseEntity<>(activityLog,HttpStatus.OK);
	}
}
