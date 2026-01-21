package com.SCM.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexVoucher;
import com.SCM.dto.VoucherModelDto;
import com.SCM.model.Voucher;
import com.SCM.repository.VoucherRepo;
import com.SCM.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {

	@Autowired
	private VoucherRepo voucherRepo;
	
	
	
	@Override
	public Voucher saveVoucher(VoucherModelDto voucher) {
	
		Voucher voucher2=new Voucher();
		voucher2.setVoucherseries(voucher.getVoucherseries());
		Voucher save = voucherRepo.save(voucher2);
		
		return save;
	}

	@Override
	public List<Voucher> getAll() {
		List<Voucher> allVoucher = voucherRepo.findAll();
		return allVoucher;
	}

	@Override
	public Optional<Voucher> getById(long id) {
		
		Optional<Voucher> findById = voucherRepo.findById(id);
		return findById;
	}

	@Override
	public String updateVoucher(Voucher voucher,long id) {

		if(voucherRepo.findById(id).isPresent()) {
			voucher.setId(id);
			voucherRepo.save(voucher);
			return "succesfully update the voucher with id "+id;
			}
		return "no voucher is present with id "+id;
	}

	@Override
	public String deleteVoucher(long id) {
		// TODO Auto-generated method stub
		if (voucherRepo.findById(id).isPresent()) {
			voucherRepo.deleteById(id);
			return "voucher delete succesfully with id "+id ;
		}
		return "no voucher is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexVoucherAsc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = voucherRepo.count();
		long pages = countpages / pagesize;

		List<IndexVoucher> ipo = voucherRepo.indexvoucher(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexVoucherDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = voucherRepo.count();
		long pages = countpages / pagesize;

		List<IndexVoucher> ipo = voucherRepo.indexvoucher(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchVoucher(int pageno, int pagesize, String search) {
		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<IndexVoucher> voucher = voucherRepo.indexvoucher(search, p);
       long searchcount = voucher.size();
		
		response.put("data", voucher);
		response.put("SearchCount", searchcount);
		
		return response;
	}

	@Override
	public List<IndexVoucher> getAllVoucher() {
List<IndexVoucher> all = voucherRepo.getAll();
		return all;
	}

	
	
	
}
