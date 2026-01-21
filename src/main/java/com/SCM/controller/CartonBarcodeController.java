package com.SCM.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.hql.internal.ast.tree.ResolvableNode;
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

import com.SCM.dto.CartonBarcodeDto;
import com.SCM.model.CartonBarcode;
import com.SCM.service.CartonBarcodeService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping("/api/cartonbarcode")
@CrossOrigin(origins = "*")

public class CartonBarcodeController {

	@Autowired
	private CartonBarcodeService cartonBarcodeService;

	@PostMapping("/save")
	public ResponseEntity<?> saveCartonBarcode(@RequestBody CartonBarcodeDto cartonBarcodeDto)
			throws WriterException, IOException {
		CartonBarcodeDto save = cartonBarcodeService.save(cartonBarcodeDto);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCartonBarcode(@RequestBody CartonBarcodeDto barcodeDto, @PathVariable long id)
			throws WriterException, IOException {
		String updateCarton = cartonBarcodeService.updateCarton(barcodeDto, id);
		return new ResponseEntity<>(updateCarton, HttpStatus.OK);
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAllCartonBarcode() {
		List<CartonBarcodeDto> all = cartonBarcodeService.getAll();
		return new ResponseEntity<>(all, HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getCartonBarcodeById(@PathVariable long id) {
		Optional<CartonBarcodeDto> all = cartonBarcodeService.getById(id);
		return new ResponseEntity<>(all, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCartonBarcodeById(@PathVariable long id) {
		return new ResponseEntity<>(cartonBarcodeService.deleteCarton(id), HttpStatus.OK);
	}

	@GetMapping("/cartonbarcodebypb/{pid}/{bid}")
	public ResponseEntity<?> getByProductAndBrand(@PathVariable long pid, @PathVariable long bid) {
		Optional<CartonBarcode> byProductAndBrand = cartonBarcodeService.getByProductAndBrand(pid, bid);
		return new ResponseEntity<>(byProductAndBrand, HttpStatus.OK);
	}

	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAllDistributor(@PathVariable("pageno") int pageno,
		                                         @PathVariable("pagesize") int pagesize,
		                                         @PathVariable("sortby") String sortby,
			                                     @PathVariable("field") String field,
			                                     @PathVariable("search") String search) {
		if (!search.equals(" ")) {

			return new ResponseEntity<>(cartonBarcodeService.SearchCartonBarcode(pageno, pagesize, search),HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(cartonBarcodeService.IndexCartonBarcodeAsc(pageno, pagesize, field),HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(cartonBarcodeService.IndexCartonBarcodeDesc(pageno, pagesize, field),HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

}
