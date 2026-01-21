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
import com.SCM.dto.MaterialRecepitNoteDto;
import com.SCM.service.MaterialReceiptNoteService;

@RestController
@RequestMapping("/api/mrn")
@CrossOrigin(origins = "*")
public class MaterialReceiptNoteController {

	@Autowired
	public MaterialReceiptNoteService materialReceiptNoteService;
	

	@PostMapping("/")
	public ResponseEntity<?> saveMRN(@RequestBody MaterialRecepitNoteDto materialRecepitNoteDto)
	{
		return new ResponseEntity<>(materialReceiptNoteService.saveMaterialRecepitNote(materialRecepitNoteDto),HttpStatus.CREATED);
	}
	
	
	@PostMapping("/sdn/{supplierdeliverynoteId}")
	public ResponseEntity<?> ConvertMRNtoSupplierDeliveryNote(@RequestBody MaterialRecepitNoteDto materialRecepitNoteDto,@PathVariable("supplierdeliverynoteId")int supplierdeliverynoteId)
	{
		return new ResponseEntity<>(materialReceiptNoteService.ConvertoMRNSupplierdeliverynote(materialRecepitNoteDto,supplierdeliverynoteId),HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> showMRN()
	{
		return new ResponseEntity<>(materialReceiptNoteService.getAllMaterialRecepitNote(),HttpStatus.OK);	
	}
	
	@GetMapping("/sdn/{supplierdeliverynoteId}")
	public ResponseEntity<?> showMRNbySupplierDeliverynote(@PathVariable("supplierdeliverynoteId") int supplierdeliverynoteId)
	{
		return new ResponseEntity<>(materialReceiptNoteService.getMaterialRecepitNoteBySupplierdeliveryId(supplierdeliverynoteId),HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showMRNById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(materialReceiptNoteService.getMaterialRecepitNoteById(id),HttpStatus.OK);	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMRN(@PathVariable("id") int id,@RequestBody MaterialRecepitNoteDto materialRecepitNoteDto)
	{
	     return new ResponseEntity<>(materialReceiptNoteService.updateMaterialRecepitNote(materialRecepitNoteDto,id),HttpStatus.ACCEPTED);	
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMRN(@PathVariable("id") int id)
	{
		materialReceiptNoteService.deleteMaterialRecepitNote(id);
		return new ResponseEntity<>("deleted MAterial receipt note success",HttpStatus.OK);	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id)
	{
		materialReceiptNoteService.deleteMaterialRecepitNote1(id);
		return new ResponseEntity<>("Manuelly deleted MAterial receipt note success",HttpStatus.OK);	
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
				return new ResponseEntity<>(materialReceiptNoteService.searchByMaterialReceipt(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(materialReceiptNoteService.indexMaterialReceiptAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(materialReceiptNoteService.indexMaterialReceiptDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	    

}
