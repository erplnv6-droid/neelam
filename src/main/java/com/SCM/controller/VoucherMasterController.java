package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.model.VoucherMaster;
import com.SCM.service.VoucherMasterService;

@RestController
@RequestMapping("/api/vouchermaster")
public class VoucherMasterController {

	@Autowired
	private VoucherMasterService voucherMasterService;

	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody VoucherMaster master) {
		if(master.getPrefil().equals("Yes") && master.getWidth()==0)
		{
			return ResponseEntity.badRequest().body("Please set width when Prefil is 'Yes'");
		}
		else
		{
		return new ResponseEntity<>(voucherMasterService.save(master), HttpStatus.CREATED);
		}
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> get() {

		return new ResponseEntity<>(voucherMasterService.get(), HttpStatus.OK);

	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable int id)
	{
		return new ResponseEntity<>(voucherMasterService.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getsdn")
	public ResponseEntity<?> getByIdSdn()
	{
		return new ResponseEntity<>(voucherMasterService.getSdn(),HttpStatus.OK);
	}
	
	@GetMapping("/getmrn")
	public ResponseEntity<?> getByIdMrn()
	{
		return new ResponseEntity<>(voucherMasterService.getMrn(),HttpStatus.OK);
	}
	
	@GetMapping("/getpurchase")
	public ResponseEntity<?> getByIdPurchase()
	{
		return new ResponseEntity<>(voucherMasterService.getPurchase(),HttpStatus.OK);
	}
	
	@GetMapping("/getpr")
	public ResponseEntity<?> getByIdPr()
	{
		return new ResponseEntity<>(voucherMasterService.getPr(),HttpStatus.OK);
	}
	
	@GetMapping("/getso")
	public ResponseEntity<?> getByIdSo()
	{
		return new ResponseEntity<>(voucherMasterService.getSo(),HttpStatus.OK);
	}
	
	@GetMapping("/getdc")
	public ResponseEntity<?> getByIdDc()
	{
		return new ResponseEntity<>(voucherMasterService.getDc(),HttpStatus.OK);
	}
	
	@GetMapping("/getsale")
	public ResponseEntity<?> getByIdSale()
	{
		return new ResponseEntity<>(voucherMasterService.getSale(),HttpStatus.OK);
	}
	
	@GetMapping("/getsr")
	public ResponseEntity<?> getByIdSr()
	{
		return new ResponseEntity<>(voucherMasterService.getSr(),HttpStatus.OK);
	}
	
	@GetMapping("/getdo")
	public ResponseEntity<?> getByIdDo()
	{
		return new ResponseEntity<>(voucherMasterService.getDo(),HttpStatus.OK);
	}
	
	@GetMapping("/getdi")
	public ResponseEntity<?> getByIdDi()
	{
		return new ResponseEntity<>(voucherMasterService.getDi(),HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexVoucherMaster(@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby,
			@PathVariable("field") String field, 
			@PathVariable("search") String search) {
		if (!search.equals(" ")) {

			return new ResponseEntity<>(voucherMasterService.searchByVouchers(pageno, pagesize, search), HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(voucherMasterService.indexVoucherAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(voucherMasterService.indexVoucherDesc(pageno, pagesize, field), HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public void  deleteById(@PathVariable int id)
	{
		voucherMasterService.deleteById(id);
	}
	
}
