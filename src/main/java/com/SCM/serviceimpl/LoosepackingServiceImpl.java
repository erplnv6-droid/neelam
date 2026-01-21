package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexBranch;
import com.SCM.IndexDto.IndexLoosePacking;
import com.SCM.IndexDto.IndexLoosePackingPagination;
import com.SCM.dto.LoosepackingDto;
import com.SCM.model.LoosePacking;
import com.SCM.model.LoosepackingActivityLog;
import com.SCM.repository.LoosePackingRepo;
import com.SCM.repository.LoosepackingActivityLogRepo;
import com.SCM.service.LoosePackingService;


@Service
public class LoosepackingServiceImpl implements LoosePackingService {
    
	@Autowired
	private LoosePackingRepo loosePackingRepo;
	
	@Autowired
	private LoosepackingActivityLogRepo loosepackingActivityLogRepo;
	

	
	@Override
	public LoosePacking save(LoosepackingDto loosepackingDto) {
		
		LoosePacking lp = new LoosePacking();	
		lp.setBrand(loosepackingDto.getBrand());	
		lp.setRemarks(loosepackingDto.getRemarks());
		lp.setQty(loosepackingDto.getQty());
		lp.setPcs(loosepackingDto.getPcs());
		lp.setProduct(loosepackingDto.getProduct());
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetailsImpl.getUsername();
		
		lp.setOperatorname(username);
		lp.setCreateddate(LocalDate.now());
		lp.setCreatedtime(LocalTime.now());
		
		LoosePacking save = loosePackingRepo.save(lp);
		
		LoosepackingActivityLog lpal = new LoosepackingActivityLog();
		
		if(lpal.getUnqno() == null)
		{
			ArrayList<Integer> in = new ArrayList<Integer>();
			int i;
			for (i = 1; i < 1000000; i++) {
				in.add(i);

			}
			Collections.shuffle(in);
			for (i = 0; i < 1; i++) {
				System.out.println(in.get(i));
			}

			String uniq = "UNQ- ";
			lpal.setCreateddate(LocalDate.now());
			lpal.setCreatedtime(LocalTime.now());
			lpal.setLoggeduser(username);
			lpal.setLoosepackingid(save.getId());
			lpal.setUnqno(uniq + in.get(i));

			loosepackingActivityLogRepo.save(lpal);
		}

		return save;
	}

	
	@Override
	public LoosePacking getById(int id) {
		LoosePacking loosePacking = loosePackingRepo.findById((long) id).get();
		return loosePacking;
	}


	@Override
	public List<IndexLoosePacking> FetchProductByBrand(int bid) {
		
		List<IndexLoosePacking> fetchProductByBrand = loosePackingRepo.fetchProductByBrand(bid);
		
		return fetchProductByBrand;
	}


	@Override
	public Map<String, Object> IndexLoocsePackingAsc(int pageno, int pagesize, String field) {
	
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = loosePackingRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexLoosePackingPagination> indexLoosePacking = loosePackingRepo.IndexLoosePacking(p);

		response.put("Index", indexLoosePacking);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> IndexLoosePackingDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = loosePackingRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexLoosePackingPagination> indexLoosePacking = loosePackingRepo.IndexLoosePacking(p);

		response.put("Index", indexLoosePacking);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> SearchLoosePacking(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexLoosePackingPagination> searchByLoosePacking = loosePackingRepo.SearchByLoosePacking(search, p);

		long searchcount = searchByLoosePacking.size();

		response.put("data", searchByLoosePacking);
		response.put("SearchCount", searchcount);

		return response;
	}

}
