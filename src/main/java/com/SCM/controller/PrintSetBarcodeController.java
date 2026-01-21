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
import com.SCM.dto.PrintSetBarcodeDto;
import com.SCM.service.PrintSetBarcodeService;

@RestController
@RequestMapping("/api/printsetbarcode")
public class PrintSetBarcodeController {
	
	@Autowired
	private PrintSetBarcodeService barcodeService;
	
	@PostMapping("/post")
	public ResponseEntity<?> createPrintSetBarcode(@RequestBody PrintSetBarcodeDto barcodeDto)
	{
		return new ResponseEntity<>(barcodeService.createPrintBarcode(barcodeDto),HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllPrintSetBarcode()
	{
		return new ResponseEntity<>(barcodeService.getAllPrintBarcode(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getByIdPrintBarcode(@PathVariable long id)
	{
		return new ResponseEntity<>(barcodeService.findByPrintBarcode(id),HttpStatus.OK);
	}
	
	@PutMapping("/put/{id}")
	public ResponseEntity<?> updatePrintBarcode(@RequestBody PrintSetBarcodeDto barcodeDto,@PathVariable long id)
	{
		return new ResponseEntity<>(barcodeService.updatePrintSetBarcodeDto(barcodeDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable long id)
	{
		barcodeService.deletById(id);
	}
	
	@GetMapping("/page/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> indexPrintSetBarcode(@PathVariable ("pageno") int pageno,
			@PathVariable ("pagesize") int pagesize,
			@PathVariable ("sort") String sort,
			@PathVariable ("field") String field,
			@PathVariable ("search") String search)
	
	{
		
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(barcodeService.SearchPrintBarcode(pageno, pagesize, search),HttpStatus.OK);
		}
		else if(sort.equals("asc"))
		{
			return new ResponseEntity<>(barcodeService.IndexPrintBarcodeAsc(pageno, pagesize, field),HttpStatus.OK);
			
		}
		else if(sort.equals("desc"))
		{
			return new ResponseEntity<>(barcodeService.IndexPrintBarcodeDesc(pageno, pagesize, field),HttpStatus.OK);
		}
		else
		{
			return ResponseEntity.ok("Bad Request");
		}
		
	
		
	}

}
