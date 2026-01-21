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

import com.SCM.IndexDto.IndexCartonBarcode;
import com.SCM.dto.CartonBarcodeDto;
import com.SCM.mapper.CartonBarcodeMapper;
import com.SCM.model.BillOfMaterial;
import com.SCM.model.BillOfMaterialItems;
import com.SCM.model.Brand;
import com.SCM.model.CartonBarcode;
import com.SCM.model.CartonBarcodeActivityLog;
import com.SCM.model.CartonBarcodeItems;
import com.SCM.model.GalaPrefix;
import com.SCM.model.MasterCartoon;
import com.SCM.model.Product;
import com.SCM.model.Staff;
import com.SCM.repository.BillOfMaterialItemRepo;
import com.SCM.repository.BillOfMaterialRepo;
import com.SCM.repository.BrandRepo;
import com.SCM.repository.CartonBarcodeActivityLogRepo;
import com.SCM.repository.CartonBarcodeItemRepo;
import com.SCM.repository.CartonBarcodeRepo;
import com.SCM.repository.GalaPrefixRepo;
import com.SCM.repository.MasterCartoonRepo;
import com.SCM.repository.ProductBarcodeActivityLogRepo;
import com.SCM.repository.ProductRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.CartonBarcodeService;
import com.google.zxing.WriterException;

@Service
public class CartonBarcodeServiceImpl implements CartonBarcodeService {

	@Autowired
	private CartonBarcodeMapper mapper;
	
	@Autowired
	private CartonBarcodeRepo cartonbarcodeRepo;
	
	@Autowired
	private GalaPrefixRepo galaRepo;
	@Autowired
	private StaffRepo staffRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private MasterCartoonRepo masterCartoonRepo;

	@Autowired
	private BillOfMaterialRepo billOfMaterialRepo;
	
	@Autowired
	private BillOfMaterialItemRepo billOfMaterialItemRepo;
	
	@Autowired
	private CartonBarcodeActivityLogRepo activityLogRepo;

//	app.file.upload-cartonbarcodeeancode=../webapps/scm/WEB-INF/classes/static/BarcodeImages/CartonBarcode/eancode/
//			app.file.upload-cartonbarcodegalaseries=../webapps/scm/WEB-INF/classes/static/BarcodeImagesCartonBarcode/galaseriesnumber/
//			app.file.upload-cartonbarcodepcs=.

	@Value("${app.file.upload-cartonbarcodeeancode}")
	private String FILE_PATH1;

	@Value("${app.file.upload-cartonbarcodegalaseries}")
	private String FILE_PATH2;

	@Value("${app.file.upload-cartonbarcodepcs}")
	private String FILE_PATH3;

	private final Path fileStorageLocation1;
	private final Path fileStorageLocation2;
	private final Path fileStorageLocation3;

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

	
	@Autowired
	public CartonBarcodeServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation1 = Paths.get(env.getProperty("app.file.upload-cartonbarcodeeancode", "/Images/"))
				.toAbsolutePath().normalize();
		this.fileStorageLocation2 = Paths.get(env.getProperty("app.file.upload-cartonbarcodegalaseries", "/Images/"))
				.toAbsolutePath().normalize();
		this.fileStorageLocation3 = Paths.get(env.getProperty("app.file.upload-cartonbarcodepcs", "/Images/"))
				.toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation1);
		Files.createDirectories(this.fileStorageLocation2);
		Files.createDirectories(this.fileStorageLocation3);
	}

	
	
	
    @Override
	public CartonBarcodeDto save(CartonBarcodeDto dto) throws WriterException, IOException {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = userDetailsImpl.getEmail();
		Staff staff = staffRepo.findByEmail(email).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		CartonBarcode entity = mapper.toEntity(dto);
		entity.setCreatebyname(uname);
		entity.setCreatedby(uid);
		entity.setRole(role);
		entity.setCreateddate(LocalDate.now());
		entity.setCreatedtime(LocalTime.now());
		long masterCartoon = entity.getMasterCartoon().getId();

		masterCartoonRepo.findById(masterCartoon).orElseThrow(() -> new RuntimeException("master cartoon id is not valid"));
		MasterCartoon mastercart = masterCartoonRepo.findById(masterCartoon).get();
		MasterCartoon masterCartoon2 = mastercart;
		mastercart.setStatus(true);
		masterCartoonRepo.save(masterCartoon2);

//		if (cartonbarcodeRepo.existsByMasterCartoon(mastercart)) {			
//			throw new RuntimeException("already created carton barcode for this master carton");
//		}

		galaRepo.findByStaff(staff).orElseThrow(() -> new RuntimeException("this staff does not have any gala "));

		GalaPrefix galawithstaff = galaRepo.findByStaff(staff).get();
		String galaname = galawithstaff.getGname();
		
		Optional<CartonBarcode> cartonBarcode = cartonbarcodeRepo.getLastGalaInfo(galaname);

		if (!cartonBarcode.isPresent()) {

			String galaseries = galaname + 1000000;
			entity.setGalaname(galaname);
			entity.setGalanumber(1000000);
			entity.setGalaPrefix(galawithstaff);
			entity.setGalaseriesname(galaseries);
			entity.setPcs(mastercart.getPcs());
			entity.setEancode(mastercart.getEancode());
			entity.setProduct(mastercart.getProduct());
			entity.setMasterCartoon(dto.getMasterCartoon());
			entity.setBrand(mastercart.getBrand());
			entity.setStdqty(mastercart.getStdqty());
			entity.setCreatebyname(uname);
			entity.setCreatedby(uid);
			entity.setRole(role);
			entity.setCreateddate(LocalDate.now());
			entity.setCreatedtime(LocalTime.now());
			entity.setEmptymasterctnweight(mastercart.getEmptymasterqty());
			entity.setMinweight(mastercart.getMinweight());
			entity.setMaxweight(mastercart.getMaxweight());
			entity.setNetweight(dto.getNetweight());
			entity.setGrossweight(dto.getGrossweight());
			entity.setOperatorname(dto.getOperatorname());
			entity.setHeight(mastercart.getHeight());
			entity.setWidth(mastercart.getWidth());
			entity.setLength(mastercart.getLength());
			entity.setOperatorname(mastercart.getOperatorname());
			entity.setNetweight(dto.getNetweight());
			entity.setGrossweight(mastercart.getGrossweight());
			
			long netweight = dto.getNetweight();
			System.out.println(netweight + "+++++++++++++++++++++++ netweight ");

			long id = dto.getMasterCartoon().getId();

			MasterCartoon master = masterCartoonRepo.findById(id).get();
			int pid2 = master.getProduct().getId();
			
			Product product = productRepo.findById(pid2).get();
			Optional<BillOfMaterial> billOfMaterial1 = billOfMaterialRepo.findByProduct(product);

			if (billOfMaterial1.isPresent()) {
				BillOfMaterial billOfMaterial = billOfMaterialRepo.findByProduct(product).get();

				int id3 = billOfMaterial.getId();
				billOfMaterial.getRate();

				List<BillOfMaterialItems> allBillOfmaterialItems = billOfMaterialItemRepo.getAllBillOfmaterialItems(id3);

				List<CartonBarcodeItems> c1 = new ArrayList<>();

				allBillOfmaterialItems.forEach(i -> {
					
					CartonBarcodeItems cartonBarcodeitem = new CartonBarcodeItems();
					cartonBarcodeitem.setAmount(i.getAmount());
					cartonBarcodeitem.setProduct(i.getProduct());
					cartonBarcodeitem.setRate(i.getRate());
					cartonBarcodeitem.setUom(i.getUom());

					if (entity.getLoosepacking() == 0) {
						cartonBarcodeitem.setQty(i.getQty() * master.getStdqty());
					}

					if (entity.getLoosepacking() != 0) {
						cartonBarcodeitem.setQty(i.getQty() * entity.getLoosepacking());
					}

					System.out.println("&&&&&&&&&&&&&&&&&&&&&&&& " + i.getQty() * entity.getLoosepacking());
					cartonBarcodeitem.setBillOfMaterialid(billOfMaterial);
					c1.add(cartonBarcodeitem);
				});
				entity.setCartonBarcodeItems(c1);

			}

			CartonBarcode save = cartonbarcodeRepo.save(entity);
			CartonBarcodeDto dto2 = mapper.toDto(save);
					
			return dto2; 
		}
		else {
		
			System.out.println("================================================================================================");

			CartonBarcode lastgala = cartonbarcodeRepo.getLastGalaInfo(galaname).get();
								
			if (cartonbarcodeRepo.existsByMasterCartoon(mastercart)) {	
							
				System.out.println("Enter.......");
				List<CartonBarcode>  fetchGalaAndMasterCarton = cartonbarcodeRepo.fetchGalaAndMasterCarton(mastercart.getId());
				
				String glan= fetchGalaAndMasterCarton.stream().map(i -> i.getGalaname()).findFirst().get();
				long galano=fetchGalaAndMasterCarton.stream().map(i -> i.getGalanumber()).findFirst().get();
				String galaser= fetchGalaAndMasterCarton.stream().map(i -> i.getGalaseriesname()).findFirst().get();
				
				String galaseries = lastgala.getGalaname();
				long galaseries1 = lastgala.getGalanumber();
				
				System.out.println(galaseries + "++++++++++++++++");
				System.out.println(galaseries1 + "+++++++++++++++++++++++");
				
				entity.setGalaname(glan);
				entity.setGalanumber(galano);
				entity.setGalaseriesname(galaser);	
				entity.setGalaPrefix(galawithstaff);
				entity.setPcs(mastercart.getPcs());
				entity.setProduct(mastercart.getProduct());
				entity.setEancode(mastercart.getEancode());
				entity.setMasterCartoon(dto.getMasterCartoon());
				entity.setCreateddate(LocalDate.now());
				entity.setBrand(mastercart.getBrand());
				entity.setStdqty(mastercart.getStdqty());
				entity.setEmptymasterctnweight(mastercart.getEmptymasterqty());
				entity.setMinweight(mastercart.getMinweight());
				entity.setMaxweight(mastercart.getMaxweight());
				entity.setOperatorname(mastercart.getOperatorname());
				entity.setNetweight(dto.getNetweight());
				entity.setGrossweight(mastercart.getGrossweight());
				entity.setHeight(mastercart.getHeight());
				entity.setWidth(mastercart.getWidth());
				entity.setLength(mastercart.getLength());
				
				long netweight = mastercart.getNetweight();
				System.out.println(netweight + "+++++++++++++++++++++++ netweight if .........");
				
			}
			else  {
				
				System.out.println("Middle..........................");

				CartonBarcode lastMaxGalaSeries = cartonbarcodeRepo.LastMaxGalaSeries(galaname);
			
				String galaname2 = lastMaxGalaSeries.getGalaname();
				long galanumber = lastMaxGalaSeries.getGalanumber();
				
				System.out.println(galanumber);
				long newGalanumber = galanumber + 1;
				
				System.out.println(lastMaxGalaSeries.getGalaseriesname() + 1 + "++++++++++++++++ last MAXXXXXXXXXXXXXXXX");
	
				entity.setGalaseriesname(galaname2 + newGalanumber);
				entity.setGalaname(galaname2);
				entity.setGalanumber(newGalanumber);
				entity.setGalaPrefix(galawithstaff);
				entity.setPcs(mastercart.getPcs());
				entity.setProduct(mastercart.getProduct());
				entity.setEancode(mastercart.getEancode());
				entity.setMasterCartoon(dto.getMasterCartoon());
				entity.setCreateddate(LocalDate.now());
				entity.setBrand(mastercart.getBrand());
				entity.setStdqty(mastercart.getStdqty());
				entity.setEmptymasterctnweight(mastercart.getEmptymasterqty());
				entity.setMinweight(mastercart.getMinweight());
				entity.setMaxweight(mastercart.getMaxweight());
				entity.setOperatorname(mastercart.getOperatorname());
				entity.setNetweight(dto.getNetweight());
				entity.setGrossweight(mastercart.getGrossweight());
				entity.setHeight(mastercart.getHeight());
				entity.setWidth(mastercart.getWidth());
				entity.setLength(mastercart.getLength());
				
				long netweight = mastercart.getNetweight();
				System.out.println(netweight + "+++++++++++++++++++++++ netweight else .........");
				
			}
			
			long id = dto.getMasterCartoon().getId();
			System.out.println(id + "masterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

			MasterCartoon master = masterCartoonRepo.findById(id).get();
			int id2 = master.getProduct().getId();
			System.out.println(id2 + "   masterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			
			long id3 = master.getBrand().getId();
			System.out.println(id3 + "   masterrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

			Product product = productRepo.findById(id2).get();
			Brand brand2 = brandRepo.findById(id3).get();

			Optional<BillOfMaterial> billOfMaterial1 = billOfMaterialRepo.findByProduct(product);

			if (billOfMaterial1.isPresent()) {
				
				BillOfMaterial billOfMaterial2 = billOfMaterialRepo.findByProduct(product).get();

				int id4 = billOfMaterial2.getId();
				billOfMaterial2.getRate();
		
				List<BillOfMaterialItems> allBillOfmaterialItems = billOfMaterialItemRepo.getAllBillOfmaterialItems(id4);

				List<CartonBarcodeItems> c1 = new ArrayList<>();

				allBillOfmaterialItems.forEach(i -> {
					
					CartonBarcodeItems cartonBarcodeitem = new CartonBarcodeItems();
					cartonBarcodeitem.setAmount(i.getAmount());
					cartonBarcodeitem.setProduct(i.getProduct());
					cartonBarcodeitem.setRate(i.getRate());
					cartonBarcodeitem.setUom(i.getUom());

					if (entity.getLoosepacking() == 0) {
						cartonBarcodeitem.setQty(i.getQty() * master.getStdqty());
					}

					if (entity.getLoosepacking() != 0) {
						cartonBarcodeitem.setQty(i.getQty() * entity.getLoosepacking());
					}

					cartonBarcodeitem.setBillOfMaterialid(billOfMaterial2);
					c1.add(cartonBarcodeitem);
				});
				entity.setCartonBarcodeItems(c1);
				
			}
			CartonBarcode save2 = cartonbarcodeRepo.save(entity);
			String username = userDetailsImpl.getUsername();
		
			CartonBarcodeActivityLog cbal = new CartonBarcodeActivityLog();
			
			if(cbal.getUnqno() == null)
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
				cbal.setCreateddate(LocalDate.now());
				cbal.setCreatedtime(LocalTime.now());
				cbal.setLoggeduser(username);
				cbal.setCartonbarcodeid(save2.getId());
				cbal.setUnqno(uniq + in.get(i));		
			}
								
			CartonBarcode save = cartonbarcodeRepo.save(entity);
			CartonBarcodeDto dto2 = mapper.toDto(save);
		
			activityLogRepo.save(cbal);
		
			return dto2;
		}
	}
	
	@Override
	public List<CartonBarcodeDto> getAll() {
		List<CartonBarcode> cartons = cartonbarcodeRepo.findAll();
		List<CartonBarcodeDto> collect = cartons.stream().map(cr -> mapper.toDto(cr)).collect(Collectors.toList());
		return collect;
	}

	
	@Override
	public Optional<CartonBarcodeDto> getById(long id) {
	
		Optional<CartonBarcode> cartons = cartonbarcodeRepo.findById(id);
		Optional<CartonBarcodeDto> dto = cartons.map((cr) -> mapper.toDto(cr));
		return dto;
	}

	@Override
	public String updateCarton(CartonBarcodeDto dto, long id) throws WriterException, IOException {

		if (cartonbarcodeRepo.existsById(id)) {

			Optional<CartonBarcode> carton1 = cartonbarcodeRepo.findById(id);
			CartonBarcode carton = carton1.get();

			CartonBarcode cartonBarcode = carton;

			int id2 = carton.getProduct().getId();
			Product prod = productRepo.findById(id2).get();

			CartonBarcode entity = cartonbarcodeRepo.findById(id).get();
			CartonBarcode entity2 = entity;

			entity.setEmptymasterctnweight(dto.getEmptymasterctnweight());
			entity.setMinweight(dto.getMinweight());
			entity.setMaxweight(dto.getMaxweight());
			entity.setHeight(dto.getHeight());
			entity.setWidth(dto.getWidth());
			entity.setLength(dto.getLength());


//						end qr code for pcsimage

			CartonBarcode save = cartonbarcodeRepo.save(entity2);
			CartonBarcodeDto dto2 = mapper.toDto(save);

			return "succesfully updated the carton barcode with id " + id;

		}
		return "no cartonbarcode is present with id " + id;
	}

	
	@Override
	public String deleteCarton(long id) {

		if (cartonbarcodeRepo.existsById(id)) {

			CartonBarcode cartonBarcode = cartonbarcodeRepo.findById(id).get();
			MasterCartoon masterCartoon = cartonBarcode.getMasterCartoon();
			long id2 = masterCartoon.getId();
			MasterCartoon masterCartoon2 = masterCartoonRepo.findById(id2).get();
			MasterCartoon masterCartoon3 = masterCartoon2;
			masterCartoon2.setStatus(false);
			masterCartoonRepo.save(masterCartoon3);
			cartonbarcodeRepo.deleteById(id);

			return "Succesfully delete the cartonbarcode with id " + id;
		}
		return "no carton barcode is present with id " + id;
	}

	@Override
	public Map<String, Object> IndexCartonBarcodeAsc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = cartonbarcodeRepo.IndexAllCartonBarcode(p).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexCartonBarcode> icb = cartonbarcodeRepo.IndexAllCartonBarcode(p);

		response.put("Index", icb);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexCartonBarcodeDesc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = cartonbarcodeRepo.IndexAllCartonBarcode(p).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexCartonBarcode> icb = cartonbarcodeRepo.IndexAllCartonBarcode(p);

		response.put("Index", icb);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchCartonBarcode(int pageno, int pagesize, String search) {
		// TODO Auto-generated method stub

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = cartonbarcodeRepo.IndexAllCartonBarcode(p).size();
		long pages = countpages / pagesize;
		List<IndexCartonBarcode> setbarcode = cartonbarcodeRepo.IndexAllCartonBarcode(p, search);

		long searchcount = setbarcode.size();

		response.put("data", setbarcode);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Autowired
	private BrandRepo brandRepo;

	@Override
	public Optional<CartonBarcode> getByProductAndBrand(long productid, long brandid) {
		// TODO Auto-generated method stub
		long p = productid;
		int pid = (int) p;
		Product product = productRepo.findById(pid).get();
		Brand brand = brandRepo.findById(brandid).get();
		Optional<CartonBarcode> cartonbarcode = cartonbarcodeRepo.findByProductAndBrand(product, brand);
		return cartonbarcode;
	}

}
