package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.SCM.dto.PrintProductBarcodeDto;
import com.SCM.service.PrintProductBarcodeService;

@RestController
@RequestMapping("/api/printproduct")
public class PrintProductBarcodeController {
	
	@Autowired
	private PrintProductBarcodeService printProductBarcodeService;
	
	
	@PostMapping("/post")
	public ResponseEntity<?> createPrintProductBarcode(@RequestBody PrintProductBarcodeDto barcodeDto)
	{
		return new ResponseEntity<>(printProductBarcodeService.createPrintBarcode(barcodeDto),HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllPrintProductBarcode()
	{
		return new ResponseEntity<>(printProductBarcodeService.getAllPrintBarcode(),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getByIdPrintBarcode(@PathVariable long id)
	{
		return new ResponseEntity<>(printProductBarcodeService.findByPrintBarcode(id),HttpStatus.OK);
	}
	
	@PutMapping("/put/{id}")
	public ResponseEntity<?> updatePrintBarcode(@RequestBody PrintProductBarcodeDto barcodeDto,@PathVariable long id)
	{
		return new ResponseEntity<>(printProductBarcodeService.updatePrintProductBarcodeDto(barcodeDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable long id)
	{
		printProductBarcodeService.deletById(id);
	}
	
	@GetMapping("/page/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> indexSearchPrintBarcode(@PathVariable ("pageno") int pageno,
			                                        @PathVariable ("pagesize") int pagesize,
			                                        @PathVariable ("sort") String sort,
			                                        @PathVariable ("field") String field,
			                                        @PathVariable ("search") String search
			                                        )
	{
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(printProductBarcodeService.SearchPrintBarcode(pageno, pagesize, search),HttpStatus.OK);
		}
		else if(sort.equals("asc"))
		{
			return new ResponseEntity<>(printProductBarcodeService.IndexPrintBarcodeAsc(pageno, pagesize, field),HttpStatus.OK);
		}
		else if(sort.equals("desc"))
		{
			return new ResponseEntity<>(printProductBarcodeService.IndexPrintBarcodeDesc(pageno, pagesize, field),HttpStatus.OK);
		}
		else
		{
		  return ResponseEntity.ok("Bad Request");
		}
		
	}

}
