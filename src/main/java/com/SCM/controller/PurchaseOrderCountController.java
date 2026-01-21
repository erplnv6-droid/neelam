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

import com.SCM.projection.PurchaseOrderCountProjection;

import com.SCM.repository.PurchaseOrderCountRepository;


@RestController
@RequestMapping("/api/count/po")
public class PurchaseOrderCountController {

	@Autowired
	private PurchaseOrderCountRepository purchaseOrderCountRepository;
	
	@GetMapping("/get/month")
	public ResponseEntity<List<PurchaseOrderCountProjection>> findByPoCountMonth()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PurchaseOrderCountProjection> collect=purchaseOrderCountRepository.getMonthCountPurchaseOrder().stream().map(purchaseOrderCountProjection -> modelMapper.map(purchaseOrderCountProjection, PurchaseOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PurchaseOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/date")
	public ResponseEntity<List<PurchaseOrderCountProjection>> findByPoCountDate()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PurchaseOrderCountProjection> collect=purchaseOrderCountRepository.getDateCountPurchaseOrder().stream().map(purchaseOrderCountProjection -> modelMapper.map(purchaseOrderCountProjection, PurchaseOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PurchaseOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year")
	public ResponseEntity<List<PurchaseOrderCountProjection>> findByPoCountYear()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PurchaseOrderCountProjection> collect=purchaseOrderCountRepository.getYearCountPurchaseOrder().stream().map(purchaseOrderCountProjection -> modelMapper.map(purchaseOrderCountProjection, PurchaseOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PurchaseOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/month/supplier/{supplier_id}")
	public ResponseEntity<List<PurchaseOrderCountProjection>> findByPoCountMonthBySupplierId(@PathVariable int supplier_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PurchaseOrderCountProjection> collect=purchaseOrderCountRepository.getMonthCountPurchaseOrderBySupplierId(supplier_id).stream().map(purchaseOrderCountProjection -> modelMapper.map(purchaseOrderCountProjection, PurchaseOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PurchaseOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/date/supplier/{supplier_id}")
	public ResponseEntity<List<PurchaseOrderCountProjection>> findByPoCountDateBySupplierId(@PathVariable int supplier_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PurchaseOrderCountProjection> collect=purchaseOrderCountRepository.getDateCountPurchaseOrderBySupplierId(supplier_id).stream().map(purchaseOrderCountProjection -> modelMapper.map(purchaseOrderCountProjection, PurchaseOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PurchaseOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year/supplier/{supplier_id}")
	public ResponseEntity<List<PurchaseOrderCountProjection>> findByPoCountYearBySupplierId(@PathVariable int supplier_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PurchaseOrderCountProjection> collect=purchaseOrderCountRepository.getYearCountPurchaseOrderBySupplierId(supplier_id).stream().map(purchaseOrderCountProjection -> modelMapper.map(purchaseOrderCountProjection, PurchaseOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PurchaseOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
}
