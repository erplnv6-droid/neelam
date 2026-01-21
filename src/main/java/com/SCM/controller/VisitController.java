package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SCM.dto.VisitDto;
import com.SCM.service.VisitService;

@RestController
@RequestMapping("/api/visit")
public class VisitController {

	@Autowired
	private VisitService visitService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> visitin(@RequestBody VisitDto visitDto)
	{
		return new ResponseEntity<>(visitService.visitIn(visitDto),HttpStatus.CREATED);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> visitin(@RequestBody VisitDto visitDto,@PathVariable("id")int id)
	{
		return new ResponseEntity<>(visitService.visitOut(visitDto,id),HttpStatus.CREATED);	
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> fetchvisit()
	{
		return new ResponseEntity<>(visitService.fetchVisit(),HttpStatus.FOUND);	
	}
}
