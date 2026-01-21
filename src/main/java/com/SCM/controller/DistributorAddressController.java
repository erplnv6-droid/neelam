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
import com.SCM.service.DistributorAddressService;

@RestController
@RequestMapping("/api/distributor_address")
public class DistributorAddressController {
	
	@Autowired
	private DistributorAddressService distributorAddressService;
	
	
	@GetMapping("/findDistributorAddressByDistributor/{id}")
	public ResponseEntity<List<DistributorAddressDto>> findDistributorAddressByDistributor(@PathVariable ("id") Long id)
	{
		List<DistributorAddressDto> byDistributor = distributorAddressService.findDistributorAddressByDistributor(id);
		
		if(byDistributor.isEmpty())
		{
			return new ResponseEntity<List<DistributorAddressDto>>(HttpStatus.NOT_FOUND);
		}
		
		else
		{
			return new ResponseEntity<List<DistributorAddressDto>>(byDistributor , HttpStatus.OK);
		}
	}
	
	
	@DeleteMapping("/deleteDistAddressById/{id}")
	public ResponseEntity<String> deleteDistAddressById(@PathVariable ("id") Long id)
	{
		distributorAddressService.deleteByDistributorAddressId(id);
		
		return new ResponseEntity<String>("Deleted Successfully" , HttpStatus.OK);
	}

}
