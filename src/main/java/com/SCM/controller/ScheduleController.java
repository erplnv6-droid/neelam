package com.SCM.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.SchedulerDto;
import com.SCM.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/")
	public ResponseEntity<?>  schedule(@RequestBody SchedulerDto schedulerDto)
	{
		return new ResponseEntity<>(scheduleService.schedule(schedulerDto),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> fetchSchedule()
	{
		return new ResponseEntity<>(scheduleService.fetchSchedule(),HttpStatus.FOUND);
	}
}
