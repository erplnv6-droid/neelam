package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.Groupn1Dto;
import com.SCM.service.Groupn1Service;

@RestController
@RequestMapping("/api/v1/groupn1")
@CrossOrigin(origins = "*")
public class Groupn1Controller {

	
	@Autowired
	private Groupn1Service service;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Groupn1Dto dto){
		return new ResponseEntity<>(service.save(dto),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getGroupn1(@PathVariable long id){
		return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateGroupn1(@PathVariable long id,@RequestBody Groupn1Dto dto){
		return new ResponseEntity<>(service.update(dto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGroupn1(@PathVariable long id){
		return new ResponseEntity<>(service.deleteGroupn1(id),HttpStatus.OK);
	}
	
}
