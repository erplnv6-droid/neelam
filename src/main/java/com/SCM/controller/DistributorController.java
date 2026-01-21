package com.SCM.controller;

import com.SCM.ExportDto.ExportDistributor;
import com.SCM.IndexDto.DistDto;
import com.SCM.IndexDto.DistributorToStaffDto;
import com.SCM.config.UserId;
import com.SCM.dto.*;
import com.SCM.model.Distributor;
import com.SCM.model.ERole;
import com.SCM.payload.MessageResponse;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.DistributorService;
import com.SCM.serviceimpl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/distributor")
public class DistributorController {

	@Autowired
	private UserId user;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DistributorService distributorService;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private StaffRepo staffRepo;

	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllDistributor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getAuthorities());
//        }
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            System.out.println(user.getAuthorities().contains(ERole.ROLE_ADMIN));
			return new ResponseEntity<>(distributorService.getAllDist(), HttpStatus.OK);
		}

		else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_NSM"))) {

			return new ResponseEntity<>(distributorService.getAllDist(), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"))) {

			return new ResponseEntity<>(distributorService.getDistributorByRSM(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"))) {

			return new ResponseEntity<>(distributorService.getDistributorByASM(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"))) {

			return new ResponseEntity<>(distributorService.getDistributorByASE(user.getId().intValue()), HttpStatus.OK);
			
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DISTRIBUTOR"))) {

			return new ResponseEntity<>(distributorService.getDistributorBySingleDist(user.getId().intValue()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Distributor Not!!!! Found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<?> get(@PathVariable Integer id) {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		user.getAuthorities();
//		System.out.println(user.getAuthorities());

		try {
			Distributor distributor = distributorService.getDistributorById(id);
			
//			Distributor distributor  = distributorRepo.getDistributorById(id);
			
			return new ResponseEntity<>(distributor, HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Distributor>(HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("/{d_id}/{from_date}/{to_date}")
	public List<WorkOrderDto> fetchprimary(@PathVariable("d_id") Integer d_id,
			@PathVariable("from_date") String from_date, @PathVariable("to_date") String to_date) {

		String sql = "CALL fetchworkorderbyid(?,?,?)";

		List<WorkOrderDto> dtos = jdbcTemplate.query(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, d_id);
				ps.setString(2, from_date);
				ps.setString(3, to_date);
			}
		}, new BeanPropertyRowMapper<WorkOrderDto>(WorkOrderDto.class));

		return dtos;
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody @Valid DistributorDto distributorDto) {

		if (distributorRepo.existsByPerEmail(distributorDto.getPerEmail()) 
				|| staffRepo.existsByEmail(distributorDto.getPerEmail())
				|| retailerRepo.existsByPerEmail(distributorDto.getPerEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		return new ResponseEntity<>(distributorService.saveDistributor(distributorDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		distributorService.deleteDistributor(id);
		return new ResponseEntity<String>("Your Brand is SuccessFully Deleted!!", HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public Distributor updateUser(@PathVariable("id") int id, @RequestBody DistributorDto distributorDto) {
		return distributorService.updateDistributor(distributorDto,id);
	}
	
	@PutMapping("/update1/{id}")
	public Distributor updateUser1(@PathVariable("id") int id, @RequestBody DistributorDto distributorDto) {
		
		return distributorService.updateDistributor1(distributorDto,id);
	}

	// ---------------------------- ase asm rsm nsm

	@GetMapping("ase/{aseId}")
	public ResponseEntity<List<DistributorDto>> getDistributorByAseId(@PathVariable("aseId") int aseId) {
		List<DistributorDto> dto = distributorService.getDistributorByASE(aseId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/{asmId}")
	public ResponseEntity<List<DistributorDto>> getDistributorByAsmId(@PathVariable("asmId") int asmId) {
		List<DistributorDto> dto = distributorService.getDistributorByASE(asmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("rsm/{rsmId}")
	public ResponseEntity<List<DistributorDto>> getDistributorByRsmId(@PathVariable("rsmId") int rsmId) {
		List<DistributorDto> dto = distributorService.getDistributorByRSM(rsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("nsm/{nsmId}")
	public ResponseEntity<List<DistributorDto>> getDistributorByNsmId(@PathVariable("nsmId") int nsmId) {
		List<DistributorDto> dto = distributorService.getDistributorByNSM(nsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("zone/{zonesId}")
	public ResponseEntity<List<DistributorDto>> getDistributorByZoneId(@PathVariable("zonesId") int zonesId) {
		List<DistributorDto> dto = distributorService.getDistributorByZONE(zonesId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("state/{stateId}")
	public ResponseEntity<List<DistributorDto>> getDistributorByStateId(@PathVariable("stateId") int stateId) {
		List<DistributorDto> dto = distributorService.getDistributorBySTATE(stateId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	// ------------------- index distributor

	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search") String search) {

		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(distributorService.SearchDistributor(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(distributorService.IndexDistributorAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(distributorService.IndexDistributorDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
	
	
	@GetMapping("page1/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAllDistributor(@PathVariable("pageno") int pageno,
			                                     @PathVariable("pagesize") int pagesize,
			                                     @PathVariable("sortby") String sortby,
			                                     @PathVariable("field") String field,
			                                     @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(distributorService.SearchAllDistributor(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(distributorService.IndexAllDistributorAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(distributorService.IndexAllDistributorDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@GetMapping("/export")
	public ResponseEntity<?> exportDistributor() {
		
		List<ExportDistributor> dto = distributorService.ExportDistributor();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
	@GetMapping("/getAll1")
	public ResponseEntity<?> distributorgetAllbyJdbcTemp() {
		
		List<Distributor> dto = distributorService.getAllDistByJdbcTemp();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
	@PutMapping("/updateretailerlocation/{id}/{longt}/{lat}")
	public ResponseEntity<?> updateretLocation(@PathVariable("id")int id,@PathVariable("longt")Double longt,@PathVariable("lat")Double lat)
	{
		return new ResponseEntity<>(distributorService.updateRetailerLocation(id, lat, longt), HttpStatus.OK);
	}
	
	
	
	@PutMapping("/updatedistributorlocation/{id}/{longt}/{lat}")
	public ResponseEntity<?> updateDistributor(@PathVariable("id")int id,@PathVariable("longt")Double longt,@PathVariable("lat")Double lat)
	{
		return new ResponseEntity<>(distributorService.updateDistributorLocation(id, lat, longt), HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/fetchlocation/{pincode}")
	public ResponseEntity<?> distributorsBypincode(@PathVariable String pincode){
		return new ResponseEntity<>(distributorService.findByPincode(pincode),HttpStatus.OK);
	}
	
	
	@GetMapping("/fetch")
	public ResponseEntity<?> show() {
	
		List<DistDto> fetchDistributor = distributorService.FetchDistributor();
		return new ResponseEntity<>(fetchDistributor, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/distributortostaff/{id}")
	public ResponseEntity<?> getDistributorTostaffBYDistId(@PathVariable("id") int id) {
		List<DistributorToStaffDto> distributorTostaff = distributorService.getDistributorTostaff(id);

		return new ResponseEntity<>(distributorTostaff, HttpStatus.OK);
	}
	
	
	

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

			return new ResponseEntity<>(distributorService.findDistributorWhereGroupnIsAsc(pageno, pagesize, field, search1,search2,search3,search4,search5,count),HttpStatus.OK);
			

		} else if ("desc".equals(sortby)) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++desc+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			return new ResponseEntity<>(distributorService.findDistributorWhereGroupnIsDesc(pageno, pagesize, field, search1,search2,search3,search4,search5,count),HttpStatus.OK);

		} else {
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
