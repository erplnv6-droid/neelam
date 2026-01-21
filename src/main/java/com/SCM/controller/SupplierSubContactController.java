package com.SCM.controller;

import javax.validation.Valid;

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
import com.SCM.dto.SupplierSubContactsDto;
import com.SCM.model.SupplierSubContacts;
import com.SCM.service.SupplierSubContactsService;

@RestController
@RequestMapping("/api/suppliersubcontacts")
@CrossOrigin(origins = "*")
public class SupplierSubContactController {

	
	@Autowired
	private SupplierSubContactsService supplierSubContactsService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> saveSupplierSubConatcts(@RequestBody @Valid SupplierSubContactsDto suppliersubcontactsDto)
	{
		return new ResponseEntity<>(supplierSubContactsService.saveSuppliersubContacts(suppliersubcontactsDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> showSupplierSubConatcts()
	{
		return new ResponseEntity<>(supplierSubContactsService.getAllSupplierSubContacts(),HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showSupplierSubContactsById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(supplierSubContactsService.getSupplierSubContactsById(id),HttpStatus.OK);	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSupplierSubContacts(@PathVariable("id") int id,@RequestBody SupplierSubContacts supplierSubContacts)
	{
	     return new ResponseEntity<>(supplierSubContactsService.updateSupplierSubContacts(supplierSubContacts,id),HttpStatus.CREATED);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSupplierSubContacts(@PathVariable("id") int id)
	{
		supplierSubContactsService.deleteSupplierSubContacts(id);
		
		return new ResponseEntity<>("deleted Supplier sub conatacts success",HttpStatus.OK);	
	}	
}
