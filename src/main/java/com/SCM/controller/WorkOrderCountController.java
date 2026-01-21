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


import com.SCM.projection.WorkOrderCountProjection;
import com.SCM.repository.WorkOrderCountRepository;

@RestController
@RequestMapping("/api/count")
public class WorkOrderCountController {

	@Autowired
	private WorkOrderCountRepository orderCountRepository;
	
	@GetMapping("/get/month")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getCountWorkOrder().stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/date")
	public ResponseEntity<List<WorkOrderCountProjection>> findByDateWorkOrderCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getDateCountWorkOrder().stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/year")
	public ResponseEntity<List<WorkOrderCountProjection>> findByYearWorkOrderCount()
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getYearCountWorkOrder().stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	

	@GetMapping("/get/month/distributor/{distributor_id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountByDistributorIdMonth(@PathVariable int distributor_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getCountWorkOrderByDistributorIdMonth(distributor_id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/date/distributor/{distributor_id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountByDistributorIdDate(@PathVariable int distributor_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getDateCountWorkOrderByDistributorIdDate(distributor_id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/year/distributor/{distributor_id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountByDistributorIdYear(@PathVariable int distributor_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getYearCountWorkOrderByDistributorIdYear(distributor_id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	@GetMapping("/get/month/retailer/{retailer_id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountByReatilerIdMonth(@PathVariable int retailer_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getCountWorkOrderByRetailerMonth(retailer_id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/date/retailer/{retailer_id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountByRetailerIdDate(@PathVariable int retailer_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getDateCountWorkOrderByRetailerDate(retailer_id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/year/retailer/{retailer_id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountByRetailerIdYear(@PathVariable int retailer_id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getYearCountWorkOrderByRetailerYear(retailer_id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/month/staff/{id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountBySatffIdMonth(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getYearCountWorkOrderBySatffMonth(id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	
	@GetMapping("/get/date/staff/{id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountBySatffIdDate(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getYearCountWorkOrderBySatffDate(id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	@GetMapping("/get/year/staff/{id}")
	public ResponseEntity<List<WorkOrderCountProjection>> findByWorkOrderCountBySatffIdYear(@PathVariable int id)
	{
			ModelMapper modelMapper=new ModelMapper();
			List<WorkOrderCountProjection> collect=orderCountRepository.getYearCountWorkOrderBySatffYear(id).stream().map(workOrderCountProjection -> modelMapper.map(workOrderCountProjection, WorkOrderCountProjection.class))
					.collect(Collectors.toList());
			
		
			return new ResponseEntity<List<WorkOrderCountProjection>>(collect,HttpStatus.OK);
			
			
	}
	
	
	
}
