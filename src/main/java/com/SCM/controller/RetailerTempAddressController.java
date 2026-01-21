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

import com.SCM.dto.RetailerTemporaryAddressDto;
import com.SCM.service.RetailerTempAddressService;

@RestController
@RequestMapping("/api/retailer_temp_address")
public class RetailerTempAddressController {
	
	@Autowired
	private RetailerTempAddressService retailerTempAddressService;
	
	
	@GetMapping("/findRetailerTempAddByRTId/{id}")
	public ResponseEntity <List<RetailerTemporaryAddressDto>> findRetailerTempAddByRTId(@PathVariable ("id") Long id)
	{
		List<RetailerTemporaryAddressDto> byRTId = retailerTempAddressService.findRetailerTempAddByRTId(id);
		
		if(byRTId.isEmpty())
		{
			return new ResponseEntity<List<RetailerTemporaryAddressDto>>(HttpStatus.NOT_FOUND);
		}
		
		else
		{
			return new ResponseEntity<List<RetailerTemporaryAddressDto>>(byRTId , HttpStatus.OK);
		}
	}
	
	
	@DeleteMapping("/deleteRetailerTempAddById/{id}")
	public ResponseEntity<String> deleteById(@PathVariable ("id") Long id)
	{
		String deleteById = retailerTempAddressService.deleteById(id);
		
		return new ResponseEntity<String>("Deleted Successfully" , HttpStatus.OK);
	}

}
