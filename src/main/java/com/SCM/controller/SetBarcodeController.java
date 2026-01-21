package com.SCM.controller;

import java.io.IOException;

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

import com.SCM.dto.SetBarcodeDto;
import com.SCM.model.SetBarcode;
import com.SCM.service.SetBarcodeService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping("/api/setbarcode")
public class SetBarcodeController {

	@Autowired
	private SetBarcodeService barcodeService;

	@PostMapping("/save")
	public ResponseEntity<?> saveSetBarcode(@RequestBody SetBarcodeDto barcode) throws WriterException, IOException {
		return new ResponseEntity<>(barcodeService.saveSetBarcode(barcode), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBracode(@RequestBody SetBarcodeDto barcode, @PathVariable long id)
			throws WriterException, IOException {
		return new ResponseEntity<>(barcodeService.updateSetBarcode(barcode, id), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBarcode(@PathVariable long id) {
		return new ResponseEntity<>(barcodeService.deleteSetBarcode(id), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable long id) {
		return new ResponseEntity<>(barcodeService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(barcodeService.getAllSetBarcode(), HttpStatus.OK);
	}

	@GetMapping("/setbarcodebypb/{pid}/{bid}")
	public ResponseEntity<?> getByProductAndBrand(@PathVariable long pid, @PathVariable long bid) {
		
		SetBarcode setbarcode = barcodeService.getByProductAndBarcode(pid, bid);
		
		return new ResponseEntity<>(setbarcode, HttpStatus.OK);
	}

	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAllDistributor(@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("sortby") String sortby,
			@PathVariable("field") String field, @PathVariable("search") String search) {
		if (!search.equals(" ")) {

			return new ResponseEntity<>(barcodeService.SearchSetBarcode(pageno, pagesize, search), HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(barcodeService.IndexSetBarcodeAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(barcodeService.IndexSetBarcodeDesc(pageno, pagesize, field), HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/product/{bid}")
	public ResponseEntity<?> FetchSetBarCodeProduct(@PathVariable("bid")int bid) {
		return new ResponseEntity<>(barcodeService.fetchSetbarcodeProduct(bid), HttpStatus.OK);
	}
}
