package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexProductBarcode;
import com.SCM.IndexDto.SetBarcodePrintIndex;
import com.SCM.dto.ProductBarcodeDto;
import com.SCM.mapper.ProductBarcodeMapper;
import com.SCM.model.Brand;
import com.SCM.model.Product;
import com.SCM.model.ProductBarcode;
import com.SCM.model.ProductBarcodeActivityLog;
import com.SCM.repository.BrandRepo;
import com.SCM.repository.ProductBarcodeActivityLogRepo;
import com.SCM.repository.ProductBarcodeRepo;
import com.SCM.repository.ProductRepo;
import com.SCM.service.ProductBarcodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

@Service
public class ProductBarcodeServiceImpl implements ProductBarcodeService {

	@Autowired
	private ProductBarcodeRepo barcodeRepo;

	@Autowired
	private ProductBarcodeMapper barcodeMapper;

	@Autowired
	private BrandRepo brandRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductBarcodeActivityLogRepo activityLogRepo;
	
	

	@Value("${app.file.upload-productbarcodeeancode}")
	private String FILE_PATH;

	private final Path fileStorageLocation;

	public ProductBarcodeServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-productbarcodeeancode", "/Images/"))
				.toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();
		return uid;
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	@Override
	public ProductBarcodeDto saveProductBarcode(ProductBarcodeDto dto) throws WriterException, IOException {
		return null;
	}

	@Override
	public List<ProductBarcodeDto> getAllProductBarcode() {

		List<ProductBarcode> barcodes = barcodeRepo.findAll();
		List<ProductBarcodeDto> collect = barcodes.stream().map((br) -> barcodeMapper.toDto(br))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Optional<ProductBarcodeDto> getProductBarcodeById(long id) {
		Optional<ProductBarcode> getProductById = barcodeRepo.findById(id);
		Optional<ProductBarcodeDto> product = getProductById.map((br) -> barcodeMapper.toDto(br));
		return product;
	}

	@Override
	public String updateProductBarcode(ProductBarcodeDto dto, long id) throws IOException, WriterException {
		if (barcodeRepo.existsById(id)) {

//			update
			Product productt = dto.getProduct();
			Brand brand = dto.getBrand();
			Long id2 = brand.getId();
			Brand brand1 = brandRepo.findById(id2).get();
			Long bid = brand1.getId();
			int id1 = productt.getId();
			Product product3 = productRepo.findById(id1).get();
			int p1id = product3.getId();
			System.out.println(bid + " brandddddddddddddddddddddddddddddd " + p1id);

//			if ( barcodeRepo.barcodeByBrandAndProductid(bid, p1id).isPresent()) {
//				throw new RuntimeException(product3.getProductName()+" and brand "+brand1.getBrandName() + " is already present ");
//			}

//			
			// suraj

			ProductBarcode p = barcodeRepo.findById(id).get();
			String filepath = p.getBarcodename();
			System.out.println(filepath + " this is file path ");

			if (filepath != null) {
				Path path = Paths.get(FILE_PATH + filepath);
				System.out.println(path + " this is file path ");

				Files.delete(path);
			}
			ProductBarcode entity = barcodeMapper.toEntity(dto);

			Product product2 = entity.getProduct();

			int pid = product2.getId();

			Product product = productRepo.findById(pid).get();

			String barcodePath = FILE_PATH;
			String name = product.getProductName() + product.getId();
			String barcodeName1 = barcodePath + name;
			String barcodeName = "BarcodeImages/ProductBarcode/" + name;

			String ean;
			ean = dto.getEancode();
			entity.setEancode(ean);
			if (ean == null) {
				ean = product.getEanCode();
			}
			ProductBarcode p2 = p;

			p.setEancode(ean);

			p.setBarcode(barcodeName);
			p.setBarcodename(name);

			p.setActualqty(dto.getActualqty());
//			p.setBrand(dto.getBrand());
			p.setBrandname(dto.getBrandname());
			p.setCapacity(dto.getCapacity());
			p.setDiameter(dto.getDiameter());
			p.setMrp(dto.getMrp());
			p.setOurprice(dto.getOurprice());
			p.setPackedOn(dto.getPackedOn());
			p.setPreviewlabel(dto.getPreviewlabel());
			p.setPrintingqty(dto.getPrintingqty());
//			p.setProductname(dto.getProductname());
			p.setProductname1(dto.getProductname1());
			p.setProductname2(dto.getProductname2());
			p.setQty(dto.getQty());
			p.setSize(dto.getSize());
			barcodeRepo.save(p2);

			Code128Writer writer = new Code128Writer();
			BitMatrix matrix = writer.encode(ean, BarcodeFormat.CODE_128, 500, 300);
			Path path = Paths.get(barcodeName1);
			MatrixToImageWriter.writeToPath(matrix, "PNG", path);

			return "product barcode updated succesfully " + p2.getId();

		}
		return "no product barcode availabe with id " + id;
	}

	@Override
	public String deleteProductBarcode(long id) {

		if (barcodeRepo.existsById(id)) {

			barcodeRepo.deleteById(id);
			return "succesfully deleted the productbarcode with id " + id;

		}
		return "no product barcode availabe with id " + id;
	}

	
	
	public ProductBarcode generateQrCode(ProductBarcodeDto dto) throws WriterException, IOException {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Product productt = dto.getProduct();
		Brand brand = dto.getBrand();
		Long id2 = brand.getId();
		
		Brand brand1 = brandRepo.findById(id2).get();
		Long bid = brand1.getId();

		int id = productt.getId();
		Product product3 = productRepo.findById(id).get();
		int p1id = product3.getId();

		if (barcodeRepo.barcodeByBrandAndProductid(bid, p1id).isPresent()) {

			throw new RuntimeException(product3.getProductName() + "and brand" + brand1.getBrandName() + "is already present ");
		}

		try {

			ProductBarcode entity = barcodeMapper.toEntity(dto);
			String ean;
//			Product product4 = entity.getProduct();
//			Product prod = productRepo.findById(product4.getId()).get();
//			System.out.println(dto.getEancode() + "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");

			ean = dto.getEancode();

			entity.setEancode(ean);
			Product product2 = entity.getProduct();
			int pid = product2.getId();
//			String productName = product2.getProductName();
//			int id1 = (int) pid;

			Product product = productRepo.findById(pid).get();
			if (ean == null) {
				ean = product.getEanCode();
			}

			entity.setEancode(ean);
			entity.setProductname(product.getProductName());

			String barcodePath = FILE_PATH;
			String name = product.getProductName() + product.getId();
			String barcodeName1 = barcodePath + name;
			String barcodeName = "BarcodeImages/ProductBarcode/" + name;
			entity.setCreatebyname(uname);
			entity.setCreatedby(uid);
			entity.setRole(role);
			entity.setCreateddate(LocalDate.now());
			entity.setCreatedtime(LocalTime.now());
			entity.setBarcode(barcodeName);
			entity.setBarcodename(name);
			ProductBarcode save = barcodeRepo.save(entity);

			Code128Writer writer = new Code128Writer();
			BitMatrix matrix = writer.encode(ean,BarcodeFormat.CODE_128,500,300);
			Path path = Paths.get(barcodeName1);
			MatrixToImageWriter.writeToPath(matrix, "PNG", path);

			return save;

		} catch (Exception e) {

			System.out.println("Error while creating barcode" + e);
			ProductBarcode barcode = new ProductBarcode();
			barcode.setBarcode(" error while creating the barcode");
			return barcode;
		}
	}

	@Override
	public byte[] getBarCodeById(long id) throws IOException {

		Optional<ProductBarcode> barcode = barcodeRepo.findById(id);
		String barcodepath = barcode.get().getBarcode();
		byte[] readAllBytes = Files.readAllBytes(new java.io.File(barcodepath).toPath());

		return readAllBytes;
	}

	@Override
	public byte[] getBarCodeByName(String barcodename) throws IOException {

		Optional<ProductBarcode> barcode = barcodeRepo.findByBarcodename(barcodename);
		String barcodepath = barcode.get().getBarcode();
		byte[] readAllBytes = Files.readAllBytes(new java.io.File(barcodepath).toPath());
		return readAllBytes;
	}

	@Override
	public Map<String, Object> IndexAllProductBarcodeAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);

		Pageable p = PageRequest.of(pageno, pagesize, sort);
		long countpages = barcodeRepo.indexProductBarcode().size();
		long pages = countpages / pagesize;
		List<IndexProductBarcode> barcode = barcodeRepo.IndexProductBarcode(p);

		response.put("Index", barcode);
		response.put("CountPages", countpages);
		response.put("Pages", pages);
		return response;
	}

	@Override
	public Map<String, Object> IndexAllProductBarcodeDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = barcodeRepo.indexProductBarcode().size();
		long pages = countpages / pagesize;
		List<IndexProductBarcode> barcode = barcodeRepo.IndexProductBarcode(p);

		response.put("Index", barcode);
		response.put("CountPages", countpages);
		response.put("Pages", pages);
		return response;
	}

	@Override
	public Map<String, Object> SearchAllProductBarcode(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = barcodeRepo.indexProductBarcode().size();
		long pages = countpages / pagesize;
		List<IndexProductBarcode> barcode = barcodeRepo.IndexProductBarcode(search, p);
		long searchcount = barcode.size();

		response.put("data", barcode);
		response.put("SearchCount", searchcount);
		return response;
	}
    
	
// --------------------------  print product barcode ---------------------------------------------------------------
	
	@Override
	public ProductBarcode getByProductAndBrand(Long productid, Long brandid) {

		long p = productid;
		int pid = (int) p;

		Product product = productRepo.findById(pid).get();
		Brand brand = brandRepo.findById(brandid).get();

		ProductBarcode barcode = barcodeRepo.findByProductAndBrand(product, brand).get();
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetailsImpl.getUsername();
		
		ProductBarcodeActivityLog pbal = new ProductBarcodeActivityLog();
		
		if(pbal.getUnqno() == null)
		{
			ArrayList<Integer> in = new ArrayList<Integer>();
			int i;
			for (i = 1; i < 10000000; i++) {
				in.add(i);

			}
			Collections.shuffle(in);
			for (i = 0; i < 1; i++) {
				System.out.println(in.get(i));
			}

			String uniq = "UNQ- ";
			pbal.setCreateddate(LocalDate.now());
			pbal.setCreatedtime(LocalTime.now());
			pbal.setLoggeduser(username);
			pbal.setProductbarcodeid(barcode.getId());
			pbal.setUnqno(uniq + in.get(i));

			activityLogRepo.save(pbal);
		}

		return barcode;
	}

	@Override
	public List<SetBarcodePrintIndex> fetchProductBarcodeProduct(int bid) {
		List<SetBarcodePrintIndex> fetchSetBarCodeProduct = barcodeRepo.FetchSetBarCodeProduct(bid);
		return fetchSetBarCodeProduct;
	}

}
