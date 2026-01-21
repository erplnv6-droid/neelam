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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.PurchaseOrderDto;
import com.SCM.service.POService;

@RestController
@RequestMapping("/api/po")
@CrossOrigin(origins = "*")
public class POController {

	@Autowired
	private POService poService;

	
	@PostMapping("/")
	public ResponseEntity<?> savePO(@Valid @RequestBody PurchaseOrderDto purchaseOrderDto) {
		
		return new ResponseEntity<>(poService.savePO(purchaseOrderDto), HttpStatus.CREATED);
	}
	

	@GetMapping("/getAll")
	public ResponseEntity<?> showPO() {
		
		return new ResponseEntity<>(poService.getAllPO(), HttpStatus.OK);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> showPOById(@PathVariable("id") int id) {
		
		return new ResponseEntity<>(poService.getPOById(id), HttpStatus.OK);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePO(@RequestBody PurchaseOrderDto purchaseOrderDto, @PathVariable("id") int id) {
		
		return new ResponseEntity<>(poService.updatePO(purchaseOrderDto, id), HttpStatus.CREATED);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePO(@PathVariable("id") int id) {
		
		poService.deletePO(id);
		return new ResponseEntity<>("deleted Purchase Order success", HttpStatus.OK);
	}
	
	
	
  //-------------------   index PO
    
    
    @GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexPo(@PathVariable("pageno") int pageno,
			                         @PathVariable("pagesize") int pagesize,
			                         @PathVariable("sortby") String sortby,
			                         @PathVariable("field") String field,
			                         @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(poService.SearchPO(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(poService.IndexPOAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(poService.IndexPODesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
    
}
