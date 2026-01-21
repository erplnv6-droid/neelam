package com.SCM.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexVoucherMaster;
import com.SCM.IndexDto.TranporterProjection;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.VoucherMasterService;

@Service
public class VoucherMasterServiceImpl implements VoucherMasterService {

	@Autowired
	private VoucherMasterRepo voucherMasterRepo;
	
	
	@Override
	public VoucherMaster save(VoucherMaster master) {
		
		master.setStatus("Active");

	
		VoucherMaster save = voucherMasterRepo.save(master);
		
voucherMasterRepo.updatePreviousPoToInactive();
		
return save;
		}
		
	


	@Override
	public List<VoucherMaster> get() {
		
		List<VoucherMaster> all = voucherMasterRepo.getAll();
		return all;
	}


	@Override
	public Map<String, Object> indexVoucherAsc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = voucherMasterRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexVoucherMaster> ipo = voucherMasterRepo.getIndexVoucherMaster(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> indexVoucherDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = voucherMasterRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexVoucherMaster> ipo = voucherMasterRepo.getIndexVoucherMaster(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> searchByVouchers(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexVoucherMaster> receipt = voucherMasterRepo.getIndexVoucherMasterSearch(search, p);

		int size = receipt.size();

		response.put("data", receipt);

		response.put("SearchCount", size);

		return response;
	}


	@Override
	public Optional<VoucherMaster> getById(int id) {
		// TODO Auto-generated method st
		
		Optional<VoucherMaster> findById = voucherMasterRepo.findById(id);
		
		return findById;
	}


	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
		voucherMasterRepo.deleteById(id);
		
	}


	@Override
	public List<VoucherMaster> getSdn() {
		List<VoucherMaster> all = voucherMasterRepo.getAllSdn();
		return all;
	}


	@Override
	public List<VoucherMaster> getMrn() {
		List<VoucherMaster> all = voucherMasterRepo.getAllMrn();
		return all;
	}


	@Override
	public List<VoucherMaster> getSo() {
		List<VoucherMaster> all = voucherMasterRepo.getAllSo();
		return all;
	}


	@Override
	public List<VoucherMaster> getPurchase() {
		List<VoucherMaster> all = voucherMasterRepo.getAllPurchase();
		return all;
	}


	@Override
	public List<VoucherMaster> getPr() {
		List<VoucherMaster> all = voucherMasterRepo.getAllPr();
		return all;
	}


	@Override
	public List<VoucherMaster> getDc() {
		List<VoucherMaster> all = voucherMasterRepo.getAllDc();
		return all;
	}


	@Override
	public List<VoucherMaster> getSale() {
		List<VoucherMaster> all = voucherMasterRepo.getAllSale();
		return all;
	}


	@Override
	public List<VoucherMaster> getSr() {
		List<VoucherMaster> all = voucherMasterRepo.getAllSr();
		return all;
	}


	@Override
	public List<VoucherMaster> getDo() {
		List<VoucherMaster> all = voucherMasterRepo.getAllDo();
		return all;
	}


	@Override
	public List<VoucherMaster> getDi() {
		List<VoucherMaster> all = voucherMasterRepo.getAllDi();
		return all;
	}

}
