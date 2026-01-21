package com.SCM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.DistributorAddressDto;
import com.SCM.model.SupplierAddress;
import com.SCM.service.DistributorAddressService;
import com.SCM.service.SupplierAddressService;

@RestController
@RequestMapping("api/supplieraddress/")
public class SupplierAddressController {

	@Autowired
	private SupplierAddressService supplierAddressService;

	
	@GetMapping("/getsupplieraddbysupplier/{id}")
	public ResponseEntity<?> GetSupplierAddressBySupplier(@PathVariable("id") Long id) {
		
		List<SupplierAddress> allAddressBySupplierId = supplierAddressService.findAllAddressBySupplierId(id);

		return new ResponseEntity<>(allAddressBySupplierId, HttpStatus.OK);

	}

	@DeleteMapping("/deleteSupAddressById/{id}")
	public ResponseEntity<String> deleteDistAddressById(@PathVariable("id") Long id) {
		
		supplierAddressService.deleteSupplierAddress(id);

		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}
}
