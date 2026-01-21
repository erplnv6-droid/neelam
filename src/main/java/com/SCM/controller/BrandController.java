package com.SCM.controller;

import com.SCM.model.Brand;
import com.SCM.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllBrand() {

		List<Brand> b = brandService.getAllBrands();

		if (b != null) {

			return new ResponseEntity<>(b, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("brand name must be unique", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Brand> get(@PathVariable Long id) {
		try {
			Brand brand = brandService.getBrandById(id);
			return new ResponseEntity<Brand>(brand, HttpStatus.OK);
		} catch (NoSuchElementException e) {

		
			return new ResponseEntity<Brand>(HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Brand brand) {
		try {

			return new ResponseEntity<>(brandService.saveBrand(brand), HttpStatus.CREATED);

		} catch (DataIntegrityViolationException e) {

			return new ResponseEntity<>("brand name must be unique", HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		brandService.deleteBrand(id);
		return new ResponseEntity<String>("Your Brand is SuccessFully Deleted!!", HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public Brand updateUser(@PathVariable("id") long id, @RequestBody Brand brand) {
		return brandService.updateBrand(brand, id);
	}
	
	
    @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{   	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(brandService.SearchBrand(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(brandService.IndexBrandAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(brandService.IndexBrandDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
}
