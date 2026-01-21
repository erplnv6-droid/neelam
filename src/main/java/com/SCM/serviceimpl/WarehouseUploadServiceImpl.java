package com.SCM.serviceimpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.Helper.ExcelUploadByLoop;
import com.SCM.IndexDto.PreIndex;
import com.SCM.model.WarehouseExcelUpload;
import com.SCM.model.WarehouseUpload;
import com.SCM.repository.WarehouseExcelUploadRepo;
import com.SCM.repository.WarehouseUploadRepo;
import com.SCM.service.WarehouseUploadService;

@Service
public class WarehouseUploadServiceImpl implements WarehouseUploadService {

	@Autowired
	private ExcelUploadByLoop excelUploadByLoop;

	@Autowired
	private WarehouseUploadRepo warehouseUploadRepo;

	@Autowired
	private WarehouseExcelUploadRepo excelUploadRepo2;

	public void savePrimary(WarehouseUpload warehouseUpload, MultipartFile file) throws IOException {
//		try {
//			Optional<WarehouseUpload> wwh = warehouseUploadRepo.findById(warehouseUpload.getWarehouse().getId());
//
//            Optional<WarehouseUpload> byId = warehouseUploadRepo.findById(wwh.get().getWarehouse().getId());
//
//			if (!byId.isPresent()) {
//				System.out.println("+++++++++++++++");
//				List<WarehouseExcelUpload> convertExcelToListOfSecondary = excelUploadByLoop.convertExcelUpload(file.getInputStream());
//				warehouseUpload.setExcelUploads(convertExcelToListOfSecondary);
//				warehouseUpload.setPdate(warehouseUpload.getPdate());
//			}
//			if (wwh.isPresent()) {
//
//				WarehouseUpload wh = wwh.get();
//				int n = wh.getWarehouse().getId();
//				warehouseUploadRepo.deleteExcelUploadByWarehouseId(n);
//
//				List<WarehouseExcelUpload> convertExcelToListOfSecondary = excelUploadByLoop.convertExcelUpload(file.getInputStream());
//				warehouseUpload.setExcelUploads(convertExcelToListOfSecondary);
//				warehouseUpload.setPdate(warehouseUpload.getPdate());
//			}
//			warehouseUploadRepo.save(warehouseUpload);
//		} 
//		catch (IOException e) {
//
//			e.printStackTrace();
//		}
	}

	
	@Override
	public void savePrimary1(WarehouseUpload warehouseUpload, MultipartFile file) throws IOException {

		Optional<WarehouseUpload> byId = warehouseUploadRepo.findById(warehouseUpload.getWarehouse().getId());

		boolean existsByWarehouseId = warehouseUploadRepo.existsByWarehouseId(warehouseUpload.getWarehouse().getId());

		if (!byId.isPresent()) {

			if (!existsByWarehouseId) {
				
				List<WarehouseExcelUpload> convertExcelToListOfSecondary = excelUploadByLoop.convertExcelUpload(file.getInputStream());
				warehouseUpload.setExcelUploads(convertExcelToListOfSecondary);
				warehouseUpload.setPdate(warehouseUpload.getPdate());
			}
		}
		if (existsByWarehouseId) {
			
			int existid = warehouseUpload.getWarehouse().getId();
			warehouseUploadRepo.deleteExcelUploadByWarehouseId(existid);

			List<WarehouseExcelUpload> convertExcelToListOfSecondary = excelUploadByLoop.convertExcelUpload(file.getInputStream());
			warehouseUpload.setExcelUploads(convertExcelToListOfSecondary);
			warehouseUpload.setPdate(warehouseUpload.getPdate());
		}

		warehouseUploadRepo.save(warehouseUpload);
	}

	@Override
	public Map<String, Object> IndexWarehouseUploadAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = excelUploadRepo2.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<PreIndex> ipo = warehouseUploadRepo.IndexPrimary(p);

		System.out.println(ipo);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexWarehouseUploadDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = excelUploadRepo2.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<PreIndex> ipo = warehouseUploadRepo.IndexPrimary(p);

		System.out.println(ipo);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchWarehouseUpload(int pageno, int pagesize, String search) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<PreIndex> searchPrimary = warehouseUploadRepo.SearchByPrimary(search, p);

		long searchcount = searchPrimary.size();

		response.put("data", searchPrimary);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public List<WarehouseUpload> showWarehouseUpload() {
		List<WarehouseUpload> all = warehouseUploadRepo.findAll();
		return all;
	}

	@Override
	public Map<String, Object> IndexWarehouseUploadAscByWarehouseId(int pagno, int pagesize, String field, int wid) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = excelUploadRepo2.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<PreIndex> ipo = warehouseUploadRepo.IndexExcelByWarehouseIdandDate(p, wid);
		String getpdate = ipo.get(0).getpdate();

		List<WarehouseUpload> fetchLastModifieddate = warehouseUploadRepo.fetchLastModifieddate();
		LocalDate pdate = fetchLastModifieddate.get(0).getPdate();

		System.out.println(ipo);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		response.put("lastmodifieddate", pdate);

		return response;
	}

	@Override
	public Map<String, Object> IndexWarehouseUploadDescByWarehouseId(int pagno, int pagesize, String field, int wid) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = excelUploadRepo2.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<PreIndex> ipo = warehouseUploadRepo.IndexExcelByWarehouseIdandDate(p, wid);

		String getpdate = ipo.get(0).getpdate();

		List<WarehouseUpload> fetchLastModifieddate = warehouseUploadRepo.fetchLastModifieddate();
		LocalDate pdate = fetchLastModifieddate.get(0).getPdate();

		System.out.println(ipo);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		response.put("lastmodifieddate", pdate);

		return response;
	}

	@Override
	public Map<String, Object> SearchWarehouseUploadDescByWarehouseId(int pageno,int pagesize,String search,int wid) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<PreIndex> searchPrimary = warehouseUploadRepo.SearchByExcelByWarehouseIdandDate(search,p, wid);

		long searchcount = searchPrimary.size();

		response.put("data", searchPrimary);
		response.put("SearchCount", searchcount);

		return response;
	}
}
