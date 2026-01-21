 package com.SCM.serviceimpl;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexMinutesOfMeeting;
import com.SCM.IndexDto.IndexPurchaseReutrn;
import com.SCM.dto.MinutesOfMeetingDto;
import com.SCM.model.Distributor;
import com.SCM.model.MinutesOfMeeting;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;
import com.SCM.model.Supplier;
import com.SCM.repository.MinutesOfMeetingRepo;
import com.SCM.service.MinutesOfMeetingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MinutesOfMeetingServiceImpl implements MinutesOfMeetingService {

	@Autowired
	private MinutesOfMeetingRepo minutesOfMeetingRepo;

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	@Autowired
	ObjectMapper mapper;

//	private final static String FOLDER_PATH = "D:/img/";

	private final Path fileStorageLocation;

	public MinutesOfMeetingServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir7", "/MinutesOfmeeting/"))
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
	public MinutesOfMeeting save(MinutesOfMeetingDto minutesOfMeetingDto, String staffjson, String retailerjson,
			String distributorjson,String supplierjson,MultipartFile docfile) throws IOException {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		MinutesOfMeeting meeting = new MinutesOfMeeting();
		meeting.setMeetingtitle(minutesOfMeetingDto.getMeetingtitle());
		meeting.setMeetingdate(minutesOfMeetingDto.getMeetingdate());
		meeting.setStaff(minutesOfMeetingDto.getStaff());
		meeting.setCreateddate(LocalDate.now());
		meeting.setCreatedtime(LocalTime.now());
		meeting.setCreatebyname(uname);
		meeting.setCreatedby(uid);
		meeting.setRole(role);
		meeting.setDescription(minutesOfMeetingDto.getDescription());

		List<Staff> items = mapper.readValue(staffjson, new TypeReference<List<Staff>>() {});
		
		List<Retailer> retaileritems = mapper.readValue(retailerjson, new TypeReference<List<Retailer>>() {});
		
		List<Distributor> distributoritems = mapper.readValue(distributorjson, new TypeReference<List<Distributor>>() {});
		
		List<Supplier> supplieritems = mapper.readValue(supplierjson, new TypeReference<List<Supplier>>() {});
		
		meeting.setStaff(items);
		meeting.setRetailer(retaileritems);
		meeting.setDistributor(distributoritems);
		meeting.setSuppliers(supplieritems);

		MinutesOfMeeting savedMeeting = minutesOfMeetingRepo.save(meeting);

		String docFolder = "doc_" + savedMeeting.getId() + docfile.getOriginalFilename();
//	    String docFolderPath = FOLDER_PATH + docFolder;

		if (docfile.isEmpty()) {

			meeting.setDocname(null);
			meeting.setDoclocation(null);

		} else if (!docfile.isEmpty()) {

			Files.copy(docfile.getInputStream(), fileStorageLocation.resolve(docFolder),
					StandardCopyOption.REPLACE_EXISTING);

			meeting.setDocname(docfile.getOriginalFilename());
			meeting.setDoclocation("MinutesOfmeeting/" + docFolder);

//	        Path path = Paths.get(docFolderPath);
			minutesOfMeetingRepo.save(meeting);
		}
		return minutesOfMeetingRepo.save(meeting);
	}

	@Override
	public List<MinutesOfMeeting> getAll() {

		return minutesOfMeetingRepo.findAll();
	}

	@Override
	public MinutesOfMeeting getById(int id) {
		MinutesOfMeeting minutesOfMeeting = minutesOfMeetingRepo.findById(id).get();
		return minutesOfMeeting;
	}

	@Override
	public String delete(int id) {

		minutesOfMeetingRepo.deleteById(id);
		return "delete";
	}

	@Override
	public MinutesOfMeeting update(MinutesOfMeetingDto minutesOfMeetingDto, MultipartFile docfile, String staffjson,
			                       String retailerjson, String distributorjson,String supplierjson,int id) throws IOException {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		MinutesOfMeeting meeting = minutesOfMeetingRepo.findById(id).get();

		String docFolderPath = "doc_" + minutesOfMeetingDto.getId() + docfile.getOriginalFilename();
		
		meeting.setMeetingtitle(minutesOfMeetingDto.getMeetingtitle());
		meeting.setMeetingdate(minutesOfMeetingDto.getMeetingdate());
		meeting.setDescription(minutesOfMeetingDto.getDescription());
		meeting.setUpdatedbyname(uname);
		meeting.setUpdatedby(uid);
		meeting.setUpdatedrole(role);
		meeting.setUpdateddate(LocalDate.now());
		meeting.setUpdatedtime(LocalTime.now());

		List<Staff> items = mapper.readValue(staffjson, new TypeReference<List<Staff>>() {});
		
		List<Retailer> retaileritems = mapper.readValue(retailerjson, new TypeReference<List<Retailer>>() {});
		
		List<Distributor> distributoritems = mapper.readValue(distributorjson, new TypeReference<List<Distributor>>() {});
		
		List<Supplier> supplieritems = mapper.readValue(supplierjson, new TypeReference<List<Supplier>>() {});
		
		meeting.setStaff(items);
		meeting.setRetailer(retaileritems);
		meeting.setDistributor(distributoritems);
		meeting.setSuppliers(supplieritems);

		MinutesOfMeeting update = minutesOfMeetingRepo.save(meeting);

		if (docfile.isEmpty()) {

			meeting.setDocname(meeting.getDocname());
			meeting.setDoclocation(meeting.getDoclocation());

			minutesOfMeetingRepo.save(meeting);
		}
		if (!docfile.isEmpty()) {

			try {
				Path doctodeletepath = Paths.get(FILE_PATH + update.getDoclocation());
				Files.delete(doctodeletepath);

			} catch (Exception e) {
				e.getStackTrace();
			}

			meeting.setDocname(docfile.getOriginalFilename());
			meeting.setDoclocation("MinutesOfmeeting/" + docFolderPath);

			Files.copy(docfile.getInputStream(), fileStorageLocation.resolve(docFolderPath),StandardCopyOption.REPLACE_EXISTING);


			minutesOfMeetingRepo.save(meeting);
		}
		return update;
	}

	
	
	
	
	@Override
	public Map<String, Object> IndexMOMAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long id = userDetails.getId();
		int idd = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeeting = minutesOfMeetingRepo.IndexMiutesOfMeeting(p);

				response.put("Index", indexMiutesOfMeeting);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RETAILER")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByRetailer(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMinutesOfMeetingByDistributor = minutesOfMeetingRepo.IndexMinutesOfMeetingByDistributor(p, idd);
				response.put("Index", indexMinutesOfMeetingByDistributor);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			else if(s.equals("ROLE_SUPPLIER"))
			{
				long countpages =minutesOfMeetingRepo.IndexMinutesOfMeetingBySupplier(idd).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> ipo = minutesOfMeetingRepo.IndexMinutesOfMeetingBySupplier(p, idd);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			

			else if (s.equals("ROLE_RSM")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByStaffRSM(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByStaffASM(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASE")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByStaffASE(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			
			
		}
		return response;
	}

	@Override
	public Map<String, Object> IndexMOMDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long id = userDetails.getId();
		int idd = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeeting = minutesOfMeetingRepo.IndexMiutesOfMeeting(p);

				response.put("Index", indexMiutesOfMeeting);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RSM")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByStaffRSM(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASM")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByStaffASM(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASE")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByStaffASE(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RETAILER")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMiutesOfMeetingByRetailer = minutesOfMeetingRepo.IndexMiutesOfMeetingByRetailer(p, idd);

				response.put("Index", indexMiutesOfMeetingByRetailer);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = minutesOfMeetingRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> indexMinutesOfMeetingByDistributor = minutesOfMeetingRepo.IndexMinutesOfMeetingByDistributor(p, idd);

				response.put("Index", indexMinutesOfMeetingByDistributor);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			else if(s.equals("ROLE_SUPPLIER"))
			{
				long countpages =minutesOfMeetingRepo.IndexMinutesOfMeetingBySupplier(idd).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMinutesOfMeeting> ipo = minutesOfMeetingRepo.IndexMinutesOfMeetingBySupplier(p, idd);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return response;
	}

	
	@Override
	public Map<String, Object> SearchMOM(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {
				System.out.println("admin");
				List<IndexMinutesOfMeeting> orders = minutesOfMeetingRepo.SearchMinutesOfMeeting(search, p);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				List<IndexMinutesOfMeeting> orders = minutesOfMeetingRepo.SearchMinutesOfMeeting(search, p, sid);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			}
		


	}
		return null;

	}

	@Override
	public byte[] downloadImages(String filename) throws IOException {

		Optional<MinutesOfMeeting> imgfile = minutesOfMeetingRepo.findByDocname(filename);
		String filepath = imgfile.get().getDoclocation();
		byte[] images = Files.readAllBytes(new File(filepath).toPath());
		return images;
	}

	@Override
	public byte[] getImagebyId(int id) throws IOException {

		Optional<MinutesOfMeeting> minutesOfMeeting = minutesOfMeetingRepo.findById(id);

		String doclocation = minutesOfMeeting.get().getDoclocation();

		return Files.readAllBytes(new File(doclocation).toPath());
	}

}
