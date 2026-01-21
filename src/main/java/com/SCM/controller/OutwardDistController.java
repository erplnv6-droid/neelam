package com.SCM.controller;

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

import com.SCM.dto.OutwardDistributorStockDto;
import com.SCM.repository.OutwardDistStockRepo;
import com.SCM.service.OutwardDistStockService;


@RestController
@RequestMapping("/api/outdiststock")
public class OutwardDistController {

	@Autowired
	private OutwardDistStockService outwardDistStockService;
	
	@Autowired
	private OutwardDistStockRepo outwardDistStockRepo;
	
	
	@PostMapping("/")
	private ResponseEntity<?> save(@RequestBody OutwardDistributorStockDto distributorStockDto)
	{
		return new ResponseEntity<>(outwardDistStockService.save(distributorStockDto),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> showODS() {
		
		return new ResponseEntity<>(outwardDistStockService.getAllOutwardDistributorStock(), HttpStatus.OK);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> showODSById(@PathVariable("id") int id) {
		
		return new ResponseEntity<>(outwardDistStockService.getOutwardDistributorStockById(id), HttpStatus.OK);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateODS(@RequestBody OutwardDistributorStockDto distributorStockDto, @PathVariable("id") int id) {
		
		return new ResponseEntity<>(outwardDistStockService.updateOutwardDistributorStock(distributorStockDto, id), HttpStatus.CREATED);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteODS(@PathVariable("id") int id) {
		
		outwardDistStockService.deleteOutwardDistributorStock(id);
		
		return new ResponseEntity<>("deleted Outwardstock  success", HttpStatus.OK);
	}
	
	
	
	//---------------reports
	
	@GetMapping("/report/{fromdate}/{todate}/{distid}")
	private ResponseEntity<?> reports(@PathVariable("fromdate")String fromdate,
			                          @PathVariable("todate")String todate,
			                          @PathVariable("distid")int distid)
	{
		return new ResponseEntity<>(outwardDistStockRepo.distributorstockreport(fromdate,todate,distid),HttpStatus.OK);
	}
	
	
	
//	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
//	public ResponseEntity<?> IndexRetailer(@PathVariable("pageno") int pageno,
//			                               @PathVariable("pagesize") int pagesize,
//			                               @PathVariable("sortby") String sortby,
//			                               @PathVariable("fromdate")String fromdate,
//			 			                   @PathVariable("todate")String todate,
//			 			                   @PathVariable("distid")int distid) {
//		
//		 if ("asc".equals(sortby)) {
//			 
//			return new ResponseEntity<>(outwardDistStockService.DistributorStockReport(pageno,pagesize,sortby,fromdate,todate,distid), HttpStatus.OK);
//			
//   }     else if("desc".equals(sortby)) {
//			
//			return new ResponseEntity<>(outwardDistStockService.DistributorStockReport(pageno,pagesize,sortby,fromdate,todate,distid),HttpStatus.OK);
//			
//		} else {
//			
//			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
	// Outward Distributor Stock Index
	
	
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	 public ResponseEntity<?> indexOutwardDistribtor(@PathVariable("pageno") int pageno,
				                                     @PathVariable("pagesize") int pagesize,
				                                     @PathVariable("sortby") String sortby,
				                                     @PathVariable("field") String field,
				                                     @PathVariable("search") String search) 
		{   	
		    if(!search.equals(" "))
		    {
			    return new ResponseEntity<>(outwardDistStockService.searchByOutwardDistributorStock(pageno, pagesize,search),HttpStatus.OK) ; 
		    }
    	    else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(outwardDistStockService.indexOutwardDistributorStockAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(outwardDistStockService.indexOutwardDistributorStockDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	 
	
	// Report By distributor
	
	
	@GetMapping("/page/{did}/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	 public ResponseEntity<?> reportOutwardDistribtor(@PathVariable("did") long did,
			                                      @PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search") String search) 
		{  	
		    if(!search.equals(" "))
		    {
			    return new ResponseEntity<>(outwardDistStockService.searchReportByOutwardDistributorStock(did, pageno, pagesize, search),HttpStatus.OK) ; 
		    }
   	        else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(outwardDistStockService.reportOutwardDistributorStockAsc(did, pageno, pagesize, field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(outwardDistStockService.reportOutwardDistributorStockDesc(did, pageno, pagesize, field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	
	
	@GetMapping("/getRetailer/{id}")
	public ResponseEntity<?> getAllRetailer(@PathVariable int id)
	{
		return new ResponseEntity<>(outwardDistStockService.getAllRetailer(id),HttpStatus.OK);
	}
	 
	
}
