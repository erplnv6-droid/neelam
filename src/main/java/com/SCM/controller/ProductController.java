package com.SCM.controller;

import com.SCM.ExportDto.ExportProduct;
import com.SCM.IndexDto.IndexProductCategory;
import com.SCM.dto.ProductDto;

import com.SCM.model.GstMaster;
import com.SCM.model.OpeningStock;
import com.SCM.model.Product;
import com.SCM.model.ProductImage;
import com.SCM.projection.ProductProjectionByProductType;
import com.SCM.projection.ProductTypeByProduct;
import com.SCM.repository.GstMasterRepository;
import com.SCM.repository.ProductImageRepo;
import com.SCM.repository.ProductRepo;
import com.SCM.service.ProductImageService;
import com.SCM.service.ProductService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductImageRepo productImageRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private GstMasterRepository gstMasterRepository;

	@Autowired
	private ProductImageService imageService;

	@GetMapping("/productsByBrand/{id}")
	public ResponseEntity<?> getAllByBrand(@PathVariable int id) {

		return new ResponseEntity<>(productService.findByBrand(id), HttpStatus.OK);

	}

	@GetMapping("/getAllos")
	public List<OpeningStock> getAll() {

		return productService.getAllProductandOpeningStock();
	}

	@GetMapping("/i/{id}")
	public List<ProductImage> getAllsada(@PathVariable("id") int id) {

		List<ProductImage> po = productImageRepo.findByProductId1(id);

		return po;
	}

	@GetMapping("/pro")
	public List<Product> getAllProduct() {

		return productRepo.findAll();
	}

	@GetMapping("/getAllos1")
	public List<ProductDto> getAll1() {

		return productService.getAllProductandOpeningStockandImages();
	}

	@GetMapping("/getpath")
	public Resource getimg() throws MalformedURLException {

		return productService.getImagePath();
	}

	@GetMapping("/getpath/{location}")
	public byte[] getPathWithimg(@PathVariable("location") String location) throws IOException {

		byte[] imgdata = productService.downloadImages(location);

		return imgdata;
	}

	@PostMapping("/create")
	public ResponseEntity<?> create1(@ModelAttribute ProductDto productDto, MultipartFile[] files)
			throws IllegalStateException, IOException {

		return new ResponseEntity<>(productService.saveProduct1(productDto, files), HttpStatus.CREATED);
	}

	@PostMapping("/image")
	public ResponseEntity<?> image(@RequestParam("files") MultipartFile files)
			throws IllegalStateException, IOException {

		return new ResponseEntity<>(imageService.save1(files), HttpStatus.CREATED);
	}

	@GetMapping("/image/{filename}")
	public ResponseEntity<?> downloadImagesInFiles(@PathVariable("filename") String filename) throws IOException {

		byte[] imgdata = imageService.downloadImages(filename);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imgdata);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteOsandProduct(@PathVariable int id) {

		productService.deleteOpeningStockandProduct(id);

		return new ResponseEntity<String>("Your Product is SuccessFully Deleted!!", HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public Product updateUser(@PathVariable("id") int id, @ModelAttribute ProductDto productDto, MultipartFile[] files)
			throws IllegalStateException, IOException, JSONException {

		return productService.updateProduct1(productDto, files, id);

	}

	@GetMapping("/getIgstById/{id}")
	public List<GstMaster> GetIGSTById(@PathVariable int id) {

		return gstMasterRepository.findByType(id);
	}

	@GetMapping("/getIgstByIdonedata/{id}")
	public Optional<GstMaster> GetIGSTByIdOneData(@PathVariable int id) {

		return gstMasterRepository.findById(id);
	}

	@GetMapping("/getcgstByIdonedata/{id}/{ids}")
	public Optional<GstMaster> GetCGSTByIdOneData(@PathVariable float id, @PathVariable int ids) {

		return gstMasterRepository.findByPerAndType(id, ids);
	}

	@DeleteMapping("/imagedelete/{id}")
	public ResponseEntity<String> deleteProductImage(@PathVariable int id) {

		productService.deleteProductImage(id);
		return new ResponseEntity<String>("Your product image id is SuccessFully Deleted!!", HttpStatus.OK);
	}

	// ---------------- Product Index

	@GetMapping("/page/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> IndexProduct(@PathVariable("pageno") int pageno, 
			                              @PathVariable("pagesize") int pagesize,
			                              @PathVariable("sort") String sort,
			                              @PathVariable("field") String field,
			                              @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			
			   String cleanedSearch = search.replaceAll("[^a-zA-Z0-9]", "");
			return new ResponseEntity<>(productService.SearchProduct(pageno, pagesize, cleanedSearch), HttpStatus.OK);
			
		} else if ("asc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexProductAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexProductDSC(pageno, pagesize, field), HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/export")
	public ResponseEntity<?> exportProduct() {
		List<ExportProduct> dto = productService.exportProduct();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/getproduct/{woid}")
	public List<Product> getproductByWarehouseId(@PathVariable("woid") int woid) {

		return productService.productwithwarehouseid(woid);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Product> pr(@PathVariable("id") Integer id) {
		try {

			Product product = productService.getProductById(id);

			return new ResponseEntity<>(product, HttpStatus.OK);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getById1/{pid}")
	public ResponseEntity<?> NewPrById(@PathVariable("pid") Integer pid) {
		try {

			ProductProjectionByProductType product = productRepo.productgetById(pid);

			return new ResponseEntity<>(product, HttpStatus.OK);

		} catch (NoSuchElementException e) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll")
	public List<Product> AllProduct() {

		return productService.AllProduct();
	}

	@GetMapping("/category")
	public List<IndexProductCategory> AllProductCategory() {

		return productRepo.category();
	}
//	  		
//	  @GetMapping("/category/{category}")
//		public List<Product> AllProductCategoryByCategory(@PathVariable("category")String category) {
//			
//			return productRepo.productbycategory(category);
//		}

	@GetMapping("/category/{pageno}/{pagesize}/{category}")
	public ResponseEntity<?> AllProductCategoryByCategoryPagination(

			@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			@PathVariable("category") String category) {

		return new ResponseEntity<>(productService.productByProductCategory(pageno, pagesize, category), HttpStatus.OK);
	}

	@GetMapping("/page/category/{category}/{pageno}/{pagesize}/{field}/{search2}")
	public ResponseEntity<?> productProjectionForCategory(@PathVariable("category") String category,
			@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			@PathVariable("field") String field, @PathVariable("search2") String search2

	) {
		if (search2.equals(" ")) {
			return new ResponseEntity<>(
					productService.IndexProductProjectionForCategory(category, pageno, pagesize, field), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(productService.searchProductByCategoryVise(category, search2, pageno, pagesize),
					HttpStatus.OK);
		}
	}

	@GetMapping("/page/{pageno}/{pagesize}/{search1}/{search2}")
	public ResponseEntity<?> productProjectionForCategoryBySearch(@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("search1") String search1,
			@PathVariable("search2") String search2) {
		return new ResponseEntity<>(productService.searchProductByCategoryVise(search1, search2, pageno, pagesize),
				HttpStatus.OK);

	}

//		@GetMapping("/page/category/{category}/{product_type}/{pageno}/{pagesize}")
//		public ResponseEntity<?> productProjectionForCategoryProduct(
//											  @PathVariable("category") String category,
//											  @PathVariable("product_type") String product_type,
//											  @PathVariable("pageno")int pageno,
//	                                          @PathVariable("pagesize") int pagesize
//	                                        
//	                                          
//	                                          ) {
//			
//			Pageable p=PageRequest.of(pageno, pagesize);
//			
//			List<ProductProjectionByProductType> productByProductTypeVise = productRepo.getProductByProductTypeVise(category, product_type, p);
//
//				return new ResponseEntity<>(productByProductTypeVise,HttpStatus.OK);
//			}
//		}

	@GetMapping("/page/category/{category}/{product_type}/{pageno}/{pagesize}/{sort}/{field}")
	public ResponseEntity<?> productProjectionForCategoryProduct(@PathVariable("category") String category,
			@PathVariable("product_type") String product_type, @PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("sort") String sort,
			@PathVariable("field") String field

	) {

		if ("asc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexProductProjectionForCategoryAndProductTypeAsc(product_type,
					category, pageno, pagesize, field), HttpStatus.OK);
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexProductProjectionForCategoryAndProductTypeDesc(product_type,
					category, pageno, pagesize, field), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/page/search/{category}/{product_type}/{pageno}/{pagesize}/{search1}")
	public ResponseEntity<?> productProjectionForProductTypeBySearch(@PathVariable("category") String category,
			@PathVariable("product_type") String product_type, @PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("search1") String search1

	) {
		return new ResponseEntity<>(
				productService.searchProductByProductTypeVise(product_type, category, search1, pageno, pagesize),
				HttpStatus.OK);

	}

	@GetMapping("/page/productname/{pageno}/{pagesize}/{search}")
	public ResponseEntity<?> productProjectionForProductNameBySearch(

			@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			@PathVariable("search") String search

	) {
		return new ResponseEntity<>(productService.searchProductByProductNameVise(search, pageno, pagesize),
				HttpStatus.OK);

	}

//		@GetMapping("/page/chittu/{category}/{product_type}/{pageno}/{pagesize}/{search1}")
//		public ResponseEntity<?> productProjectionForCategoryProduct(
//											  @PathVariable("category") String category,
//											  @PathVariable("product_type") String product_type,
//											  @PathVariable("pageno")int pageno,
//	                                          @PathVariable("pagesize") int pagesize,
//	                                        @PathVariable("search1") String search1
//	                                          
//	                                          ) {
//			
//			Pageable p=PageRequest.of(pageno, pagesize);
//			System.out.println(category);
//			List<ProductProjectionByProductType> productByProductTypeVise = productRepo.getProductByProductTypeViseSearch(category, product_type,p,search1);
//
//				return new ResponseEntity<>(productByProductTypeVise,HttpStatus.OK);
//			}

	@GetMapping("/producttype/{product_type}")
	public List<ProductTypeByProduct> getProductByType(@PathVariable("product_type") String product_type) {
		return productService.productByProductType(product_type);
	}

	
	
	
	@GetMapping("/page1/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> IndexForFinishedProduct(@PathVariable("pageno") int pageno, 
			                                         @PathVariable("pagesize") int pagesize,
			                                         @PathVariable("sort") String sort,
			                                         @PathVariable("field") String field,
			                                         @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(productService.SearchFinishedProduct(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexFinishedProductAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexFinishedProductDSC(pageno, pagesize, field), HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
//	------------------  product with single images
	
	@GetMapping("/singleimage/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> IndexSingleProductwithSingleImage(@PathVariable("pageno") int pageno, 
			                                                   @PathVariable("pagesize") int pagesize,
			                                                   @PathVariable("sort") String sort,
			                                                   @PathVariable("field") String field,
			                                                   @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(productService.SearchProductWithSingleImage(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexProductWithSingleImageAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(productService.IndexProductWithSingleImageDSC(pageno, pagesize, field), HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
}
