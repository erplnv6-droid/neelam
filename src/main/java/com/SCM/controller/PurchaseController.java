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

import com.SCM.dto.PurchaseDto;
import com.SCM.model.Purchase;
import com.SCM.service.PurchaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> savedPurchase(@RequestBody PurchaseDto purchaseDto)
	{
		return new ResponseEntity<>(purchaseService.savePurchase(purchaseDto),HttpStatus.OK);
	}
	
	@PostMapping("/po/{poId}")
	public ResponseEntity<?> ConvertPotoPurchase(@RequestBody PurchaseDto purchaseDto,@PathVariable("poId")int poId)
	{
		return new ResponseEntity<>(purchaseService.ConvertPotoPurchase(purchaseDto,poId),HttpStatus.OK);
	}
	
//	 convert mrn to purchase
	
	@PostMapping("/mrn/{mrnId}")
	public ResponseEntity<?> ConvertMrntoPurchase(@RequestBody PurchaseDto purchaseDto,@PathVariable("mrnId")int mrnId)
	{
		return new ResponseEntity<>(purchaseService.ConvertMRNtoPurchase(purchaseDto,mrnId),HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> showPurchase()
	{
		return new ResponseEntity<>(purchaseService.getAllPurchase(),HttpStatus.OK);	
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showPurchaseById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(purchaseService.getPurchaseById(id),HttpStatus.OK);	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePurchase(@PathVariable("id") int id,@RequestBody PurchaseDto purchaseDto)
	{
	     return new ResponseEntity<>(purchaseService.updatePurchase(purchaseDto,id),HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePurchase(@PathVariable("id") int id)
	{
		purchaseService.deletePurchase(id);
		return new ResponseEntity<>("deleted Purchase success",HttpStatus.OK);	
	}	
	
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePurchseConvert(@PathVariable int id){
		String deletePurchaseConvert = purchaseService.deletePurchaseConvert(id);
		return new ResponseEntity<>(deletePurchaseConvert,HttpStatus.OK);
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
				return new ResponseEntity<>(purchaseService.searchByPurchase(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(purchaseService.indexPurchaseAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(purchaseService.indexPurchaseDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	 
	 
	
}
