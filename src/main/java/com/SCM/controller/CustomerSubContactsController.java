package com.SCM.controller;

import javax.validation.Valid;

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
import com.SCM.dto.CustomerSubContactsDto;
import com.SCM.model.CustomerSubContacts;
import com.SCM.service.CustomerSubContactsService;


@RestController
@RequestMapping("/api/customersubcontact")
public class CustomerSubContactsController {

	@Autowired
	private CustomerSubContactsService subContactService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveSubConatcts(@RequestBody @Valid CustomerSubContactsDto customerSubContactsDto)
	{
		return new ResponseEntity<>(subContactService.savesubContacts(customerSubContactsDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> showSubConatcts()
	{
		return new ResponseEntity<>(subContactService.getAllSubContacts(),HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showSubContactsById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(subContactService.getSubContactsById(id),HttpStatus.OK);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSubContacts(@PathVariable("id") int id,@RequestBody CustomerSubContacts customerSubContacts)
	{
	     return new ResponseEntity<>(subContactService.updateSubContacts(customerSubContacts,id),HttpStatus.ACCEPTED);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubContacts(@PathVariable("id") int id)
	{
		subContactService.deleteSubContacts(id);
		return new ResponseEntity<>("deleted customer sub conatacts success",HttpStatus.OK);	
	}	
}
