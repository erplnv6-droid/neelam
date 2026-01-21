package com.SCM.controller;

import java.util.List;

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

import com.SCM.dto.WarehouseDto;
import com.SCM.model.Warehouse;
import com.SCM.service.WarehouseService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
//	@Autowired
//	private WarehouseRepository warehouseRepository;
	
	
	@PostMapping("/add")
	public String add(@RequestBody WarehouseDto warehouseDto) {

		warehouseService.saveWarehouse(warehouseDto);

		return "New Warehouse is added";
	}

	
	@GetMapping("/getAll")
	public List<Warehouse> getAllWarehouses() {

		return warehouseService.getAllWarehouses();

	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<?>  findWarehouseById(@PathVariable("id") int id) {

		return new ResponseEntity<>(warehouseService.getWarehouseById(id),HttpStatus.OK) ;
	}

	@PutMapping("/update/{id}")
	public Warehouse updateWarehouse(@PathVariable int id, @RequestBody Warehouse warehouse) {

		return warehouseService.updateWarehouse(warehouse, id);

	}

	@DeleteMapping("/delete/{id}")
	public String deleteWarehouse(@PathVariable int id) {

		return warehouseService.deleteWarehouse(id);

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
			return new ResponseEntity<>(warehouseService.SearchWarehouse(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(warehouseService.IndexWarehouseAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(warehouseService.IndexWarehouseDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}	

}
