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

import com.SCM.dto.RetailerAddressDto;
import com.SCM.model.RetailerAddress;
import com.SCM.service.RetailerAddressService;

@RestController
@RequestMapping("/api/retialer_address")
public class RetailerAddressController {
	
	@Autowired
	private RetailerAddressService retailerAddressService;
	
	@GetMapping("/findAllAddressByRetailerId/{id}")
	public ResponseEntity<List<RetailerAddressDto>> findAllAddressByRetailerId(@PathVariable ("id") Long id)
	{
		List<RetailerAddressDto> byRetailerId = retailerAddressService.findAllAddressByRetailerId(id);
		
		if(byRetailerId.isEmpty())
		{
			return new ResponseEntity<List<RetailerAddressDto>>(HttpStatus.NOT_FOUND);
		}
		
		else
		{
		    return new ResponseEntity<List<RetailerAddressDto>>(byRetailerId , HttpStatus.OK);
		}
	}

	
	@DeleteMapping("/deleteRetailerAddress/{id}")
	public ResponseEntity<String> deleteRetailerAddress(@PathVariable ("id") Long id)
	{
		String address = retailerAddressService.deleteRetailerAddress(id);
		
		return new ResponseEntity<String>("Retailer Address Deleted Successfully" , HttpStatus.OK);
	}
}
