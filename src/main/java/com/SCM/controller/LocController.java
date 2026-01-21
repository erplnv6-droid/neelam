package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.Loc2DTO;
import com.SCM.service.LocService;

@RestController
@RequestMapping("/api/loc")
public class LocController {

	@Autowired
	private LocService locService;
	
	@PostMapping("/")
	public ResponseEntity<?> send(@RequestBody Loc2DTO loc2dto)
	{
		return new ResponseEntity<>(locService.save(loc2dto),HttpStatus.CREATED);
	}
	
	@GetMapping("/{staffid}")
	public ResponseEntity<?> get(@PathVariable("staffid")int staffid)
	{
		return new ResponseEntity<>(locService.getbystaffid(staffid),HttpStatus.OK);
	}
}
