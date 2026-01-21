package com.SCM.controller;

import java.io.IOException;
import java.util.List;

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

import com.SCM.dto.MasterCartoonDto;
import com.SCM.model.MasterCartoon;
import com.SCM.service.MasterCartoonService;
import com.google.zxing.WriterException;


@RestController
@RequestMapping("/api/mastercartoon")
public class MasterCartoonController {

	@Autowired
	private MasterCartoonService masterCartoonService;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveMasterCartoon(@RequestBody MasterCartoonDto cartoonDto){
		return new ResponseEntity<>(masterCartoonService.saveMasterCartoon(cartoonDto),HttpStatus.OK);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		return new ResponseEntity<>(masterCartoonService.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getll")
	public ResponseEntity<?> getAllMasterCartoon(){
		return new ResponseEntity<>(masterCartoonService.getAll(),HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?>updateCartoon(@PathVariable long id,@RequestBody MasterCartoonDto cartoonDto) throws WriterException, IOException{
		return new ResponseEntity<>(masterCartoonService.updateMasterCartoon(id, cartoonDto),HttpStatus.OK);
	}
	

//	@Autowired
//	private MasterCartoonRepo masterCartoonRepo;
//	@Autowired
//	private ProductRepo productRepo;
//	@Autowired
//	private BrandRepo brandRepo;
//	@Autowired
//	private CartonBarcodeRepo cartonBarcodeRepo;
//	
	
//	@PutMapping("/update/{id}")
//	public ResponseEntity<?>updateCartoon(@PathVariable long id,@RequestBody MasterCartoonDto cartoonDto) throws WriterException, IOException{
//		
//if (masterCartoonRepo.existsById(id)) {
//			
//			MasterCartoon masterCartoon = masterCartoonRepo.findById(id).get();
//			int id2 = masterCartoon.getProduct().getId();
//			Long id3 = masterCartoon.getBrand().getId();
//			Product product = productRepo.findById(id2).get();
//			Brand brand = brandRepo.findById(id3).get();
//			
//			cartonBarcodeRepo.findByMasterCartoon(masterCartoon);
//			if(cartonBarcodeRepo.existsByMasterCartoon(masterCartoon) || masterCartoonRepo.existsByProductAndBrand(product, brand) )      
//			{
//				return new ResponseEntity<>("this is already exist",HttpStatus.OK);
//			}
//		
//		return new ResponseEntity<>("succesfully updated",HttpStatus.OK);
//	}
//return new ResponseEntity<>("no master is present with id"+id,HttpStatus.OK);
//
//
//	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCartoon(@PathVariable long id){
		return new ResponseEntity<>(masterCartoonService.deleteMasterCartoon(id),HttpStatus.OK);
	}
	
	@PutMapping("/update/normal/{id}")
	public ResponseEntity<?> updateMasterCartoon(@PathVariable long id,@RequestBody MasterCartoonDto dto){
		return new ResponseEntity<>(masterCartoonService.updateNormal(id, dto),HttpStatus.OK);
	}
	
	
	@GetMapping("/bybrand/{id}")
	public ResponseEntity<?> findByBrandid(@PathVariable long id){
		List<MasterCartoon> byBrand = masterCartoonService.getByBrand(id);
		return new ResponseEntity<>(byBrand,HttpStatus.OK);
	}
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAllDistributor(@PathVariable("pageno") int pageno,
			                                     @PathVariable("pagesize") int pagesize,
			                                     @PathVariable("sortby") String sortby,
			                                     @PathVariable("field") String field,
			                                     @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(masterCartoonService.SearchMasterCarton(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(masterCartoonService.IndexMasterCartonAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(masterCartoonService.IndexMasterCartonDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	

	@GetMapping("/bomproduct/{bid}")
	public ResponseEntity<?> fetchProductFromBom(@PathVariable("bid")long bid){
		return new ResponseEntity<>(masterCartoonService.fetchProductfromBom(bid),HttpStatus.OK);
	}
}
