package com.SCM.serviceimpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.FetchBomProductIndex;
import com.SCM.IndexDto.IndexMasterCarton;
import com.SCM.dto.MasterCartoonDto;
import com.SCM.mapper.MasterCartoonMapper;
import com.SCM.model.BillOfMaterial;
import com.SCM.model.Brand;
import com.SCM.model.MasterCartoon;
import com.SCM.model.Product;
import com.SCM.model.Staff;
import com.SCM.repository.BillOfMaterialRepo;
import com.SCM.repository.BrandRepo;
import com.SCM.repository.CartonBarcodeRepo;
import com.SCM.repository.MasterCartoonRepo;
import com.SCM.repository.ProductRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.MasterCartoonService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.experimental.var;

@Service
public class MasterCartoonServiceImpl implements MasterCartoonService {

	@Autowired
	private MasterCartoonRepo masterCartoonRepo;
	
	@Autowired
	private BillOfMaterialRepo billOfMaterialRepo;

	@Autowired
	private MasterCartoonMapper mapper;

	@Autowired
	private CartonBarcodeRepo cartonBarcodeRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private BrandRepo brandRepo;

	@Autowired
	private StaffRepo staffRepo;
	
	

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
	public MasterCartoonDto saveMasterCartoon(MasterCartoonDto cartoonDto) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		MasterCartoon entity = mapper.toEntity(cartoonDto);
		Product product = entity.getProduct();

		Brand brand = entity.getBrand();
		int id = cartoonDto.getProduct().getId();
		Product product2 = productRepo.findById(id).get();

		if (cartoonDto.getEancode() != null) {
			entity.setEancode(cartoonDto.getEancode());
		} else {
			entity.setEancode(product2.getEanCode());
		}

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		Staff user = staffRepo.findById(loggeduser).get();
		System.out.println(user.getStaffName() + " staff name ");

		entity.setOperatorname(user.getStaffName());
		entity.setNetweight(cartoonDto.getNetweight());
		entity.setGrossweight(cartoonDto.getGrossweight());
		entity.setCreatebyname(uname);
		entity.setCreatedby(uid);
		entity.setRole(role);
		entity.setCreateddate(LocalDate.now());
		entity.setCreatedtime(LocalTime.now());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		System.out.println(currentPrincipalName + " name of the user+++++++++++++++++++++++++++++++++++++++++++++===");
		System.out.println(currentPrincipalName + " name of the user+++++++++++++++++++++++++++++++++++++++++++++===");

		if (masterCartoonRepo.byBrandAndProductId(product.getId(), brand.getId()).isPresent()) {
			throw new RuntimeException(
					" this product with this brand is aleady present please change either one info ");
		}
		entity.setCreateddate(LocalDate.now());
		entity.setStatus(false);

		MasterCartoon save = masterCartoonRepo.save(entity);
		MasterCartoonDto dto = mapper.toDto(save);
		return dto;
	}

	@Override
	public Optional<MasterCartoonDto> getById(long id) {
		// TODO Auto-generated method stub
		Optional<MasterCartoon> cartoon = masterCartoonRepo.findById(id);
		Optional<MasterCartoonDto> cart = cartoon.map((cr) -> mapper.toDto(cr));
		return cart;
	}

	@Override
	public List<MasterCartoonDto> getAll() {
		// TODO Auto-generated method stub
		List<MasterCartoon> cartoons = masterCartoonRepo.findAll();
		List<MasterCartoonDto> collect = cartoons.stream().map((cr) -> mapper.toDto(cr)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public String updateNormal(long id, MasterCartoonDto cartoonDto) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		if (masterCartoonRepo.existsById(id)) {
			MasterCartoon carton = masterCartoonRepo.findById(id).get();
			MasterCartoon entity = mapper.toEntity(cartoonDto);
			entity.setId(id);
			entity.setUpdatedbyname(uname);
			entity.setUpdatedby(uid);
			entity.setUpdatedrole(role);
			entity.setUpdateddate(LocalDate.now());
			entity.setUpdatedtime(LocalTime.now());
			masterCartoonRepo.save(entity);

			return "succesfully updated the master cartoon with id" + id;
		}
		return "no cartoon is available with id " + id;
	}

	@Override
	public String updateMasterCartoon(long id, MasterCartoonDto mastercartoonDto) throws WriterException, IOException {

		if (masterCartoonRepo.existsById(id)) {

			MasterCartoon masterCartoon = masterCartoonRepo.findById(id).get();
			cartonBarcodeRepo.findByMasterCartoon(masterCartoon);
			if (cartonBarcodeRepo.existsByMasterCartoon(masterCartoon)) {
				throw new RuntimeException("carton barcode is already created with master id " + id);
			} else {
				return "Succesfully deleted the cartoon with id " + id;
			}
		}

//if (masterCartoonRepo.existsById(id)) {
//			
//			MasterCartoon masterCartoon = masterCartoonRepo.findById(id).get();
//			cartonBarcodeRepo.findByMasterCartoon(masterCartoon);
//			
//			
//			if(cartonBarcodeRepo.existsByMasterCartoon(masterCartoon))
//			{
//				throw new RuntimeException("carton barcode is already created with master id "+id);
//			}
//			else {
//				MasterCartoon entity = mapper.toEntity(mastercartoonDto);
//				entity.setId(id);
//				masterCartoonRepo.save(entity);
//				return "Succesfully updated the cartoon with id "+id;
//			}}
//		
//return "no mastercarton present with id "+id;	

		if (masterCartoonRepo.existsById(id)) {

			MasterCartoon masterCartoon = masterCartoonRepo.findById(id).get();

			System.out.println(
					"**********************************************************************************************");

			Product product = productRepo.findById(mastercartoonDto.getProduct().getId()).get();
			Brand brand = brandRepo.findById(mastercartoonDto.getBrand().getId()).get();
			Optional<MasterCartoon> masterCartoon2 = masterCartoonRepo.findByProductAndBrand(product, brand);
//			long id4 = masterCartoon2.getId();

			cartonBarcodeRepo.findByMasterCartoon(masterCartoon);

			if (cartonBarcodeRepo.existsByMasterCartoon(masterCartoon)) {
				return "carton barcode is already created with master id ";
			}

			if (masterCartoon2.isPresent()) {
				MasterCartoon masterCartoon3 = masterCartoonRepo.findByProductAndBrand(product, brand).get();
				long id4 = masterCartoon3.getId();

				if (id4 == id) {
					MasterCartoon entity = mapper.toEntity(mastercartoonDto);
					entity.setId(id);
					masterCartoonRepo.save(entity);

					return "Succesfully update the cartoon with id " + id;
				}
			}

			else if (masterCartoonRepo.existsByProductAndBrand(product, brand)) {
				System.out.println(product.getId());
				System.out.println(brand.getId());
				return "master carton is already present with product and brand ";
			}

			else {

				MasterCartoon entity = mapper.toEntity(mastercartoonDto);
				entity.setId(id);
				masterCartoonRepo.save(entity);

				return "Succesfully update the cartoon with id " + id;
			}

		}

		return "no mastercarton present with id " + id;

		// TODO Auto-generated method stub
//		if (masterCartoonRepo.existsById(id)) {
//	
//			MasterCartoon carton = masterCartoonRepo.findById(id).get();
//			
//			
//			CartonBarcode cartonbarcode = cartonBarcodeRepo.findByMasterCartoon(carton).get();
//			 CartonBarcode cart = cartonBarcodeRepo.findById(cartonbarcode.getId()).get();
////			update carton barcode
//			 
//			 
//			 CartonBarcode cart2=cart;
//			 
//			cart.setId(cart.getId());
//			cart.setEancode(mastercartoonDto.getEancode());
//			cart.setEmptymasterctnweight(mastercartoonDto.getEmptymasterqty());
//			cart.setHeight(mastercartoonDto.getHeight());
//			cart.setLength(mastercartoonDto.getLength());
//			cart.setMaxweight(mastercartoonDto.getMaxweight());
//			cart.setMinweight(mastercartoonDto.getMinweight());
//			cart.setPcs(mastercartoonDto.getPcs());
//			cart.setProduct(mastercartoonDto.getProduct());
//			cart.setStdqty(mastercartoonDto.getStdqty());
//			cart.setWidth(mastercartoonDto.getWidth());
//			
//			String pcsimagepath=cart.getPcsimagepath();
//			File file1 = new File(pcsimagepath);
//
//			if (file1.exists()) {
//				file1.delete();
//			}
//			
//			String eancodeimagepath = cart.getEancodeimagepath();
//			File file2 = new File(eancodeimagepath);
//
//			if (file2.exists()) {
//				file2.delete();
//			}
////			set path for pcs qr
//			
//			String qrCodePath1 = "E:\\New folder\\cartonbarcode\\pcs\\";
//			String name11 = UUID.randomUUID().toString();
//			String qrname1 = mastercartoonDto.getProductname1() + name11 + "-QRCODE.png";
//			String qrCodeName1 = qrCodePath1 + mastercartoonDto.getProductname1() + name11 + "-QRCODE.png";
//			
//			cart.setPcsimagename(qrname1);
//			cart.setPcsimagepath(qrCodeName1);
//			
//			var qrCodeWriter1 = new QRCodeWriter();
//			BitMatrix bitMatrix1 = qrCodeWriter1.encode(""+mastercartoonDto.getPcs(), BarcodeFormat.QR_CODE, 400, 400);
//			Path path1 = FileSystems.getDefault().getPath(qrCodeName1);
//			MatrixToImageWriter.writeToPath(bitMatrix1, "PNG", path1);
//// set path for ean qr		
//			
//			String qrCodePath2 = "E:\\New folder\\cartonbarcode\\eancode\\";
//			String name22 = UUID.randomUUID().toString();
//			String qrname2 = mastercartoonDto.getProductname1() + name22 + "-QRCODE.png";
//			String qrCodeName2 = qrCodePath2 + mastercartoonDto.getProductname1() + name22 + "-QRCODE.png";
//			cart.setEancodeimagename(qrname2);
//			cart.setEancodeimagepath(qrCodeName2);
//			
//			var qrCodeWriter2 = new QRCodeWriter();
//			BitMatrix bitMatrix2 = qrCodeWriter2.encode(mastercartoonDto.getEancode(), BarcodeFormat.QR_CODE, 400, 400);
//			Path path2 = FileSystems.getDefault().getPath(qrCodeName2);
//			MatrixToImageWriter.writeToPath(bitMatrix2, "PNG", path2);
//			
//cartonBarcodeRepo.save(cart2);
////			
//			carton.setEancode(mastercartoonDto.getEancode());
//			carton.setEmptymasterqty(mastercartoonDto.getEmptymasterqty());
//			carton.setHeight(mastercartoonDto.getHeight());
//			carton.setId(id);
//			carton.setLength(mastercartoonDto.getLength());
//			carton.setMaxweight(mastercartoonDto.getMaxweight());
//			carton.setMinweight(mastercartoonDto.getMinweight());
//			carton.setProductname1(mastercartoonDto.getProductname1());
//			carton.setProductname2(mastercartoonDto.getProductname2());
//			carton.setQty(mastercartoonDto.getQty());
//			carton.setWidth(mastercartoonDto.getWidth());
//			carton.setStdqty(mastercartoonDto.getStdqty());
//			carton.setPcs(mastercartoonDto.getPcs());
//	
//			
//			
//			
//			MasterCartoon save = masterCartoonRepo.save(carton);
//			
//			
//			
//			
//			
//			
//			return "succesfully updated the cartoon with id "+id;
//		}
//		return "no cartoon is available with id "+id;

	}

	@Override
	public String deleteMasterCartoon(long id) {
		// TODO Auto-generated method stub
		if (masterCartoonRepo.existsById(id)) {

			masterCartoonRepo.deleteById(id);
			return "succesfully deleted the mastercarton with id " + id;

		}
		return "no cartoon is available with id " + id;
	}

	@Override
	public Map<String, Object> IndexMasterCartonAsc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = masterCartoonRepo.IndexMasterCarton(p).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexMasterCarton> icb = masterCartoonRepo.IndexMasterCarton(p);

		response.put("Index", icb);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexMasterCartonDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = masterCartoonRepo.IndexMasterCarton(p).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexMasterCarton> icb = masterCartoonRepo.IndexMasterCarton(p);

		response.put("Index", icb);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchMasterCarton(int pageno, int pagesize, String search) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = masterCartoonRepo.IndexMasterCarton(p).size();
		long pages = countpages / pagesize;

		List<IndexMasterCarton> setbarcode = masterCartoonRepo.IndexMasterCarton(p, search);

		long searchcount = setbarcode.size();

		response.put("data", setbarcode);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public List<MasterCartoon> getByBrand(long id) {
		Brand brand = brandRepo.findById(id).get();
		List<MasterCartoon> master = masterCartoonRepo.findByBrand(brand);
		return master;
	}

	@Override
	public List<FetchBomProductIndex> fetchProductfromBom(long bid) {
		String brandName = brandRepo.findById(bid).get().getBrandName();
		List<FetchBomProductIndex> fetchbomproduct = billOfMaterialRepo.fetchbomproduct(brandName);
		return fetchbomproduct;
	}
}
