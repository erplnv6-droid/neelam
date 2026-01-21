package com.SCM.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.ExportDto.ExportRetailer;
import com.SCM.IndexDto.RetailerDetailsByPincode;
import com.SCM.IndexDto.RetailerToStaffDto;
import com.SCM.config.UserId;
import com.SCM.dto.RetailerDto;
import com.SCM.model.Retailer;
import com.SCM.payload.MessageResponse;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.RetailerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/retailer")
public class RetailerController {

	@Autowired
	private RetailerService retailerService;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private StaffRepo staffRepo;

	@Autowired
	private UserId user;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllRetailer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
//        {
		System.out.println(authentication.getAuthorities() + " get Authorites");
//        }

		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

			return new ResponseEntity<>(retailerService.getAllRetail(), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_NSM"))) {

			return new ResponseEntity<>(retailerService.getAllRetail(), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"))) {

			return new ResponseEntity<>(retailerService.getRetailerByRSM1(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"))) {

			return new ResponseEntity<>(retailerService.getRetailerByASM(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"))) {

			return new ResponseEntity<>(retailerService.getRetailerByASE(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RETAILER"))) {

			return new ResponseEntity<>(retailerService.getRetailerByRETAILER(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_DISTRIBUTOR"))) {

			return new ResponseEntity<>(retailerService.getRetailerByDISTRIBUTOR(user.getId().intValue()),
					HttpStatus.OK);
		}

		else {
			return new ResponseEntity<>("Retailer Not!!!! Found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllRetailer")
	public List<Retailer> getAll() {
		return retailerService.getAllRetailer();
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {
		try {
			RetailerDto retailer = retailerService.getRetailerById(id);

//			Retailer retailer = retailerRepo.getRetailerById(id);

			return new ResponseEntity<>(retailer, HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<Retailer>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll1")
	public List<Retailer> getAll1() {

		return retailerService.getAllWorkOrderWithRetailer();

	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody @Valid RetailerDto retailerDto) {

		if (retailerRepo.existsByPerEmail(retailerDto.getPerEmail())
				|| distributorRepo.existsByPerEmail(retailerDto.getPerEmail())
				|| staffRepo.existsByEmail(retailerDto.getPerEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		return new ResponseEntity<>(retailerService.saveRetailer(retailerDto), HttpStatus.CREATED);
	}

	@PostMapping("/create1")
	public ResponseEntity<?> createRetailer(@RequestBody @Valid RetailerDto retailerDto) {

		if (retailerRepo.existsByPerEmail(retailerDto.getPerEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		return new ResponseEntity<>(retailerService.registerRetailer(retailerDto), HttpStatus.CREATED);
	}

	@PostMapping("/convertto") // Convert temporary to Retailer
	public ResponseEntity<?> ConvertRetailer(@RequestBody @Valid RetailerDto retailerDto) {

		if (retailerRepo.existsByPerEmail(retailerDto.getPerEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		return new ResponseEntity<>(retailerService.ConvertToRetailer(retailerDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {

		retailerService.deleteRetailer(id);
		return new ResponseEntity<String>("Your Retailer is SuccessFully Deleted!!", HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public Retailer updateUser(@PathVariable("id") int id, @RequestBody RetailerDto retailerDto) {

		return retailerService.updateRetailer(retailerDto, id);
	}

	
	@PutMapping("/update1/{id}")
	public Retailer updateUser1(@PathVariable("id") int id, @RequestBody RetailerDto retailerDto) {

		return retailerService.updateRetailer1(retailerDto, id);
	}
	
	@GetMapping("{d_id}/{from_date}/{to_date}")
	public ResponseEntity<List<Retailer>> getWorkOrderId(@PathVariable("d_id") int d_id,
			@PathVariable("from_date") String from_date, @PathVariable("to_date") String to_date) {
		List<Retailer> r = retailerService.getWorkOrdersbyId(d_id, from_date, to_date);

		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	// ------Retailer ase asm rsm nsm

	@GetMapping("ase/{aseId}")
	public ResponseEntity<List<RetailerDto>> getRetailerByAseId(@PathVariable("aseId") int aseId) {
		List<RetailerDto> dto = retailerService.getRetailerByASE(aseId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/{asmId}")
	public ResponseEntity<List<RetailerDto>> getRetailerByAsmId(@PathVariable("asmId") int asmId) {
		List<RetailerDto> dto = retailerService.getRetailerByASM(asmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping("rsm/{rsmId}")
	public ResponseEntity<List<RetailerDto>> getRetailerByRsmId1(@PathVariable("rsmId") int rsmId) {
		List<RetailerDto> dto = retailerService.getRetailerByRSM1(rsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("nsm/{nsmId}")
	public ResponseEntity<List<RetailerDto>> getRetailerByNsmId(@PathVariable("nsmId") int nsmId) {
		List<RetailerDto> dto = retailerService.getRetailerByNSM(nsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("zone/{zonesId}")
	public ResponseEntity<List<RetailerDto>> getRetailerByZoneId(@PathVariable("zonesId") int zonesId) {
		List<RetailerDto> dto = retailerService.getRetailerByZONE(zonesId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("state/{stateId}")
	public ResponseEntity<List<RetailerDto>> getRetailerByStateId(@PathVariable("stateId") int stateId) {
		List<RetailerDto> dto = retailerService.getRetailerBySTATE(stateId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	// ------- index Retailer

	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexRetailer(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby, @PathVariable("field") String field,
			@PathVariable("search") String search) {

		if (!search.equals(" ")) {
			return new ResponseEntity<>(retailerService.SearchRetailer(pageno, pagesize, search), HttpStatus.OK);
		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(retailerService.IndexRetailerAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(retailerService.IndexRetailerDesc(pageno, pagesize, field), HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("page1/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexRetailer1(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby, @PathVariable("field") String field,
			@PathVariable("search") String search) {

		if (!search.equals(" ")) {
			return new ResponseEntity<>(retailerService.SearchAllRetailer(pageno, pagesize, search), HttpStatus.OK);
		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(retailerService.IndexAllRetailerAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(retailerService.IndexAllRetailerDsc(pageno, pagesize, field), HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/export")
	public ResponseEntity<?> exportRetailer() {
		List<ExportRetailer> dto = retailerService.exportRetailer();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@GetMapping("/fetchlocation/{pincode}")
	public ResponseEntity<?> FetchLocationByPincode(@PathVariable("pincode") String pincode) {

		List<RetailerDetailsByPincode> retailerbypincode = retailerService.retailerbypincode(pincode);
		
		return new ResponseEntity<>(retailerbypincode, HttpStatus.OK);

	}
	
	
	@GetMapping("/rts/{ret_id}")
	public ResponseEntity<?> retailertostaffById(@PathVariable("ret_id") int ret_id) {
		
		 List<com.SCM.IndexDto.RetailerWithRetailerStaffDto> getretailertostaffbyId = retailerService.getretailertostaffbyId(ret_id);

		return new ResponseEntity<>(getretailertostaffbyId, HttpStatus.OK);
	}
	
	
	@GetMapping("/retailertostaff/{id}")
	public ResponseEntity<?> getRetailerToStaff(@PathVariable("id") int id) {
		
		 List<RetailerToStaffDto> retailerTostaff = retailerService.getRetailerTostaff(id);

		return new ResponseEntity<>(retailerTostaff, HttpStatus.OK);
	}
	
	
//	retailer based on groupn
	
	@GetMapping("/groupn/page/{pageno}/{pagesize}/{sortby}/{field}/{search1}/{search2}/{search3}/{search4}/{search5}/{count}")
	public ResponseEntity<?> IndexRetailerByGroupn(
			@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby,
			@PathVariable("field") String field,
			@PathVariable("search1") String search1,
			@PathVariable("search2") String search2,
			@PathVariable("search3") String search3,
			@PathVariable("search4") String search4,
			@PathVariable("search5") String search5,
			@PathVariable("count") int count
			) 
	
	{

		if ("asc".equals(sortby)) {
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++asc++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return new ResponseEntity<>(retailerService.findRetailerWhereGroupnIsAsc(pageno, pagesize, field, search1,search2,search3,search4,search5,count),HttpStatus.OK);
			

		} else if ("desc".equals(sortby)) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++desc+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			return new ResponseEntity<>(retailerService.findRetailerWhereGroupnIsDesc(pageno, pagesize, field, search1,search2,search3,search4,search5,count),HttpStatus.OK);

		} else {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@GetMapping("/groupn/page/retaileranddistributorwithgroupn/{pageno}/{pagesize}/{sortby}/{field}/{search1}/{search2}/{search3}/{search4}/{search5}/{count}")
	public ResponseEntity<?> IndexRetailerAndDistributorByGroupn(
			@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby,
			@PathVariable("field") String field,
			@PathVariable("search1") String search1,
			@PathVariable("search2") String search2,
			@PathVariable("search3") String search3,
			@PathVariable("search4") String search4,
			@PathVariable("search5") String search5,
			@PathVariable("count") int count
			) 
	
	{

		if ("asc".equals(sortby)) {
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++asc++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return new ResponseEntity<>(retailerService.findRetailerAndDistributorWhereGroupnIsAsc(pageno, pagesize, field, search1,search2,search3,search4,search5,count),HttpStatus.OK);
			

		} else if ("desc".equals(sortby)) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++desc+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			return new ResponseEntity<>(retailerService.findRetailerAndDistributorWhereGroupnIsDesc(pageno, pagesize, field, search1,search2,search3,search4,search5,count),HttpStatus.OK);

		} else {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}



