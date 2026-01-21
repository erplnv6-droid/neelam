package com.SCM.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SCM.dto.RetailerTempDto;
import com.SCM.service.RetailerTempService;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping("/api/retailertemporary")
public class RetailerTempController {

	
	@Autowired
	public RetailerTempService retailerTempService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> saveTempRetailer(@RequestBody @Valid RetailerTempDto retailerTempDto)
	{
		return new ResponseEntity<>(retailerTempService.save(retailerTempDto),HttpStatus.CREATED);
	}
	
	@PostMapping("/create1")  //create Temporary Retailer
	public ResponseEntity<?> saveTempRetailer1(@RequestBody @Valid RetailerTempDto retailerTempDto)
	{
		return new ResponseEntity<>(retailerTempService.save2(retailerTempDto),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/getAll")
	public ResponseEntity<?> showTempRetailer()
	{
		return new ResponseEntity<>(retailerTempService.getAllTempRetailer(),HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> showTempRetailerById(@PathVariable("id")int id)
	{
		return new ResponseEntity<>(retailerTempService.getTempRetailerById(id),HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteRetailertemp(@PathVariable("id")int id)
	{
		return new ResponseEntity<>(retailerTempService.deleteTempRetailer(id),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(retailerTempService.SearchTemporaryRetailer(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(retailerTempService.IndexTemporaryRetailerAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(retailerTempService.IndexTemporaryRetailerDesc(pageno, pagesize, field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
}
