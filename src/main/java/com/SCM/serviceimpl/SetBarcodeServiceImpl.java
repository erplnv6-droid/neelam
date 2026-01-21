package com.SCM.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
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
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.GstModel.SetBarcodeActivityLog;
import com.SCM.IndexDto.IndexSetBarcode;
import com.SCM.IndexDto.SetBarcodePrintIndex;
import com.SCM.dto.SetBarcodeDto;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Brand;
import com.SCM.model.Product;
import com.SCM.model.SetBarcode;
import com.SCM.repository.BrandRepo;
import com.SCM.repository.ProductRepo;
import com.SCM.repository.SetBarcodeActivityLogRepo;
import com.SCM.repository.SetBarcodeRepository;
import com.SCM.service.SetBarcodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.experimental.var;
@Service
public class SetBarcodeServiceImpl implements SetBarcodeService{

	@Autowired
	public SetBarcodeRepository barcodeRepository;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private BrandRepo brandRepo;
	
	@Autowired
	private SetBarcodeActivityLogRepo activityLogRepo;
	
	
	@Value("${app.file.upload-setbarcodeeancode}")
	private String FILE_PATH1;
	
	@Value("${app.file.upload-setbarcodeqty}")
	private String FILE_PATH2;
	
	
	private final Path fileStorageLocation1;
	private final Path fileStorageLocation2;

	@Autowired
	private GetCurrentUserRoleName currentUserRoleName;
	
	@Autowired
	public  SetBarcodeServiceImpl (Environment env) throws IOException {

		this.fileStorageLocation1 = Paths.get(env.getProperty("app.file.upload-setbarcodeeancode", "/Images/")).toAbsolutePath()
				.normalize();
		this.fileStorageLocation2 = Paths.get(env.getProperty("app.file.upload-setbarcodeqty", "/Images/")).toAbsolutePath()
				.normalize();
		Files.createDirectories(this.fileStorageLocation2);

		Files.createDirectories(this.fileStorageLocation1);
	}
	
	
	
	
	@Override
	public SetBarcode saveSetBarcode(SetBarcodeDto setBarcodeDto) throws WriterException, IOException {
		// TODO Auto-generated method stub
		Optional<Product> product1 = productRepo.findById(setBarcodeDto.getProduct().getId());
		Product product=product1.get();
		Brand brand=setBarcodeDto.getBrand();
		
		SetBarcode barcode=new SetBarcode();
			
		
		if (barcodeRepository.byProductAndBrandId(setBarcodeDto.getProduct().getId(), setBarcodeDto.getBrand().getId()).isPresent()) {
			throw new RuntimeException(product.getProductName() + " is already present ");

		}
		
		Long uid = currentUserRoleName.getUserId();
		String uname = currentUserRoleName.getUserName();
		String role = currentUserRoleName.getRolename();
		
		barcode.setBrand(setBarcodeDto.getBrand());
		barcode.setProductname1(setBarcodeDto.getProductname1());
		barcode.setProductname2(setBarcodeDto.getProductname2());
		barcode.setQuantity(setBarcodeDto.getQuantity());
		barcode.setCreateddate(LocalDate.now());
		barcode.setCreatedtime(LocalTime.now());
		barcode.setCreatebyname(uname);
		barcode.setCreatedby(uid);
		barcode.setRole(role);
		String code;
		if (setBarcodeDto.getEancode()==null) {
			code=product.getEanCode();
		}
		
		else {
			code=setBarcodeDto.getEancode();
		}
		barcode.setEancode(code);
		barcode.setQuantity(setBarcodeDto.getQuantity());
		barcode.setProduct(setBarcodeDto.getProduct());

//		----------qr generate----------------
		
		String qrCodePath=FILE_PATH1;
		String name1 = UUID.randomUUID().toString();
		String qrname=product.getProductName()+name1+product.getId()+"-QRCODE.png";
		String qrCodeName=qrCodePath+product.getProductName()+name1+product.getId()+"-QRCODE.png";
//		--------------------
		
		String qrCodePath2=FILE_PATH2;
		String name2 = UUID.randomUUID().toString();
		String qrname2=product.getProductName()+name2+product.getId()+"-QRCODE.png";
		String qrCodeName2=qrCodePath2+product.getProductName()+name2+product.getId()+"-QRCODE.png";
//		--------------------
		barcode.setQrname(qrname);
//		barcode.setQrimgpath(qrCodeName);
		barcode.setQrimgpath("BarcodeImages/SetBarcode/eancode/"+qrname);
	
		barcode.setQrqtyname(qrname2);
	//	barcode.setQrqtyimgpath(qrCodeName2);
		barcode.setQrqtyimgpath("BarcodeImages/SetBarcode/qty/"+qrname2);

// 		--------------------
		var qrCodeWriter=new QRCodeWriter();
		BitMatrix bitMatrix= qrCodeWriter.encode("ProductEanCode: "+code,BarcodeFormat.QR_CODE,400,400);
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

		Path path=FileSystems.getDefault().getPath(qrCodeName);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		
//		-----------------------------------------
		var qrCodeWriter2=new QRCodeWriter();
		BitMatrix bitMatrix2= qrCodeWriter2.encode("ProductQty: "+setBarcodeDto.getQuantity(),BarcodeFormat.QR_CODE,400,400);
//		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

		Path path2=FileSystems.getDefault().getPath(qrCodeName2);
		MatrixToImageWriter.writeToPath(bitMatrix2, "PNG", path2);
//		--------------------------------------
		
		SetBarcode setbarcode = barcodeRepository.save(barcode);
		
		return setbarcode ; 

	}

	@Override
	public Optional<SetBarcode> getById(long id) {
		// TODO Auto-generated method stub
		Optional<SetBarcode> barcode = barcodeRepository.findById(id);
		return barcode;
	}

	@Override
	public List<SetBarcode> getAllSetBarcode() {
		// TODO Auto-generated method stub
		List<SetBarcode> barcodes = barcodeRepository.findAll();
		return barcodes;
	}

	@Override
	public String updateSetBarcode(SetBarcodeDto dto, long id) throws WriterException, IOException {
		
		Long uid = currentUserRoleName.getUserId();
		String uname = currentUserRoleName.getUserName();
		String role = currentUserRoleName.getRolename();
		
		if (barcodeRepository.existsById(id)) {
			 System.out.println("aklsdfjaskldj falksd fjaklsdjfs klajdjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");

			SetBarcode barcode = barcodeRepository.findById(id).get();
//			String qrqtyimgpath = barcode.getQrqtyimgpath();
			
			String qrqtyname = barcode.getQrqtyname();
			String eancode = barcode.getQrname();
			
			if (qrqtyname!=null ) {
				Path path=Paths.get(FILE_PATH2+qrqtyname);
				Files.delete(path);
			}
			if (eancode!=null) {
				Path path1=Paths.get(FILE_PATH1+eancode);
				Files.delete(path1);
			}
			
			int id2 = barcode.getProduct().getId();
			Product product3 = productRepo.findById(id2).get();
			String code;
			if (dto.getEancode()==null) {
				code=product3.getEanCode();
			}
			
			else {
				code=dto.getEancode();
			}
			 System.out.println("aklsdfjaskldj falksd fjaklsdjfs klajdjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
			SetBarcode barcode2=barcode;
//			barcode.setBrand(dto.getBrand());
			barcode.setEancode(code);
			barcode.setProductname1(dto.getProductname1());
			barcode.setProductname2(dto.getProductname2());
			barcode.setQuantity(dto.getQuantity());
			Product product2=barcode.getProduct();
			barcode.setUpdateddate(LocalDate.now());
			barcode.setUpdatedtime(LocalTime.now());
			barcode.setUpdatedbyname(uname);
			barcode.setUpdatedby(uid);
			barcode.setUpdatedrole(role);
//			-------------------------
			Optional<Product> product1 = productRepo.findById(product2.getId());
			
			Product product=product1.get();
			Brand brand=dto.getBrand();
			SetBarcode barcode1=new SetBarcode();
//			------------------------
			
			String qrCodePath2=FILE_PATH2;
			String name2 = UUID.randomUUID().toString();
			String qrname2=product.getProductName()+name2+product.getId()+"-QRCODE.png";
			String qrCodeName2=qrCodePath2+product.getProductName()+name2+product.getId()+"-QRCODE.png";
			
//			--------------------------
			barcode.setQrqtyname(qrname2);
			barcode.setQrqtyimgpath("BarcodeImages/SetBarcode/qty/"+qrname2);
			
//-----------------
			String qrCodePath1=FILE_PATH1;
			String name1 = UUID.randomUUID().toString();
			String qrname1=product.getProductName()+name1+product.getId()+"-QRCODE.png";
			String qrCodeName1=qrCodePath1+product.getProductName()+name1+product.getId()+"-QRCODE.png";
			barcode.setQrname(qrname1);
			barcode.setQrimgpath("BarcodeImages/SetBarcode/eancode/"+qrname1);
			
//---------------------------
			var qrCodeWriter2=new QRCodeWriter();
			BitMatrix bitMatrix2=
					qrCodeWriter2.encode(
							"ProductQty: "+dto.getQuantity(),
							BarcodeFormat.QR_CODE,400,400);
//			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

			Path path2=FileSystems.getDefault().getPath(qrCodeName2);
			MatrixToImageWriter.writeToPath(bitMatrix2, "PNG", path2);
			
//--------------------------------------------
			
			
			var qrCodeWriter1=new QRCodeWriter();
			BitMatrix bitMatrix1=
					qrCodeWriter1.encode(
							"ProductQty: "+code,
							BarcodeFormat.QR_CODE,400,400);
//			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

			Path path1=FileSystems.getDefault().getPath(qrCodeName1);
			MatrixToImageWriter.writeToPath(bitMatrix1, "PNG", path1);
			
			
			barcodeRepository.save(barcode2);
			return "set updated succesfully with id "+id;
	
		}
		return "no set is present with id "+id;
	}

	@Override
	public String deleteSetBarcode(long id) {
		// TODO Auto-generated method stub
		if (barcodeRepository.existsById(id)) {
			
			barcodeRepository.deleteById(id);
			return "set deleted succesfully with id "+id;
			
		}
		return "no set is present with id "+id;
	}

	
	@Override
	public Map<String, Object> IndexSetBarcodeAsc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = barcodeRepository.IndexSetBarcode().size();
		long pages = countpages / pagesize;
		
	    List<IndexSetBarcode> ipo = barcodeRepository.IndexSetBarcode(p);
	    
	    response.put("Index", ipo);
		response.put("CountPages", countpages);
		response.put("Pages", pages);

		return response;
	}
	

	@Override
	public Map<String, Object> IndexSetBarcodeDesc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = barcodeRepository.IndexSetBarcode().size();
		long pages = countpages / pagesize;
		
	    List<IndexSetBarcode> ipo = barcodeRepository.IndexSetBarcode(p);
	    
	    response.put("Index", ipo);
		response.put("CountPages", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchSetBarcode(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = barcodeRepository.IndexSetBarcode(p).size();
		long pages = countpages / pagesize;
		 List<IndexSetBarcode> setbarcode =barcodeRepository.IndexSetBarcode(p, search);
		 
		 	long searchcount = setbarcode.size();

			response.put("data", setbarcode);
			response.put("SearchCount", searchcount);

			return response;
	}




	@Override
	public SetBarcode getByProductAndBarcode(Long product, Long brand) {
		
		long p=product;
		int pid=(int)p;
		Product product1 = productRepo.findById(pid).get();
		Brand brand1 = brandRepo.findById(brand).get();
		
		SetBarcode setbarcode = barcodeRepository.findByProductAndBrand(product1, brand1).get();
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetailsImpl.getUsername();
		
		SetBarcodeActivityLog sbal = new SetBarcodeActivityLog();
		
		if(sbal.getUnqno() == null)
		{
			ArrayList<Integer> in = new ArrayList<Integer>();
			int i;
			for (i = 1; i < 1000000; i++) {
				in.add(i);

			}
			Collections.shuffle(in);
			for (i = 0; i < 1; i++) {
				System.out.println(in.get(i));
			}

			String uniq = "UNQ- ";
			sbal.setCreateddate(LocalDate.now());
			sbal.setCreatedtime(LocalTime.now());
			sbal.setLoggeduser(username);
			sbal.setSetbarcodeid(setbarcode.getId());
			sbal.setUnqno(uniq + in.get(i));

			activityLogRepo.save(sbal);
		}

		
		return setbarcode;
	}




	@Override
	public List<SetBarcodePrintIndex> fetchSetbarcodeProduct(int bid) {
		List<SetBarcodePrintIndex> fetchSetBarCodeProduct = barcodeRepository.FetchSetBarCodeProduct(bid);
		return fetchSetBarCodeProduct;
	}

	
}
