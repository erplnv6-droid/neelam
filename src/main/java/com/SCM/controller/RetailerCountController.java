package com.SCM.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.projection.RetailerCountProjection;
import com.SCM.projection.WorkOrderCountProjection;
import com.SCM.repository.RetailerCountRepository;
import com.SCM.repository.WorkOrderCountRepository;

@RestController
@RequestMapping("/api/ret/count")
public class RetailerCountController {

	
	@Autowired
	private RetailerCountRepository retailerCountRepository;
	
	@GetMapping("/get")
	public ResponseEntity<List<RetailerCountProjection>> findByRetailerMonthCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<RetailerCountProjection> collect=retailerCountRepository.getCountReatiler().stream().map(retailerCountProjection -> modelMapper.map(retailerCountProjection, RetailerCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<RetailerCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	
	
	@GetMapping("/get/distributor/{distributor_id}")
	public ResponseEntity<List<RetailerCountProjection>> findCountReatilerByDistributorIdMonth(@PathVariable int distributor_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<RetailerCountProjection> collect=retailerCountRepository.getCountReatilerByDistributorId(distributor_id).stream().map(retailerCountProjection -> modelMapper.map(retailerCountProjection, RetailerCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<RetailerCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	

	@GetMapping("/get/staff/{id}")
	public ResponseEntity<List<RetailerCountProjection>> findByRetailerSatff(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<RetailerCountProjection> collect=retailerCountRepository.getCountReatilerByStaff(id).stream().map(retailerCountProjection -> modelMapper.map(retailerCountProjection, RetailerCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<RetailerCountProjection>>(collect,HttpStatus.OK);
			
			
	
	
	}

	
	
}
