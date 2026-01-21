package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.repository.RoleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
    RoleRepository roleRepository;
	
	
	@GetMapping("/")
	public ResponseEntity<?> showRole()
	{
	    return new ResponseEntity<>(roleRepository.findAll(),HttpStatus.OK) ;
	}
}
