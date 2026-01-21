package com.SCM.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.configurationprocessor.json.JSONArray;
//
//import org.springframework.boot.configurationprocessor.json.JSONException;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//import org.springframework.boot.configurationprocessor.json.JSONArray;
//import org.springframework.boot.configurationprocessor.json.JSONException;


import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.ExportDto.ExportProduct;
import com.SCM.IndexDto.IndexProduct;
import com.SCM.IndexDto.ProductWithSigleImage;
import com.SCM.dto.ProductDto;
import com.SCM.dto.ProductImageDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.OpeningStock;
import com.SCM.model.Product;
import com.SCM.model.ProductImage;
import com.SCM.model.Warehouse;
import com.SCM.projection.ProductProjection;
import com.SCM.projection.ProductProjectionByCategory;
import com.SCM.projection.ProductProjectionByProductType;
import com.SCM.projection.ProductTypeByProduct;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.OpeningStockRepository;
import com.SCM.repository.ProductImageRepo;
import com.SCM.repository.ProductRepo;
import com.SCM.repository.WarehouseRepository;
import com.SCM.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OpeningStockRepository openingStockRepository;

	@Autowired
	private ProductImageRepo productImageRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private WarehouseRepository warehouseRepository;

	private final Path fileStorageLocation;

	@Value("${FILE_PATH}")
	private String FILE_PATH;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	

	@Autowired
	public ProductServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "/Images/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}

	@Override
	public Product getProductById(int id) {
		
		return productRepo.findById(id).orElse(null);
	}

	@Override
	public String deleteProduct(int id) {
		productRepo.deleteById(id);
		return "Company Removed !!" + id;
	}

	
	
	@Override
	public Product updateProduct(ProductDto productDto, MultipartFile[] files, int id)
			throws IllegalStateException, IOException {
		
//		Long uid = getUserId();
//		String uname=getUserName();
//		String role=getRolename();
		List<ProductImage> pimages = productImageRepo.findByProductId1(productDto.getId());

		Product existingProduct = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product id not found !!"));
		
//		existingProduct.setCreatebyname(uname);
//		existingProduct.setRole(role);
//		existingProduct.setCreatedby(id);
		
//		existingProduct.setUpdatedby(id);
//		existingProduct.setUpdatedbyname(uname);
//		existingProduct.setUpdatedrole(role);
		
		
//		existingProduct.setUpdateddate(LocalDate.now());
//		existingProduct.setUpdatedtime(LocalTime.now());
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setCapacity(productDto.getCapacity());
		existingProduct.setCgst(productDto.getCgst());
		existingProduct.setCategory(productDto.getCategory());
		existingProduct.setCgstLedger(productDto.getCgstLedger());
		existingProduct.setUomSecondary(productDto.getUomSecondary());
		existingProduct.setUomPrimary(productDto.getUomPrimary());
		existingProduct.setStandardQtyPerBox(productDto.getStandardQtyPerBox());
		existingProduct.setShortName(productDto.getShortName());
		existingProduct.setSgstLedger(productDto.getSgstLedger());
		existingProduct.setSgst(productDto.getSgst());
		existingProduct.setProductType(productDto.getProductType());
		existingProduct.setProductGroup(productDto.getProductGroup());
		existingProduct.setMrp(productDto.getMrp());
		existingProduct.setIgstLedger(productDto.getIgstLedger());
		existingProduct.setIgst(productDto.getIgst());
		existingProduct.setHsnCode(productDto.getHsnCode());
		existingProduct.setEanCode(productDto.getEanCode());
		existingProduct.setDlp(productDto.getDlp());
		existingProduct.setProductKind(productDto.getProductKind());
		existingProduct.setDiameter(productDto.getDiameter());
		existingProduct.setCostprice(productDto.getCostprice());
		existingProduct.setBrand(productDto.getBrand());
		existingProduct.setCostprice(productDto.getCostprice());
		existingProduct.setUom(productDto.getUom());

		Product updatingproduct = productRepo.save(existingProduct);

		for (ProductImage pimage : pimages) {

			int[] i = { 1 };
			Arrays.asList(files).stream().forEach(f -> {

				if (f != null) {

					if (pimage != null) {

						System.out.println(pimage.getId());
						ProductImage pi = productImageRepo.findById(pimage.getId()).get();

						System.out.println(pi + "+++++++ pi ");

						Path filetodeletepath = Paths.get(FILE_PATH + pi.getLocation());

						if (filetodeletepath != null) {

							try {
								Files.delete(filetodeletepath);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						System.out.println(filetodeletepath + "++++++ filepath");

						String filename = existingProduct.getId() + "_img_" + i[0]
								+ f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1);
						i[0]++;

						try {
							Files.copy(f.getInputStream(), this.fileStorageLocation.resolve(filename),
									StandardCopyOption.REPLACE_EXISTING);

						} catch (IOException e) {

							e.printStackTrace();
						}

						pi.setLocation("Images/" + f.getOriginalFilename());
						pi.setName(f.getOriginalFilename());
						pi.setProduct(updatingproduct);

						productImageRepo.save(pi);
					}

					// if there is no image it create one

					else {

						String filename = updatingproduct.getId() + "_img_" + i[0]
								+ f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1);

						try {
							Files.copy(f.getInputStream(), this.fileStorageLocation.resolve(filename),
									StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							e.printStackTrace();
						}

						ProductImage productImage = new ProductImage();
						productImage.setName(f.getOriginalFilename());
						productImage.setLocation("Images/" + f.getOriginalFilename());
						productImage.setProduct(existingProduct);

						productImageRepo.save(productImage);
					}
				}
			});
		}
		return updatingproduct;
	}

	
	
	@Override
	public Product updateProduct1(ProductDto productDto, MultipartFile[] files, int id) throws IllegalStateException, IOException, JSONException {

		List<ProductImage> pimages = productImageRepo.findByProductId1(productDto.getId());

		JSONArray jsonObject = new JSONArray(productDto.getProductStringImage());
		List<ProductImageDto> listProductIamgesDto = new ArrayList<>();
		
		for (int i = 0; i < jsonObject.length(); i++) {
			
			ObjectMapper objectMapper = new ObjectMapper();
			ProductImageDto productImages = objectMapper.readValue(jsonObject.get(i).toString(), ProductImageDto.class);
			listProductIamgesDto.add(productImages);
		}
		
		List<Integer> listDeleteProductImagesDto = new ArrayList<>();
		List<Integer> imagesId = new ArrayList<>();
	
		pimages.forEach(i -> {
			imagesId.add(i.getId());
		});
		
		List<Integer> listProductImagesDtoIds = new ArrayList<>();
		listProductIamgesDto.forEach(imgId -> {
			listProductImagesDtoIds.add(imgId.getId());
		});
		
		System.out.println("imagesId : " + imagesId);
		
		if (imagesId.size() > listProductIamgesDto.size()) {
			
			imagesId.forEach(img -> {

				System.out.println("img.getId() : " + img);
				
				if (!listProductImagesDtoIds.contains(img)) {
					
					System.out.println(img);

					listDeleteProductImagesDto.add(img);

				    ProductImage pdto =	productImageRepo.findById(img).get();
				    String location = pdto.getLocation();
					productImageRepo.deleteById(img);
					Path path = Paths.get(FILE_PATH + location) ;

					try {
						Files.delete(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

		Product existingProduct = productRepo.findById(id).orElseThrow(() -> new RuntimeException("product id not found !!"));

		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setCapacity(productDto.getCapacity());
		existingProduct.setCgst(productDto.getCgst());
		existingProduct.setCategory(productDto.getCategory());
		existingProduct.setCgstLedger(productDto.getCgstLedger());
		existingProduct.setUomSecondary(productDto.getUomSecondary());
		existingProduct.setUomPrimary(productDto.getUomPrimary());
		existingProduct.setStandardQtyPerBox(productDto.getStandardQtyPerBox());
		existingProduct.setShortName(productDto.getShortName());
		existingProduct.setSgstLedger(productDto.getSgstLedger());
		existingProduct.setSgst(productDto.getSgst());
		existingProduct.setProductType(productDto.getProductType());
		existingProduct.setProductGroup(productDto.getProductGroup());
		existingProduct.setMrp(productDto.getMrp());
		existingProduct.setIgstLedger(productDto.getIgstLedger());
		existingProduct.setIgst(productDto.getIgst());
		existingProduct.setHsnCode(productDto.getHsnCode());
		existingProduct.setEanCode(productDto.getEanCode());
		existingProduct.setDlp(productDto.getDlp());
		existingProduct.setProductKind(productDto.getProductKind());
		existingProduct.setDiameter(productDto.getDiameter());
		existingProduct.setCostprice(productDto.getCostprice());
		existingProduct.setBrand(productDto.getBrand());
		existingProduct.setUom(productDto.getUom());

		int[] i = { 1 };
		if (files != null) {

			Arrays.asList(files).stream().forEach(f -> {

				if (!f.isEmpty()) {
					
					String filename = existingProduct.getId() + "_image_" + i[0] + f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1);

					System.out.println(i[0]);

					System.out.println(f + "++++");
					i[0]++;
					try {

						Files.copy(f.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);

					} catch (IOException e) {

						e.printStackTrace();
					}

					ProductImage productImage = new ProductImage();
					productImage.setName(filename);
					productImage.setLocation("Images/" + filename);
					productImage.setProduct(existingProduct);
					productImageRepo.save(productImage);
				}
			});
		}
		return productRepo.save(existingProduct);
	}
	
//	public long getUserId() {
//		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
//			Long uid = userDetails.getId();
//
//			return uid;
//	}
//	public String getUserName() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
//		String username = userDetails.getUsername();
//		return username;
//	}
//
//	public String getRolename() {
//		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
//		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
//		String role=list.get(0);
//		return role;
//	}

	
	@Override
	public Product saveProduct1(ProductDto productDto, MultipartFile[] files) throws IllegalStateException, IOException {

		Product p = new Product();
		p.setCapacity(productDto.getCapacity());
		p.setCgst(productDto.getCgst());
		p.setCategory(productDto.getCategory());
		p.setCgstLedger(productDto.getCgstLedger());
		p.setUomSecondary(productDto.getUomSecondary());
		p.setUomPrimary(productDto.getUomPrimary());
		p.setStandardQtyPerBox(productDto.getStandardQtyPerBox());
		p.setShortName(productDto.getShortName());
		p.setSgstLedger(productDto.getSgstLedger());
		p.setSgst(productDto.getSgst());
		p.setProductType(productDto.getProductType());
		p.setProductName(productDto.getProductName());
		p.setProductGroup(productDto.getProductGroup());
		p.setMrp(productDto.getMrp());
		p.setIgstLedger(productDto.getIgstLedger());
		p.setIgst(productDto.getIgst());
		p.setHsnCode(productDto.getHsnCode());
		p.setEanCode(productDto.getEanCode());
		p.setDlp(productDto.getDlp());
		p.setDiameter(productDto.getDiameter());
		p.setBrand(productDto.getBrand());
		p.setProductKind(productDto.getProductKind());
		p.setCostprice(productDto.getCostprice());
		p.setUom(productDto.getUom());
		p.setCreateddate(LocalDate.now());
		p.setCreatedtime(LocalTime.now());

		Product pro = productRepo.save(p);

		// save opening stock.....

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();

		List<Warehouse> w = warehouseRepository.findAll();
		for (Warehouse warehouse : w) {

			OpeningStock openingStock = new OpeningStock();

			openingStock.setPid(pro.getId());
			openingStock.setQty(0);
			openingStock.setDate(dateFormat.format(date));
			openingStock.setProduct(pro);
			openingStock.setWid(warehouse.getId());

			openingStockRepository.save(openingStock);
		}

		int[] i = { 1 };

		Arrays.asList(files).stream().forEach(f -> {

//			String filename = pro.getId() + "_image_" + i[0] + "." + f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1);
			  String extension = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf(".") + 1);
			  String filename  = pro.getId() + "_image_" + i[0] + "." + extension;
			
			i[0]++;
			try {

				Files.copy(f.getInputStream(), this.fileStorageLocation.resolve(filename),StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException e) {

				e.printStackTrace();
			}

			ProductImage productImage = new ProductImage();
			productImage.setName(filename);
			productImage.setLocation("Images/" + filename);
			productImage.setProduct(pro);
			productImageRepo.save(productImage);

			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Long loggeduser = userDetailsImpl.getId();

			ActivityLog activityLog = new ActivityLog();
			activityLog.setCreatedate(new Date());
			activityLog.setCreatedtime(LocalTime.now());
			activityLog.setProductid((long) pro.getId());
			activityLog.setLoggeduser(loggeduser);
			activityLogRepo.save(activityLog);
		});

		return null;
	}

	@Override
	public List<OpeningStock> getAllProductandOpeningStock() {
		return openingStockRepository.fetchAllProductandOpeningStock();
	}

	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	@Override
	public String deleteOpeningStockandProduct(int id) {

		productRepo.deleteById(id);

//		productImageRepo.deleteById(id);
//		openingStockRepository.deleteById(id);

		return "delete Product and Opening stock succesfully !!";
	}

	@Override
	public List<ProductDto> getAllProductWithImages() {

		List<ProductDto> productDtos = new ArrayList<>();

		for (Product product : productRepo.findAll()) {

			ProductImage images = productImageRepo.findByProductId(product.getId());

			ProductDto dto = new ProductDto();
			dto.setId(product.getId());
			dto.setProductName(product.getProductName());
			dto.setShortName(product.getShortName());
			dto.setEanCode(product.getEanCode());
			dto.setStandardQtyPerBox(product.getStandardQtyPerBox());
			dto.setCategory(product.getCategory());
			dto.setUomPrimary(product.getUomPrimary());
			dto.setUomSecondary(product.getUomSecondary());
			dto.setMrp(product.getMrp());
			dto.setCapacity(product.getCapacity());
			dto.setDiameter(product.getDiameter());
			dto.setHsnCode(product.getHsnCode());
			dto.setBrand(product.getBrand());
			dto.setIgst(product.getIgst());
			dto.setCgst(product.getCgst());
			dto.setSgst(product.getSgst());
			dto.setIgstLedger(product.getIgstLedger());
			dto.setCgstLedger(product.getCgstLedger());
			dto.setSgstLedger(product.getSgstLedger());
			dto.setProductType(product.getProductType());
			dto.setProductGroup(product.getProductGroup());
			dto.setDlp(product.getDlp());
			dto.setImage(images);

			productDtos.add(dto);
		}
		return productDtos;
	}

	@Override
	public ProductDto getProductbyIdWithImages(int id) {

		Product product = productRepo.findById(id).get();
		ProductImage i = productImageRepo.findByProductId(product.getId());

		ProductDto dto = new ProductDto();
		dto.setId(product.getId());
		dto.setProductName(product.getProductName());
		dto.setShortName(product.getShortName());
		dto.setEanCode(product.getEanCode());
		dto.setStandardQtyPerBox(product.getStandardQtyPerBox());
		dto.setCategory(product.getCategory());
		dto.setUomPrimary(product.getUomPrimary());
		dto.setUomSecondary(product.getUomSecondary());
		dto.setMrp(product.getMrp());
		dto.setCapacity(product.getCapacity());
		dto.setDiameter(product.getDiameter());
		dto.setHsnCode(product.getHsnCode());
		dto.setBrand(product.getBrand());
		dto.setIgst(product.getIgst());
		dto.setCgst(product.getCgst());
		dto.setSgst(product.getSgst());
		dto.setIgstLedger(product.getIgstLedger());
		dto.setCgstLedger(product.getCgstLedger());
		dto.setSgstLedger(product.getSgstLedger());
		dto.setProductType(product.getProductType());
		dto.setProductGroup(product.getProductGroup());
		dto.setDlp(product.getDlp());
		dto.setImage(i);

		return dto;
	}

	@Override
	public List<Product> fetchAll(int page) {

		return productRepo.fetchProduct(page);
	}

	@Override
	public List<ProductDto> getAllProductandOpeningStockandImages() {

		List<ProductDto> productDtos = new ArrayList<>();

		for (Product product : productRepo.findAll()) {

			ProductImage image = productImageRepo.findByProductId(product.getId());
			List<OpeningStock> openingStock = openingStockRepository.findByProductId(product.getId());

			ProductDto dto = new ProductDto();
			dto.setId(product.getId());
			dto.setProductName(product.getProductName());
			dto.setShortName(product.getShortName());
			dto.setEanCode(product.getEanCode());
			dto.setStandardQtyPerBox(product.getStandardQtyPerBox());
			dto.setCategory(product.getCategory());
			dto.setUomPrimary(product.getUomPrimary());
			dto.setUomSecondary(product.getUomSecondary());
			dto.setMrp(product.getMrp());
			dto.setCapacity(product.getCapacity());
			dto.setDiameter(product.getDiameter());
			dto.setHsnCode(product.getHsnCode());
			dto.setBrand(product.getBrand());
			dto.setIgst(product.getIgst());
			dto.setCgst(product.getCgst());
			dto.setSgst(product.getSgst());
			dto.setIgstLedger(product.getIgstLedger());
			dto.setCgstLedger(product.getCgstLedger());
			dto.setSgstLedger(product.getSgstLedger());
			dto.setProductType(product.getProductType());
			dto.setProductKind(product.getProductKind());
			dto.setProductGroup(product.getProductGroup());
			dto.setDlp(product.getDlp());
			dto.setImage(image);
			dto.setOpeningStock((OpeningStock) openingStock);

			productDtos.add(dto);
		}
		return productDtos;
	}

	@Override
	public byte[] downloadImages(String filename) throws IOException {

		Optional<ProductImage> imgfile = productImageRepo.findByName(filename);

		String filepath = imgfile.get().getLocation();
		System.out.println(FILE_PATH + filepath);
		byte[] images = Files.readAllBytes(new File(filepath).toPath());
		return images;
	}

	@Override
	public Resource getImagePath() throws MalformedURLException {

		Path targetLocation = this.fileStorageLocation.toAbsolutePath().normalize();
		Resource resource = new UrlResource(targetLocation.toUri());
		System.out.println(targetLocation);
		System.out.println(resource);
		System.out.println(targetLocation.toUri());

		return resource;
	}

	@Override
	public String deleteProductImage(int id) {

		productImageRepo.deleteById(id);

		return "product image delete";
	}

	@Override
	public Map<String, Object> IndexProductAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexProduct> Listofproduct = productRepo.indexProduct(p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

	@Override
	public Map<String, Object> IndexProductDSC(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;

		List<IndexProduct> Listofproduct = productRepo.indexProduct(p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

	@Override
	public Map<String, Object> SearchProduct(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexProduct> Searchproducts = productRepo.SearchByProduct(search, p);

		long searchcount = Searchproducts.size();

		response.put("data", Searchproducts);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public List<ExportProduct> exportProduct() {

		return productRepo.ExportProduct();
	}

	@Override
	public List<Product> productwithwarehouseid(int woid) {

		return productRepo.fetchproductbywarehouseid(woid);
	}

	@Override
	public Product productById(int id) {

		Optional<Product> p = productRepo.findById(id);
		Product pr = p.get();

		return pr;
	}

	@Override
	public List<Product> AllProduct() {

		return productRepo.findAll();
	}
	
	

	@Override
	public Page<List<Product>> findWithPagination(String category,int offset, int pagesize) {
		// TODO Auto-generated method stub
		

//		Page<List<Product>> products=productRepo.productbycategory(category,(PageRequest.of(offset, pagesize)));
	  

	
		return null;
		
	}

	@Override
	public Map<String, Object> productByProductCategory(int pagno, int pagesize,String category) {
		Map<String, Object> response = new HashMap<>();

		
		Pageable p = PageRequest.of(pagno, pagesize);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;

		List<Product> ipo = productRepo.productbycategoryPagination(category, p);

		response.put("Index", ipo);
		response.put("CountPages", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexProductProjectionForCategory(String category, int pageno, int pagesize,
			String field) {
		
		 Map<String, Object> response = new HashMap<>();
	     Sort sorted;
	        
	        if (field.equals("desc")) {
	        	
	            sorted = Sort.by(Sort.Direction.DESC, "category"); // Adjust sorting based on the 'category' field
	        } else {
	            sorted = Sort.by(Sort.Direction.ASC, "category"); // Adjust sorting based on the 'category' field
	        }

	        long countpages = productRepo.count();

	        Pageable p = PageRequest.of(pageno, pagesize, sorted);
	        long totalPages = (countpages + pagesize - 1) / pagesize; // Correct calculation of total pages

	        List<ProductProjectionByCategory> proprojbycat = productRepo.getProductByCategoryVise(category, p);
	        
	        response.put("Index",proprojbycat); // Use getContent() to get the content of the page
	        response.put("Countpages", countpages);
	        response.put("pages", totalPages);
	        return response;
	}

	
	@Override
	public Map<String, Object> searchProductByCategoryVise(String search1, String search2, int pageno, int pagesize) {
		// TODO Auto-generated method stub
		Map<String, Object> response=new HashMap<>();
		
		Pageable p=PageRequest.of(pageno, pagesize);
	
		List<ProductProjectionByCategory> searchProductByCategoryVise = productRepo.searchProductByCategoryVise(search1, search2, p);
		int size = searchProductByCategoryVise.size();
		response.put("data", searchProductByCategoryVise);
		response.put("searchcount", size);
		
		return response;
//		return null;
	}

	@Override
	public Map<String, Object> IndexProductProjectionForCategoryAndProductTypeAsc(String productType,String category,int pageno, int pagesize, String field) {
		
		
		 Map<String, Object> response = new HashMap<>();
			Sort sort = Sort.by(Sort.Direction.ASC, field);
		      Pageable p = PageRequest.of(pageno, pagesize, sort);
	        long countpages = productRepo.count();

	  
	        long totalPages = (countpages + pagesize - 1) / pagesize; // Correct calculation of total pages

	        List<ProductProjectionByProductType> proprojbycat = productRepo.getProductByProductTypeVise(category,productType, p);
	        response.put("Index",proprojbycat); // Use getContent() to get the content of the page
	        response.put("Countpages", countpages);
	        response.put("Pages", totalPages);
	        return response;
	}
	
	@Override
	public Map<String, Object> IndexProductProjectionForCategoryAndProductTypeDesc(String productType,String category,int pageno, int pagesize, String field) {
		
		 Map<String, Object> response = new HashMap<>();
			Sort sort = Sort.by(Sort.Direction.DESC, field);
		      Pageable p = PageRequest.of(pageno, pagesize, sort);
	        long countpages = productRepo.count();

	  
	        long totalPages = (countpages + pagesize - 1) / pagesize; // Correct calculation of total pages

	        List<ProductProjectionByProductType> proprojbycat = productRepo.getProductByProductTypeVise(category,productType, p);
	        response.put("Index",proprojbycat); // Use getContent() to get the content of the page
	        response.put("Countpages", countpages);
	        response.put("Pages", totalPages);
	        return response;
	}

	@Override
	public Map<String, Object> searchProductByProductTypeVise(String productType,String category,String search1, int pageno, int pagesize) {
	Map<String, Object> response=new HashMap<>();
		
		Pageable p=PageRequest.of(pageno, pagesize);
	
		List<ProductProjectionByProductType> searchProductByCategoryVise = productRepo.getProductByProductTypeViseSearch(category, productType, p, search1);
		int size = searchProductByCategoryVise.size();

		response.put("searchproducts", searchProductByCategoryVise);
		response.put("searchcount", size);
		
		return response;
	}

	@Override
	public Map<String, Object> searchProductByProductNameVise(String search, int pageno, int pagesize) {
	
	Map<String, Object> response=new HashMap<>();
		
		Pageable p=PageRequest.of(pageno, pagesize);
	
		List<ProductProjectionByProductType> searchProductByCategoryVise = productRepo.getProductByProductNameViseSearch(search, p);
		int size = searchProductByCategoryVise.size();

		response.put("searchproducts", searchProductByCategoryVise);
		response.put("searchcount", size);
		
		return response;
		
		
	}

	@Override
	public List<Product> findByBrand(int id) {
		// TODO Auto-generated method stub
		List<Product> products = productRepo.findByBrand(id);
		return products;
	}



	@Override
	public List<ProductTypeByProduct> productByProductType(String product_type) {
		
		
		return productRepo.productByProductType(product_type);
	}



	@Override
	public Map<String, Object> IndexFinishedProductAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexProduct> Listofproduct = productRepo.indexProductForFinishedProduct(p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}



	@Override
	public Map<String, Object> IndexFinishedProductDSC(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexProduct> Listofproduct = productRepo.indexProductForFinishedProduct(p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}



	@Override
	public Map<String, Object> SearchFinishedProduct(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexProduct> Searchproducts = productRepo.SearchByProductForFinishedProduct(search, p);

		long searchcount = Searchproducts.size();

		response.put("data", Searchproducts);
		response.put("SearchCount", searchcount);

		return response;
	}



	@Override
	public Map<String, Object> IndexProductWithSingleImageAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<ProductWithSigleImage> productWithSigleImages = productRepo.productWithSigleImages(p);

		response.put("Index", productWithSigleImages);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}



	@Override
	public Map<String, Object> IndexProductWithSingleImageDSC(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = productRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<ProductWithSigleImage> productWithSigleImages = productRepo.productWithSigleImages(p);

		response.put("Index", productWithSigleImages);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}



	@Override
	public Map<String, Object> SearchProductWithSingleImage(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<ProductWithSigleImage> searchproductWithSigleImages = productRepo.SearchproductWithSigleImages(p,search);

		long searchcount = searchproductWithSigleImages.size();

		response.put("data", searchproductWithSigleImages);
		response.put("SearchCount", searchcount);

		return response;
	}
	
	
	


}
