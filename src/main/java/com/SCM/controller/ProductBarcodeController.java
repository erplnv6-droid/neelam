package com.SCM.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.ProductBarcodeDto;
import com.SCM.model.ProductBarcode;
import com.SCM.service.ProductBarcodeService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping("/api/productbarcode")
public class ProductBarcodeController {

	@Autowired
	private ProductBarcodeService productBarcodeService;

	@GetMapping("/barcode/{id}")
	public ResponseEntity<byte[]> getBarCodeImage(@PathVariable long id) throws IOException {

		byte[] barCodeById = productBarcodeService.getBarCodeById(id);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(barCodeById);
	}

	@GetMapping("/barcode/name/{name}")
	public ResponseEntity<byte[]> getBarCodeImageByname(@PathVariable String name) throws IOException {

		byte[] barCodeById = productBarcodeService.getBarCodeByName(name);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(barCodeById);
	}

	@PostMapping("/save")
	public ResponseEntity<ProductBarcode> saveProductBarcode1(@RequestBody ProductBarcodeDto productBarcodeDto)
			throws WriterException, IOException {

		return new ResponseEntity<ProductBarcode>(productBarcodeService.generateQrCode(productBarcodeDto),
				HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllProductBarcode() {

		List<ProductBarcodeDto> allProductBarcode = productBarcodeService.getAllProductBarcode();

		return new ResponseEntity<>(allProductBarcode, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductBarCode(@PathVariable long id) throws IOException {

		Optional<ProductBarcodeDto> productBarcodeById = productBarcodeService.getProductBarcodeById(id);

		return new ResponseEntity<>(productBarcodeById, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductBarcodeDto barcodeDto)
			throws IOException, WriterException {

		String updateProductBarcode = productBarcodeService.updateProductBarcode(barcodeDto, id);

		return new ResponseEntity<>(updateProductBarcode, HttpStatus.OK);
	}

	@GetMapping("/barcodebypb/{pid}/{bid}")
	public ResponseEntity<?> getByProductAndBrand(@PathVariable long pid, @PathVariable long bid) {
		ProductBarcode byProductAndBrand = productBarcodeService.getByProductAndBrand(pid, bid);
		return new ResponseEntity<>(byProductAndBrand, HttpStatus.OK);
	}

	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexProductBarcode(@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("sortby") String sortby,
			@PathVariable("field") String field, @PathVariable("search") String search) {

		if (!search.equals(" ")) {

			return new ResponseEntity<>(productBarcodeService.SearchAllProductBarcode(pageno, pagesize, search),
					HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(productBarcodeService.IndexAllProductBarcodeAsc(pageno, pagesize, field),
					HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(productBarcodeService.IndexAllProductBarcodeDesc(pageno, pagesize, field),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
	
	@GetMapping("/product1/{bid}")
	public ResponseEntity<?> FetchSetBarCodeProduct(@PathVariable("bid") int bid) {
		return new ResponseEntity<>(productBarcodeService.fetchProductBarcodeProduct(bid), HttpStatus.OK);
	}

}
