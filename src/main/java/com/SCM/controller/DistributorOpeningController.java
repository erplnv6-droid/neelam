package com.SCM.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SCM.dto.DistributorOpeningStockDto;
import com.SCM.model.DistributorOpeningStock;
import com.SCM.service.DistributorOpeningService;



@RestController
@RequestMapping("/api/distributor/openingstock")
public class DistributorOpeningController {

	
	@Autowired
	
	private DistributorOpeningService distributorOpeningService;
	
	
	@PostMapping("/post")
	public ResponseEntity<DistributorOpeningStockDto> createDistributorOpening(@RequestBody DistributorOpeningStockDto distributorOpeningStockDto)
	{
//		distributorOpeningStockDto.setDate(new Date());
		DistributorOpeningStockDto openingStockDto=distributorOpeningService.createOpeningStock(distributorOpeningStockDto);
		
		return new ResponseEntity<DistributorOpeningStockDto>(openingStockDto,HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<DistributorOpeningStockDto>> findByDistributorStock()
	{
		List<DistributorOpeningStockDto> distributorStockDto=distributorOpeningService.findAllOpeningStock();
		
		return new ResponseEntity<List<DistributorOpeningStockDto>>(distributorStockDto,HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> findByIdDistributorStock(@PathVariable long id)
	{
		DistributorOpeningStock byOpeningStockId1 = distributorOpeningService.findByOpeningStockId1(id);
		
		return new ResponseEntity<>(byOpeningStockId1,HttpStatus.OK);
	}
	
	
	@PutMapping("/put/{id}")
	public ResponseEntity<?> updateByDistributor(@PathVariable long id,@RequestBody DistributorOpeningStockDto distributorOpeningStockDto)
	{		
		DistributorOpeningStock update = distributorOpeningService.update(distributorOpeningStockDto,id);
		
	    return new ResponseEntity<>(update,HttpStatus.OK);	
	}
	
	@DeleteMapping("/delete/{id}")
	
	public String deleteDistributorId(@PathVariable long id)
	{
		distributorOpeningService.deleteByOpeningStockId(id);
		return "Data Deleted Successfully"; 
	}
	
	@DeleteMapping("/delete")
	public String deleteAllDistributor()
	{
		distributorOpeningService.deleteAllOpeningStock();
		return "All Data Deleted Successfully";
	}
	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributroOpeningStock(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(distributorOpeningService.searchByDistributorOpeningStock(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(distributorOpeningService.indexDistributorOpeningStockAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(distributorOpeningService.indexDistributorOpeningStockDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
    	
    	
    	
	}
	

}
