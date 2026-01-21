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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.IndexDto.IndexGroupn3;
import com.SCM.dto.AppConstants;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn3Dto;
 import com.SCM.service.Groupn3Service;

@RestController
@RequestMapping("/api/v1/groupn3")
@CrossOrigin(origins = "*")

public class Groupn3Controller {

	
	@Autowired
	private Groupn3Service service;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Groupn3Dto dto){
		return new ResponseEntity<>(service.save(dto),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
	    return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateGroupn3(@PathVariable long id, @RequestBody Groupn3Dto dto) {
	    return new ResponseEntity<>(service.updateGroupn3(dto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGroupn3(@PathVariable long id) {
	    return new ResponseEntity<>(service.deleteGroupn3(id), HttpStatus.OK);
	}

	
	@GetMapping("/page")
	public ResponseEntity<?> findAllProductByPagination(
			@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "direction") String direction,
			@RequestParam(value = "search") String search) {
	
	CustomPageResponse<IndexGroupn3> response=null;
	
	if (search.trim().isEmpty()) {
		response = service.FindAllGroupByGroupn3(pageNumber, pageSize, field, direction);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	if (!search.trim().isEmpty()) {
		
		response = service.searchGroupn3(pageNumber, pageSize, field, direction, search);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	return null;
	}
	
	
}
