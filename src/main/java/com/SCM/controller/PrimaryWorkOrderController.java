package com.SCM.controller;

import com.SCM.ExportDto.ExportPrimaryWorkOrder;
import com.SCM.config.UserId;
import com.SCM.dto.PrimaryOrderDto;
import com.SCM.model.PrimaryOrder;
import com.SCM.service.PrimaryOrderService;
import com.google.firebase.messaging.FirebaseMessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/primaryworkorder")
public class PrimaryWorkOrderController {

	@Autowired
	private PrimaryOrderService primaryOrderService;

	@Autowired
	private UserId user;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			System.out.println("success");
		}
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			
			return new ResponseEntity<>(primaryOrderService.getAllPo1(), HttpStatus.OK);
			
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_NSM"))) {

			return new ResponseEntity<>(primaryOrderService.getAllPo1(), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"))) {

			return new ResponseEntity<>(primaryOrderService.getPrimaryOrderByRSM(user.getId().intValue()),HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"))) {

			return new ResponseEntity<>(primaryOrderService.getPrimaryOrderByASM(user.getId().intValue()),HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"))) {

			return new ResponseEntity<>(primaryOrderService.getPrimaryOrderByASE(user.getId().intValue()),HttpStatus.OK);

		} else if (authentication.getAuthorities().stream()
				
				.anyMatch(a -> a.getAuthority().equals("ROLE_DISTRIBUTOR"))) {

			return new ResponseEntity<>(primaryOrderService.getPrimaryOrderByDistributor(user.getId().intValue()),
					HttpStatus.OK);
		} else {

			return new ResponseEntity<>("Primary Order  Not!!!! Found", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> savePrimaryOrder(@RequestBody PrimaryOrderDto primaryOrderDto) throws FirebaseMessagingException {
		
		return new ResponseEntity<>(primaryOrderService.savePrimaryWorkOder(primaryOrderDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public PrimaryOrderDto update(@RequestBody PrimaryOrderDto primaryOrderDto, @PathVariable int id) {

		return primaryOrderService.updatePrimaryOrder(primaryOrderDto, id);
	}

	@GetMapping("/getById/{id}")
	public PrimaryOrderDto GetById(@PathVariable int id) {

		return primaryOrderService.getPriamryWorkOderById(id);
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable int id) {

		return primaryOrderService.deletePrimaryWorkOder(id);
	}

	
	// -------------- fetch by ase asm rsm nsm 
	

	@GetMapping("ase/{aseId}")
	public ResponseEntity<List<PrimaryOrderDto>> getPrimaryOrderByAseId(@PathVariable("aseId") int aseId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderByASE(aseId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@GetMapping("asm/{asmId}")
	public ResponseEntity<List<PrimaryOrderDto>> getPrimaryOrderByAsmId(@PathVariable("asmId") int asmId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderByASM(asmId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@GetMapping("rsm/{rsmId}")
	public ResponseEntity<List<PrimaryOrderDto>> getPrimaryOrderByRsmId(@PathVariable("rsmId") int rsmId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderByRSM(rsmId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@GetMapping("nsm/{nsmId}")
	public ResponseEntity<List<PrimaryOrderDto>> getPrimaryOrderByNsmId(@PathVariable("nsmId") int nsmId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderByNSM(nsmId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@GetMapping("zone/{zonesId}")
	public ResponseEntity<List<PrimaryOrderDto>> getPrimaryOrderByZoneId(@PathVariable("zonesId") int zonesId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderByZONE(zonesId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@GetMapping("state/{stateId}")
	public ResponseEntity<List<PrimaryOrderDto>> getPrimaryOrderByStateId(@PathVariable("stateId") int stateId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderBySTATE(stateId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@GetMapping("dist/{distId}")
	public ResponseEntity<List<PrimaryOrderDto>> getWorkOrderByDistributorId(@PathVariable("distId") int distId) {
		List<PrimaryOrderDto> pdto = primaryOrderService.getPrimaryOrderByDistributor(distId);

		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}
	
	
	
	//-----index priamry order
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexPrimaryOrder(@PathVariable("pageno") int pageno,
			                                   @PathVariable("pagesize") int pagesize,
			                                   @PathVariable("sortby") String sortby,
			                                   @PathVariable("field") String field,
			                                   @PathVariable("search")String search) 
	{
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(primaryOrderService.SearchPrimaryOrder(pageno, pagesize, search),HttpStatus.OK);
		}
	    else if("asc".equals(sortby))
		{
			return new ResponseEntity<>(primaryOrderService.IndexPrimaryOrderAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(primaryOrderService.IndexPrimaryOrderDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	   @GetMapping("/export")
	    public ResponseEntity<?> exportPrimaryOrder()
	    {
	        List<ExportPrimaryWorkOrder> dto = primaryOrderService.exportPo();

	        return new ResponseEntity<>(dto, HttpStatus.OK) ;
	    }
	   
	   
	   
	   

	   
	   @GetMapping("page/primary/{pageno}/{pagesize}/{sortby}/{field}/{startdate}/{enddate}/{distid}/{search}")
		public ResponseEntity<?> IndexPrimaryOrderItemsByDistributorId(@PathVariable("pageno") int pageno,
				                                   @PathVariable("pagesize") int pagesize,
				                                   @PathVariable("sortby") String sortby,
				                                   @PathVariable("field") String field,
				                                   @PathVariable("search")String search,
											        @PathVariable("startdate")String startdate,
											        @PathVariable("enddate")String enddate,
											        @PathVariable("distid")int distid
											        )

		{
			if(!search.equals(" "))
			{
				return new ResponseEntity<>(primaryOrderService.SearchPrimaryOrderItemsDesc(startdate, enddate, distid, pageno, pagesize, search), HttpStatus.OK);
}
		     if("asc".equals(sortby))
			{
				return new ResponseEntity<>(primaryOrderService.IndexPrimaryOrderItemsAsc(startdate, enddate, distid, pageno, pagesize, field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(primaryOrderService.IndexPrimaryOrderItemsDesc(startdate, enddate, distid, pageno, pagesize, field), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	   
	   
	   
//	   @Autowired
//	   private PrimaryOrderItemsRepository itemsRepository;
//	   
//	   @GetMapping("/podistid")
//	   List<IndexPrimaryOrderByDistributorId> getAd(){
//		   return itemsRepository.getAllPoByDistId();
//	   }

	   @GetMapping("/po")
		public List<PrimaryOrder> po() {

			String sql = "CALL PrimaryOrdergetAll()";
			List<PrimaryOrder> po = jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<>(PrimaryOrder.class));
			return po;
		}
}
