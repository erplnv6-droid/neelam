package com.SCM.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.DistributorSalesReturnDto;

import com.SCM.service.DistributorSalesReturnService;

@RestController
@RequestMapping("/api/distributorsalesreturn")
public class DistributorSalesReturnController {
	
	@Autowired
	private DistributorSalesReturnService distributorSalesReturnService;
	
	
	@PostMapping("/post")
	
	public ResponseEntity<?> saveDistributorSalesReturn(@RequestBody DistributorSalesReturnDto distributorSalesReturnDto)
	{
		return new ResponseEntity<>(distributorSalesReturnService.saveDistributorSalesReturn(distributorSalesReturnDto),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	
	public ResponseEntity<?> findAllDistributorSalesReturn()
	{
		return new ResponseEntity<>(distributorSalesReturnService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	
	public ResponseEntity<?> getByIdDistributorSalesReturn(@PathVariable long id)
	{
		return new ResponseEntity<>(distributorSalesReturnService.findByDistrubutorSalesReturnId(id),HttpStatus.OK);
	}
	
	@PutMapping("/put/{id}")
	
	public ResponseEntity<?> updateDistributorSalesReturn(@RequestBody DistributorSalesReturnDto distributorSalesReturnDto,@PathVariable long id)
	{
		return new ResponseEntity<>(distributorSalesReturnService.updateDistributorSalesReturn(distributorSalesReturnDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	
	public void deleteById(@PathVariable long id)
	{
		distributorSalesReturnService.deleteDistributorSalesReturn(id);
	}

}
