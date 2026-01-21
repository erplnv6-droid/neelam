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


import com.SCM.projection.SdnCountProjection;
import com.SCM.repository.SdnCountRepository;

@RestController
@RequestMapping("/api/count/sdn")
public class SdnCountController {

	
	@Autowired
	private SdnCountRepository sdnCountRepository;
	
	
	@GetMapping("/get/month")
	public ResponseEntity<List<SdnCountProjection>> findBySdnCountMonth()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<SdnCountProjection> collect=sdnCountRepository.getMonthCountSdn().stream().map(sdnCountProjection -> modelMapper.map(sdnCountProjection, SdnCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<SdnCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/date")
	public ResponseEntity<List<SdnCountProjection>> findBySdnCountDate()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<SdnCountProjection> collect=sdnCountRepository.getDateCountSdn().stream().map(sdnCountProjection -> modelMapper.map(sdnCountProjection, SdnCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<SdnCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year")
	public ResponseEntity<List<SdnCountProjection>> findBySdnCountYear()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<SdnCountProjection> collect=sdnCountRepository.getYearCountSdn().stream().map(sdnCountProjection -> modelMapper.map(sdnCountProjection, SdnCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<SdnCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/month/supplier/{supplier_id}")
	public ResponseEntity<List<SdnCountProjection>> findBySdnCountMonthBySupplier(@PathVariable int supplier_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<SdnCountProjection> collect=sdnCountRepository.getMonthCountSdnBySupplierId(supplier_id).stream().map(sdnCountProjection -> modelMapper.map(sdnCountProjection, SdnCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<SdnCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/date/supplier/{supplier_id}")
	public ResponseEntity<List<SdnCountProjection>> findBySdnCountDateBySupplier(@PathVariable int supplier_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<SdnCountProjection> collect=sdnCountRepository.getDateCountSdnBySupplierId(supplier_id).stream().map(sdnCountProjection -> modelMapper.map(sdnCountProjection, SdnCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<SdnCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year/supplier/{supplier_id}")
	public ResponseEntity<List<SdnCountProjection>> findBySdnCountYearBySupplier(@PathVariable int supplier_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<SdnCountProjection> collect=sdnCountRepository.getYearCountSdnBySupplierId(supplier_id).stream().map(sdnCountProjection -> modelMapper.map(sdnCountProjection, SdnCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<SdnCountProjection>>(collect,HttpStatus.OK);
			
			
	}
}
