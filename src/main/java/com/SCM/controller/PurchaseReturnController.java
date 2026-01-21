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
import com.SCM.dto.PurchaseReturnDto;
import com.SCM.service.PurchaseReturnService;

@RestController
@RequestMapping("/api/pr")
@CrossOrigin(origins = "*")
public class PurchaseReturnController {

	@Autowired
	public PurchaseReturnService purchaseReturnService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> savePurchaseReturn(@RequestBody PurchaseReturnDto purchaseReturnDto)
	{
		return new ResponseEntity<>(purchaseReturnService.savePurchaseReturn(purchaseReturnDto),HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> showPurchaseReturn()
	{
		return new ResponseEntity<>(purchaseReturnService.getAllPurchaseReturn(),HttpStatus.OK);	
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showPurchaseReturnById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(purchaseReturnService.getPurchaseReturnById(id),HttpStatus.OK);	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePurchaseReturn(@PathVariable("id") int id,@RequestBody PurchaseReturnDto purchaseReturnDto)
	{
	     return new ResponseEntity<>(purchaseReturnService.updatePurchaseReturn(purchaseReturnDto,id),HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePurchaseReturn(@PathVariable("id") int id)
	{
		purchaseReturnService.deletePurchaseReturn(id);
		return new ResponseEntity<>("deleted Purchase Return success",HttpStatus.OK);	
	}	
	
	
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	 public ResponseEntity<?> indexMaterialReceipt(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search")String search) 
		{
	    	
	    	if(!search.equals(" "))
			{
				return new ResponseEntity<>(purchaseReturnService.searchByPurchaseReturn(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(purchaseReturnService.indexPurchaseReturnAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(purchaseReturnService.indexPurchaseReturnDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	
}
