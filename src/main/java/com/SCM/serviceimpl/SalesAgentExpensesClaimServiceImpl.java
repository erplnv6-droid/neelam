package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexSalesAgendExpenseClaim;
import com.SCM.IndexDto.IndexSalesAgentExpenseClaim;
import com.SCM.IndexDto.IndexSalesAgentExpenseClaimsByStaffId;
import com.SCM.dto.SalesAgentExpenseClaimDto;
import com.SCM.mapper.SalesAgentExpenseClaimMapper;
import com.SCM.model.SalesAgentExpenseClaim;
import com.SCM.repository.SalesAgentExpensesClaimRepository;
import com.SCM.service.SalesAgentExpensesClaimService;


@Service
public class SalesAgentExpensesClaimServiceImpl implements SalesAgentExpensesClaimService {

	@Autowired
	private SalesAgentExpensesClaimRepository repository;
	@Autowired
	private SalesAgentExpenseClaimMapper mapper;

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	private final Path fileStorageLocation;

	@Autowired
	public SalesAgentExpensesClaimServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir4", "/SalesAgent/")).toAbsolutePath()
				.normalize();
		Files.createDirectories(this.fileStorageLocation);
	}

	@Override
	public SalesAgentExpenseClaimDto saveAgentExpenseClaimDto(SalesAgentExpenseClaimDto agentExpenseClaimDto,MultipartFile file) throws IOException {

		SalesAgentExpenseClaim entity = mapper.toEntity(agentExpenseClaimDto);

//	folder path 
//		String path = "C:\\Users\\LNV 106\\Desktop\\projects2024\\productImage\\postInThis";
		String name = file.getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String filename = randomId.concat(name.substring(name.lastIndexOf(".")));
//		String filepath = path + File.separator + filename;

		entity.setFilepath("SalesAgent/" + filename);
		entity.setImagename(filename);
		LocalTime localTime = LocalTime.now();
		entity.setCreatedTime(localTime);
		entity.setTime(localTime);
		Date date = new Date(System.currentTimeMillis());
		entity.setCreatedOnDate(date);
		entity.setClaimexpense(agentExpenseClaimDto.getClaimexpense());
		entity.setApprovedexpense(agentExpenseClaimDto.getApprovedexpense());
		entity.setStatus("pending");
		
		

		try {
			Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {

			e.printStackTrace();
		}
		SalesAgentExpenseClaim saveEntity = repository.save(entity);

		return mapper.toDto(saveEntity);

	}

	@Override
	public Optional<SalesAgentExpenseClaimDto> getById(long id) {

		Optional<SalesAgentExpenseClaim> sales = repository.findById(id);
		Optional<SalesAgentExpenseClaimDto> map = sales.map(mapper::toDto);
		return map;
	}

	
	@Override
	public List<SalesAgentExpenseClaimDto> getAll() {

		List<SalesAgentExpenseClaim> salesAgentExpenseClaims = repository.findAll();

		return salesAgentExpenseClaims.stream().map(mapper::toDto).collect(Collectors.toList());
	}
//
//	@Override
//	public String updateSalesAgentExpensesClaimDto(long id, SalesAgentExpenseClaimDto salesAgentExpenseClaimDto) throws IOException {
//
//		SalesAgentExpenseClaim salesAgentExpenseClaim = repository.findById(id).get();
//
////		Path filetodeletepath = Paths.get(FILE_PATH + salesAgentExpenseClaim.getFilepath());
//
//		if (repository.existsById(id)) {
//
//			SalesAgentExpenseClaim entity = mapper.toEntity(salesAgentExpenseClaimDto);
//			
//			System.out.println(entity);
//
////			if (filetodeletepath != null) {
////				Files.delete(filetodeletepath);
////			}
//
//			entity.setId(id);
//			
////	folder path 
//
////			String name = file.getOriginalFilename();
////			String randomId = UUID.randomUUID().toString();
////			String filename = randomId.concat(name.substring(name.lastIndexOf(".")));
//
////			LocalTime localTime = LocalTime.now();
////			entity.setTime(localTime);
////			entity.setFilepath("SalesAgent/" + file.getOriginalFilename());
////			entity.setImagename(filename);
////			entity.setClaimexpense(salesAgentExpenseClaimDto.getClaimexpense());
////			entity.setApprovedexpense(salesAgentExpenseClaimDto.getApprovedexpense());
////			
////			try {
////				Files.copy(file.getInputStream(), this.fileStorageLocation.resolve(filename),
////						StandardCopyOption.REPLACE_EXISTING);
////
////			} catch (IOException e) {
////
////				e.printStackTrace();
////			}
////			
//			SalesAgentExpenseClaim saveEntity = repository.save(entity);
//
//			return "Succesfully updated";
//		}
//		return "id not present with id " + id;
//	}
	
	@Override
	public String updateSalesAgentExpensesClaimDto(long id, SalesAgentExpenseClaimDto salesAgentExpenseClaimDto) throws IOException {

		SalesAgentExpenseClaim salesAgentExpenseClaim = repository.findById(id).get();


	if (salesAgentExpenseClaimDto.getApprovedexpense() != 0) {

			salesAgentExpenseClaim.setApprovedexpense(salesAgentExpenseClaimDto.getApprovedexpense());
			salesAgentExpenseClaim.setStatus("approved");

			 repository.save(salesAgentExpenseClaim);

			return "Succesfully updated";
		}
		return "id not present with id " + id;
	}

// paginatino in asc and desc order

	@Override
	public Map<String, Object> ascSalesAgentExpense(int pageno, int pagesize, String field, String sort) {
		// TODO Auto-generated method stub

		Map<String, Object> response = new HashMap<>();

		Sort sorted;
		if (sort.equals("desc")) {
			sorted = Sort.by(Sort.Direction.DESC, field);
		} else {
			sorted = Sort.by(Sort.Direction.ASC, field);
		}
		Pageable p = PageRequest.of(pageno, pagesize, sorted);
		long countpages = repository.count();
		long pages = countpages / pagesize;

		List<IndexSalesAgentExpenseClaim> agentExpenseClaims = repository.indexSalesAgentExpenseClaims(p);
		response.put("Index", agentExpenseClaims);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		return response;
	}

	@Override
	public Map<String, Object> searchSalesAgentExpense(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);
		List<IndexSalesAgentExpenseClaim> agentExpenseClaims = repository.indexSalesAgentExpenseClaimsSearch(search, p);
		long searchcount = agentExpenseClaims.size();

		response.put("data", agentExpenseClaims);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public String deleteSalesAgentExpensesClaimDto(long id) {
		
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return "succesfully delete the salesAgentExpenses with id " + id;
		}
		return "id not present with id " + id;
	}

	@Override
	public String updateStatus(long id, String status) {

		if (repository.existsById(id)) {
			SalesAgentExpenseClaim agentExpenseClaim = repository.findById(id).get();
			SalesAgentExpenseClaim agentExpenseClaim2 = agentExpenseClaim;
			agentExpenseClaim.setStatus(status);
			repository.save(agentExpenseClaim2);
			return "succesfully delete the salesAgentExpenses with id " + id;
		}
		return "id not present with id " + id;
	}


	@Override
	public Map<String, Object> ascSalesAgentExpenseClaims(long staffid, int pageno, int pagesize, String field,
			String sort) {
		Map<String, Object> report = new HashMap<>();
		Sort sorted;
		
		if(sort.equals("desc")) {
			sorted = Sort.by(Sort.Direction.DESC, field);
		}else {
			sorted =  Sort.by(Sort.Direction.ASC, field);
		}
		Pageable pageable = PageRequest.of(pageno, pagesize, sorted);
		long totalItems=repository.findAllSalesAgentClaim(staffid).size();
		long pagesCount = (long) Math.abs(totalItems / pagesize);
		long remainder = totalItems % pagesize;
		if(remainder> 0) {
			pagesCount ++;
		}
		List<IndexSalesAgendExpenseClaim> data=repository.findAllSalesAgentClaimByPagination(staffid, pageable);
		report.put("Index", data);
		report.put("Pages", pagesCount);
		report.put("Enteries", totalItems);
		
		
		
		// TODO Auto-generated method stub
		return report;
	}


	@Override
	public Map<String, Object> searchSalesAgentExpenseClaims(long staffid, int pageno, int pagesize, String search) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexSalesAgendExpenseClaim> salesagentsearch=repository.searchAllSalesAgentClaimByPagination(staffid, search, p);
		long searchcount = salesagentsearch.size();
		
		response.put("data", salesagentsearch);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public Map<String, Object> ascSalesAgenetExpenseByStaffId(long id, String startDate, String endDate, int pageno,int pagesize, String field) {

		Map<String , Object> response=new HashMap<>();
		
		Sort sort=Sort.by(Sort.Direction.ASC,field);
		Pageable p=PageRequest.of(pageno, pagesize,sort);
		
		long countpages=repository.getsalesAgentExpenseByStaffid(id, startDate, endDate).size();
		long pages=countpages/pagesize;
		List<IndexSalesAgentExpenseClaimsByStaffId> sc=repository.getsalesAgentExpenseByStaffid(id, startDate, endDate, p);
		response.put("Index", sc);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		return response;
	}

	

	@Override
	public Map<String, Object> descSalesAgenetExpenseByStaffId(long id, String startDate, String endDate, int pageno,
			int pagesize, String field) {
	
		Map<String , Object> response=new HashMap<>();
		Sort sort=Sort.by(Sort.Direction.DESC,field);
		Pageable p=PageRequest.of(pageno, pagesize,sort);
		long countpages=repository.getsalesAgentExpenseByStaffid(id, startDate, endDate).size();
		long pages=countpages/pagesize;
		List<IndexSalesAgentExpenseClaimsByStaffId> sc=repository.getsalesAgentExpenseByStaffid(id, startDate, endDate, p);
		response.put("Index", sc);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		return response;
	}

	@Override
	public Map<String, Object> searchSalesAgenetExpenseByStaffId(long id, String startDate, String endDate, int pageno,
			int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);
		List<IndexSalesAgentExpenseClaimsByStaffId> sc=repository.searchsalesAgentExpenseByStaffid(id, startDate, endDate, search,p);
		long searchcount = sc.size();

		
		response.put("data", sc);
		response.put("SearchCount", searchcount);

		return response;
	}

}
