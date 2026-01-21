package com.SCM.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexInvoiceUpload;
import com.SCM.dto.InvoiceUploadDto;
import com.SCM.model.InvoiceUpload;
import com.SCM.service.InvoiceUploadService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceUploadController {

	@Autowired
	private InvoiceUploadService invoiceUploadService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> save(@ModelAttribute InvoiceUploadDto invoiceUploadDto,
			                      @RequestPart("img")MultipartFile img,
			                      @RequestPart("pdf")MultipartFile pdf) throws IOException
	{
		InvoiceUpload saveInvoiceUpload = invoiceUploadService.saveInvoiceUpload(invoiceUploadDto,img,pdf);
		
		return new ResponseEntity<>(saveInvoiceUpload,HttpStatus.OK);
	}
	
	
//	@PutMapping("/update/{id}")
//	public ResponseEntity<?> updateAsset(@PathVariable long id,@RequestBody AssetDto assetDto){
//		String assetupdate = assetService.update(id, assetDto);
//		return new ResponseEntity<>(assetupdate,HttpStatus.OK);
//	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		
		InvoiceUpload invoiceById = invoiceUploadService.getInvoiceById(id);
		return new ResponseEntity<>(invoiceById,HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> getAll(){
		 List<InvoiceUpload> allInvoice = invoiceUploadService.getAllInvoice();
		return new ResponseEntity<>(allInvoice,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id){
		return new ResponseEntity<>(invoiceUploadService.delete(id),HttpStatus.OK);
	}
	
	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search") String search) {

		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(invoiceUploadService.SearchInvoice(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(invoiceUploadService.IndexInvoiceAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(invoiceUploadService.IndexInvoiceDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	@GetMapping("/dist/{distid}")
	public ResponseEntity<?> ggggggggggggge(@PathVariable("distid")int distid){
		 List<IndexInvoiceUpload> list = invoiceUploadService.ffffffffffff(distid);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
