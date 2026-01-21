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
import com.SCM.dto.SupplierDeliveryNoteDto;
import com.SCM.service.SupplierDeliveryNoteService;

@RestController
@RequestMapping("/api/sdn")
@CrossOrigin(origins = "*")
public class SupplierDeliveryNoteController {
    
	@Autowired
	private SupplierDeliveryNoteService supplierDeliveryNoteService;
	
	@PostMapping("/")
	public ResponseEntity<?> savedSupplierDeliveryNote(@RequestBody SupplierDeliveryNoteDto supplierDeliveryNoteDto)
	{
		return new ResponseEntity<>(supplierDeliveryNoteService.saveSupplierDeliveryNote(supplierDeliveryNoteDto),HttpStatus.CREATED);
	}
	
	
	@PostMapping("/{poId}")
	public ResponseEntity<?> ConvertPotoSDN(@RequestBody SupplierDeliveryNoteDto supplierDeliveryNoteDto,@PathVariable("poId")int poId)
	{
		return new ResponseEntity<>(supplierDeliveryNoteService.ConvertPotoSDN(supplierDeliveryNoteDto,poId),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> showSupplierDeliveryNote()
	{
		return new ResponseEntity<>(supplierDeliveryNoteService.getAllSupplierDeliveryNote(),HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showSupplierDeliveryNoteById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(supplierDeliveryNoteService.getSupplierDeliveryNoteById(id),HttpStatus.OK);	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSSupplierDeliveryNote(@PathVariable("id") int id,@RequestBody SupplierDeliveryNoteDto supplierDeliveryNoteDto)
	{
	     return new ResponseEntity<>(supplierDeliveryNoteService.updateSupplierDeliveryNote(supplierDeliveryNoteDto,id),HttpStatus.ACCEPTED);	
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSupplierDeliveryNote(@PathVariable("id") int id)
	{
		supplierDeliveryNoteService.deleteSupplierDeliveryNote(id);
		return new ResponseEntity<>("status deleted Supplierdelivery note success",HttpStatus.OK);	
	}	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSupplierDeliveryNote1(@PathVariable("id") int id)
	{
		supplierDeliveryNoteService.deleteSupplierDeliveryNote1(id);
		return new ResponseEntity<>("Mauel deleted Supplierdelivery note success",HttpStatus.OK);	
	}	
	
	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexSupplierDelievery(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(supplierDeliveryNoteService.searchByIndexSupplierDelivery(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(supplierDeliveryNoteService.indexSupplierDeliveryAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(supplierDeliveryNoteService.indexSupplierDeliveryDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
    	
    	
    	
	}
	
}
