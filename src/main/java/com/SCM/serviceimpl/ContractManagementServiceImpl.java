package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexContractManagement;
import com.SCM.IndexDto.IndexPurchaseReutrn;
import com.SCM.dto.ContractManagementDto;
import com.SCM.dtoReports.ContractMgmtReport;
import com.SCM.model.ContractManagement;
import com.SCM.repository.ContractManagementRepo;
import com.SCM.service.ContractManagementService;

@Service
public class ContractManagementServiceImpl implements ContractManagementService {

	@Autowired
	private ContractManagementRepo contractManagementRepo;

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	private final Path fileStorageLocation;

	public ContractManagementServiceImpl(Environment env) throws IOException {
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir6", "/ContractManagementDoc/")).toAbsolutePath().normalize();
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

	private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
	
	
	@Override
	public ContractManagement saveCM(ContractManagementDto contractManagementDto, MultipartFile pdffile) throws IOException {
			
		ContractManagement cm = new ContractManagement();
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		cm.setRemarks(contractManagementDto.getRemarks());
		cm.setTaxableamount(contractManagementDto.getTaxableamount());
		cm.setTaxamount(contractManagementDto.getTaxamount());
		cm.setTotalamount(contractManagementDto.getTotalamount());
		cm.setContracttype(contractManagementDto.getContracttype());
		cm.setSupplier(contractManagementDto.getSupplier());
		cm.setCreateddate(LocalDate.now());
		cm.setCreatedtime(LocalTime.now());
		cm.setFromdate(contractManagementDto.getFromdate());
		cm.setTodate(contractManagementDto.getTodate());
		cm.setCreatedby(uid);
		cm.setCreatebyname(uname);
		cm.setRole(role);

		ContractManagement save = contractManagementRepo.save(cm);
		
		if(pdffile.getSize() > MAX_FILE_SIZE)
		{
			throw new RuntimeException("The file size 2MB execced to limit");
		}

		String pdffolderpath = "doc_" + save.getId() + pdffile.getOriginalFilename();
		
		if (pdffile.isEmpty()) {

			cm.setDocname(null);
			cm.setDoclocation(null);

			contractManagementRepo.save(cm);
		}
		if (!pdffile.isEmpty()) {
			
			Files.copy(pdffile.getInputStream(), fileStorageLocation.resolve(pdffolderpath),StandardCopyOption.REPLACE_EXISTING);

			cm.setDocname(pdffile.getOriginalFilename());
			cm.setDoclocation("ContractManagementDoc/" + pdffolderpath);

			contractManagementRepo.save(cm);
		}

		return save;
	}

	@Override
	public List<ContractManagement> getAllCM() {

		return contractManagementRepo.findAll();
	}

	@Override
	public ContractManagement getCMById(int id) {
		ContractManagement contractManagement = contractManagementRepo.findById(id).get();
		return contractManagement;
	}

	@Override
	public String deleteCM(int id) {
		contractManagementRepo.deleteById(id);
		return "delete";
	}

	@Override
	public ContractManagement updateCM(ContractManagementDto contractManagementDto, MultipartFile pdffile, int id)
			throws IOException {

		String pdffolderpath = "doc_" + contractManagementDto.getId() + pdffile.getOriginalFilename();

		ContractManagement cm = contractManagementRepo.findById(id).get();
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		cm.setRemarks(contractManagementDto.getRemarks());
		cm.setTaxableamount(contractManagementDto.getTaxableamount());
		cm.setTaxamount(contractManagementDto.getTaxamount());
		cm.setTotalamount(contractManagementDto.getTotalamount());
		cm.setContracttype(contractManagementDto.getContracttype());
		cm.setSupplier(contractManagementDto.getSupplier());
		cm.setFromdate(contractManagementDto.getFromdate());
		cm.setTodate(contractManagementDto.getTodate());
		cm.setUpdatedby(uid);
		cm.setUpdatedbyname(uname);
		cm.setUpdatedrole(role);
		cm.setUpdateddate(LocalDate.now());
		cm.setUpdatedtime(LocalTime.now());

		ContractManagement save = contractManagementRepo.save(cm);

		if (pdffile.isEmpty()) {

			cm.setDocname(cm.getDocname());
			cm.setDoclocation(cm.getDoclocation());

			contractManagementRepo.save(cm);
		}
		if (!pdffile.isEmpty()) {

			try {

				Path docpath = Paths.get(FILE_PATH + save.getDoclocation());
				Files.delete(docpath);

			} catch (Exception e) {
				e.printStackTrace();
			}

			cm.setDocname(pdffile.getOriginalFilename());
			cm.setDoclocation("ContractManagementDoc/" + pdffolderpath);

			Files.copy(pdffile.getInputStream(), fileStorageLocation.resolve(pdffolderpath),StandardCopyOption.REPLACE_EXISTING);

			contractManagementRepo.save(cm);
		}
		return save;
	}

	
	@Override
	public Map<String, Object> IndexContractManagementASC(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {

				long countpages = contractManagementRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexContractManagement> ipo = contractManagementRepo.IndexContractManagement(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages =contractManagementRepo.IndexContractManagementSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexContractManagement> ipo = contractManagementRepo.IndexContractManagementSupplier(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
	
		}

		return null;
	}
    
	
	@Override
	public Map<String, Object> IndexContractManagementDESC(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {

				long countpages = contractManagementRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexContractManagement> ipo = contractManagementRepo.IndexContractManagement(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages =contractManagementRepo.IndexContractManagementSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexContractManagement> ipo = contractManagementRepo.IndexContractManagementSupplier(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;
	}

	
	@Override
	public Map<String, Object> SearchContractManagement(int pageno, int pagesize, String search) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {
				System.out.println("admin");
				List<IndexContractManagement> orders = contractManagementRepo.SearchByContractManagement(search, p);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				List<IndexContractManagement> orders = contractManagementRepo.SearchByContractManagement(search, p, sid);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			}
		


	}
		return null;
	}

	
	
	@Override
	public List<com.SCM.dtoReports.ContractMgmtReport> ContractMgmtReport() {
		
		List<ContractMgmtReport> contractManagementReport = contractManagementRepo.ContractManagementReport();
		
		return contractManagementReport;
	}

	
	@Override
	public Map<String, Object> reportContractMgmtAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_DISTRIBUTOR") || s.equals("ROLE_NSM") || s.equals("ROLE_RSM")
					|| s.equals("ROLE_ASM") || s.equals("ROLE_ASE")) {

				long countpages = contractManagementRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<ContractMgmtReport> ipo = contractManagementRepo.ContractManagementReport(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
		
}

	
	@Override
	public Map<String, Object> reportContractmgmtDesc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_DISTRIBUTOR") || s.equals("ROLE_NSM") || s.equals("ROLE_RSM")
					|| s.equals("ROLE_ASM") || s.equals("ROLE_ASE")) {

				long countpages = contractManagementRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<ContractMgmtReport> ipo = contractManagementRepo.ContractManagementReport(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}
    
	
	@Override
	public Map<String, Object> searchReportByContractMgmt(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_DISTRIBUTOR") || s.equals("ROLE_NSM") || s.equals("ROLE_RSM")
					|| s.equals("ROLE_ASM") || s.equals("ROLE_ASE")) {


		List<com.SCM.dtoReports.ContractMgmtReport> searchContractManagementReport = contractManagementRepo.SearchContractManagementReport(p, search);

		int size = searchContractManagementReport.size();

		response.put("data", searchContractManagementReport);
		response.put("SearchCount", size);

		return response;
			}
		}
			return null;
	
	}
}
