package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.DistributorMinimumStockDto;
import com.SCM.repository.DistributorMinimumStockRepo;
import com.SCM.service.DistributorMinimumStockService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/dms")
public class DistributorMinimumStockController {

	@Autowired
	private DistributorMinimumStockService distributorMinimumStockService;
	@Autowired
	private DistributorMinimumStockRepo distributorMinimumStockRepo;
	
	

	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody DistributorMinimumStockDto distributorMinimumStock) {

		return new ResponseEntity<>(distributorMinimumStockService.saveDistributorMinimumStock(distributorMinimumStock),HttpStatus.CREATED) ;

	}

	@GetMapping("/")
	public  ResponseEntity<?>  getAll() {

		return new ResponseEntity<>(distributorMinimumStockService.getAllDistributorMinimumStock(),HttpStatus.OK) ;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getbyId(@PathVariable("id") int id) {

		return new ResponseEntity<>(distributorMinimumStockService.getDistributorMinimumStockById(id),HttpStatus.OK) ;
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody DistributorMinimumStockDto distributorMinimumStockDto) {

		return new ResponseEntity<>(distributorMinimumStockService.updateDistributorMinimumStock(distributorMinimumStockDto,id),HttpStatus.OK) ;
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {

		return new ResponseEntity<>(distributorMinimumStockService.deleteDistributorMinimumStock(id),HttpStatus.OK) ;

	}

	
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
		public ResponseEntity<?> IndexDistributorMinimumStock(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search")String search) 
		{   	
	    	if(!search.equals(" "))
			{
				return new ResponseEntity<>(distributorMinimumStockService.SearchDMS(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(distributorMinimumStockService.IndexDMSAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(distributorMinimumStockService.IndexDMSDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	
	 
		
	 @GetMapping("/page/{distid}/{pageno}/{pagesize}/{sortby}/{field}/{search}")
		public ResponseEntity<?> DistributorMinimumStockReport(@PathVariable("distid") int distid,
				                                               @PathVariable("pageno") int pageno,
				                                               @PathVariable("pagesize") int pagesize,
				                                               @PathVariable("sortby") String sortby,
				                                               @PathVariable("field") String field,
				                                               @PathVariable("search")String search) 
		{   	
	    	if(!search.equals(" "))
			{
	    		return null;
//				return new ResponseEntity<>(distributorMinimumStockService.SearchDMS(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(distributorMinimumStockService.IndexDMSReportAsc(pageno,pagesize,field,distid), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(distributorMinimumStockService.IndexDMSReportDesc(pageno,pagesize,field,distid), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	 
	 
	 
	 
	 @GetMapping("/dmsreport/{id}")
		public ResponseEntity<?> sadasas(@PathVariable("id") int id) {

			return new ResponseEntity<>(distributorMinimumStockRepo.MinimumStockForDistributor100(id),HttpStatus.OK) ;
		}

}
