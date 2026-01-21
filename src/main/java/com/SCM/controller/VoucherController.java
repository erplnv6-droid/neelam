package com.SCM.controller;

import java.util.List;
import java.util.Optional;

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

import com.SCM.IndexDto.IndexVoucher;
import com.SCM.dto.VoucherModelDto;
import com.SCM.model.Voucher;
import com.SCM.service.VoucherService;
import com.google.api.Http;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

	@Autowired
	private VoucherService voucherService;
	
	@PostMapping("/save")
	public  ResponseEntity<Voucher>saveVoucher(@RequestBody VoucherModelDto voucherDto) {
		return new ResponseEntity<Voucher>(voucherService.saveVoucher(voucherDto),HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<List<Voucher>> getAllVoucher(){
		return new ResponseEntity<List<Voucher>>(voucherService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Optional<Voucher>> getById(@PathVariable long id){
		return new ResponseEntity<Optional<Voucher>>(voucherService.getById(id),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateVoucher(@RequestBody Voucher modelDto,@PathVariable long id){
		return new ResponseEntity<String>(voucherService.updateVoucher(modelDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id){
		return new ResponseEntity<String>(voucherService.deleteVoucher(id),HttpStatus.OK);
	}

	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexWarehouse(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(voucherService.SearchVoucher(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(voucherService.IndexVoucherAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(voucherService.IndexVoucherDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}	
	
	
	@GetMapping("/get")
	
	public ResponseEntity<List<IndexVoucher>> getAll()
	{
		return new ResponseEntity<>(voucherService.getAllVoucher(),HttpStatus.OK);
	}
	
	
}
