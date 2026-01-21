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

import com.SCM.IndexDto.IndexRepackingPagination;
import com.SCM.dto.RepackingDto;
import com.SCM.model.Brand;
import com.SCM.model.Repacking;
import com.SCM.model.RepackingActivityLog;
import com.SCM.repository.BrandRepo;
import com.SCM.repository.RepackingActivityLogRepo;
import com.SCM.repository.RepackingRepo;
import com.SCM.service.RepackingService;

@Service
public class RepackingServiceImpl implements RepackingService {

	@Autowired
	private RepackingRepo repackingRepo;
	
	@Autowired
	private BrandRepo brandRepo;

	@Autowired
	private RepackingActivityLogRepo repackingActivityLogRepo;
	
	@Override
	public Repacking save(RepackingDto repackingDto) {
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetailsImpl.getUsername();
		Long id = repackingDto.getBrand().getId();
		Brand b = brandRepo.findById(id).get();
		String brandName = b.getBrandName();
		
		Repacking r = new Repacking();	
		r.setBrand(repackingDto.getBrand());	
		r.setBrandname(brandName);
		r.setCreateddate(LocalDate.now());
		r.setCreatedtime(LocalTime.now());
		r.setOperatorname(username);
		r.setRepackingItems(repackingDto.getRepackingItems());
			
		Repacking save = repackingRepo.save(r);
				
	    RepackingActivityLog rpal = new RepackingActivityLog();
		
	    System.out.println("+++++++++++++");
	    
		if(rpal.getUnqno() == null)
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
			rpal.setCreateddate(LocalDate.now());
			rpal.setCreatedtime(LocalTime.now());
			rpal.setLoggeduser(username);
			rpal.setRepackingid(save.getId());
			rpal.setUnqno(uniq + in.get(i));

			repackingActivityLogRepo.save(rpal);
		}	
		return save;
	}

	
	
	@Override
	public Map<String, Object> IndexRepackingAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = repackingRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexRepackingPagination> indexRepackingPaginations = repackingRepo.indexRepackingPaginations(p);

		response.put("Index", indexRepackingPaginations);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexRepackingDesc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = repackingRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexRepackingPagination> indexRepackingPaginations = repackingRepo.indexRepackingPaginations(p);

		response.put("Index", indexRepackingPaginations);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchRepacking(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexRepackingPagination> indexRepackingPaginations = repackingRepo.indexRepackingPaginations(search, p);

		long searchcount = indexRepackingPaginations.size();

		response.put("data", indexRepackingPaginations);
		response.put("SearchCount", searchcount);

		return response;
	}
}
