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
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexInvoiceUpload;
import com.SCM.dto.InvoiceUploadDto;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.InvoiceUpload;
import com.SCM.repository.InvoiceUploadRepo;
import com.SCM.service.InvoiceUploadService;

@Service
public class InvoiceUploadServiceImpl implements InvoiceUploadService {

	@Autowired
	private GetCurrentUserRoleName auth;

	@Autowired
	private InvoiceUploadRepo invoiceUploadRepo;

	private final Path fileStorageLocation;

	public InvoiceUploadServiceImpl(Environment env) throws IOException {
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir8", "/invoiceupload/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}
	
	private static final long MAX_FILE_SIZE = 4 * 1024 * 1024;

	private static final String FOLDER_PATH = "D:\\ImgPath/";

	@Override
	public InvoiceUpload saveInvoiceUpload(InvoiceUploadDto invoiceUploadDto, MultipartFile imgfiles,
			MultipartFile pdffiles) throws IOException {

		Long uid = auth.getUserId();
		String uname = auth.getUserName();
		String role = auth.getRolename();

		InvoiceUpload iu = new InvoiceUpload();
		iu.setAmount(invoiceUploadDto.getAmount());
		iu.setVoucherno(invoiceUploadDto.getVoucherno());
		iu.setVoucherdate(invoiceUploadDto.getVoucherdate());
		iu.setDistributor(invoiceUploadDto.getDistributor());
		iu.setCreatebyname(uname);
		iu.setCreatedby(uid);
		iu.setCreateddate(LocalDate.now());
		iu.setCreatedtime(LocalTime.now());
		iu.setRole(role);

		InvoiceUpload save = invoiceUploadRepo.save(iu);

		// --------- img

		String imgfolder = "IMG_" + save.getId() + imgfiles.getOriginalFilename();
		String pdffolder = "PDF_" + save.getId() + pdffiles.getOriginalFilename();

		if (imgfiles.isEmpty() && pdffiles.isEmpty()) {
			iu.setImgname(null);
			iu.setImglocation(null);
			iu.setPdfname(null);
			iu.setPdflocation(null);

			invoiceUploadRepo.save(iu);
		}
		if (!imgfiles.isEmpty() && pdffiles.isEmpty()) {
			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
					StandardCopyOption.REPLACE_EXISTING);

			iu.setImgname(imgfiles.getOriginalFilename());
			iu.setImglocation("invoiceupload/" + imgfolder);
			iu.setPdfname(null);
			iu.setPdflocation(null);

			invoiceUploadRepo.save(iu);
		}
		if (imgfiles.isEmpty() && !pdffiles.isEmpty()) {
			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
					StandardCopyOption.REPLACE_EXISTING);

			iu.setImgname(null);
			iu.setImglocation(null);
			iu.setPdfname(pdffiles.getOriginalFilename());
			iu.setPdflocation("invoiceupload/" + pdffolder);

			invoiceUploadRepo.save(iu);
		}
		if (!imgfiles.isEmpty() && !pdffiles.isEmpty()) {
			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
					StandardCopyOption.REPLACE_EXISTING);

			iu.setImgname(imgfiles.getOriginalFilename());
			iu.setImglocation("invoiceupload/" + imgfolder);
			iu.setPdfname(pdffiles.getOriginalFilename());
			iu.setPdflocation("invoiceupload/" + pdffolder);

			invoiceUploadRepo.save(iu);
		}

		return save;
	}

	@Override
	public List<InvoiceUpload> getAllInvoice() {

		List<InvoiceUpload> all = invoiceUploadRepo.findAll();

		return all;
	}

	@Override
	public InvoiceUpload getInvoiceById(int id) {
		InvoiceUpload invoiceUpload = invoiceUploadRepo.findById(id).get();
		return invoiceUpload;
	}

	@Override
	public String delete(int id) {
		invoiceUploadRepo.deleteById(id);
		return "delete invoice";
	}

	// ===================== index

	@Override
	public Map<String, Object> IndexInvoiceAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long distid = userDetails.getId();
		int diid = (int) (long) distid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				System.out.println("+++++++++++++++++++++++++++++++++++++++++");
				long countpages = invoiceUploadRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoice(p);

				System.out.println(ipo + "++++++++++++++++++++++++");

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByDistributor(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.IndexInvoiceByRsm(diid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByRsm(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.IndexInvoiceByAsm(diid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByAsm(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.IndexInvoiceByAse(diid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByAse(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return response;
	}

	@Override
	public Map<String, Object> IndexInvoiceDesc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long distid = userDetails.getId();
		int diid = (int) (long) distid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN")  || s.equals("ROLE_NSM")) {

				System.out.println("+++++++++++++++++++++++++++++++++++++++++");
				long countpages = invoiceUploadRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoice(p);

				System.out.println(ipo + "++++++++++++++++++++++++");

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByDistributor(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			else if (s.equals("ROLE_RSM")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.IndexInvoiceByRsm(diid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByRsm(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.IndexInvoiceByAsm(diid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByAsm(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				System.out.println("------------------------------------------------");

				long countpages = invoiceUploadRepo.IndexInvoiceByAse(diid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInvoiceUpload> ipo = invoiceUploadRepo.IndexInvoiceByAse(p, diid);

				System.out.println(ipo);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return response;
	}

	@Override
	public Map<String, Object> SearchInvoice(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN")  || s.equals("ROLE_NSM")) {

				List<IndexInvoiceUpload> searchInvoice = invoiceUploadRepo.SearchInvoice(search, p);

				long searchcount = searchInvoice.size();

				response.put("data", searchInvoice);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				List<IndexInvoiceUpload> searchInvoice = invoiceUploadRepo.SearchInvoice(search, p);

				long searchcount = searchInvoice.size();

				response.put("data", searchInvoice);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				List<IndexInvoiceUpload> searchInvoice = invoiceUploadRepo.SearchInvoiceRsm(riid,search, p);

				long searchcount = searchInvoice.size();

				response.put("data", searchInvoice);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				List<IndexInvoiceUpload> searchInvoice = invoiceUploadRepo.SearchInvoiceAsm(riid,search, p);

				long searchcount = searchInvoice.size();

				response.put("data", searchInvoice);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				List<IndexInvoiceUpload> searchInvoice = invoiceUploadRepo.SearchInvoiceAse(riid,search, p);

				long searchcount = searchInvoice.size();

				response.put("data", searchInvoice);
				response.put("SearchCount", searchcount);

				return response;
			}
		}
		return response;
	}

	
	@Override
	public List<IndexInvoiceUpload> ffffffffffff(int distid) {
		List<IndexInvoiceUpload> indexInvoiceByDistributor = invoiceUploadRepo.IndexInvoiceByDistributor(distid);
		return indexInvoiceByDistributor;
	}

}
