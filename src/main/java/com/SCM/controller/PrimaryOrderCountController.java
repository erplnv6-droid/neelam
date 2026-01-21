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

import com.SCM.projection.PrimaryOrderCountProjection;
import com.SCM.repository.PrimaryOrderCountRepository;

@RestController
@RequestMapping("/api/count/dist")
public class PrimaryOrderCountController {

	@Autowired
	private PrimaryOrderCountRepository primaryOrderCountRepository;
	
	@GetMapping("/get/month")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getCountWorkOrder().stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/date")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByDateWorkOrderCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getDateCountWorkOrder().stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByYearWorkOrderCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getYearCountWorkOrder().stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/month/distributor/{distrubator_id}")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCountDistributorId(@PathVariable int distrubator_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getCountWorkOrderByDistributorIdMonth(distrubator_id).stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/date/distributor/{distrubator_id}")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCountDistributorIdDate(@PathVariable int distrubator_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getDateCountWorkOrderDistributorIdDate(distrubator_id).stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/year/distributor/{distrubator_id}")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCountDistributorIdYear(@PathVariable int distrubator_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getYearCountWorkOrderDistributorIdYear(distrubator_id).stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}

	@GetMapping("/get/month/staff/{id}")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCountStaffIdMonth(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getCountWorkOrderByStaffMonth(id).stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/date/staff/{id}")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCountStaffIdDate(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getDateCountWorkOrderSatffDate(id).stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year/staff/{id}")
	public ResponseEntity<List<PrimaryOrderCountProjection>> findByWorkOrderCountStaffIdYear(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<PrimaryOrderCountProjection> collect=primaryOrderCountRepository.getYearCountWorkOrderSatffYear(id).stream().map(primaryOrderCountProjection -> modelMapper.map(primaryOrderCountProjection, PrimaryOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<PrimaryOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
}
