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

import com.SCM.IndexDto.PurchasePageDtoProjection;
import com.SCM.IndexDto.TranporterProjection;
import com.SCM.dto.TransportersDto;
import com.SCM.model.Transporters;
import com.SCM.repository.TanspoertRepository;
import com.SCM.service.TransportersService;

@Service
public class TransportersServiceImpl implements TransportersService {

	@Autowired
	private TanspoertRepository tanspoertRepository;

	@Override
	public Transporters saveTransporters(TransportersDto transportersDto) {
		// TODO Auto-generated method stub
		
		Transporters transporters=new Transporters();
		
		transporters.setTransporterGstin(transportersDto.getTransporterGstin());
		transporters.setTransporterName(transportersDto.getTransporterName());
		return tanspoertRepository.save(transporters);
	}

	@Override
	public List<Transporters> getAllTransporters() {
		// TODO Auto-generated method stub
		return tanspoertRepository.findAll();
	}

	@Override
	public Optional<Transporters> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Transporters> findBy= tanspoertRepository.findById(id);
		
		return findBy;
	}

	@Override
	public Transporters updateTransporters(TransportersDto transportersDto, long id) {
		// TODO Auto-generated method stub
		Transporters transporters=tanspoertRepository.findById(id).get();
		
		
		transporters.setTransporterGstin(transportersDto.getTransporterGstin());
		transporters.setTransporterName(transportersDto.getTransporterName());
		
		
		
		
		return tanspoertRepository.save(transporters);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		tanspoertRepository.deleteById(id);
		
		
	}
	
	
	@Override
	public Map<String, Object> indexTransporterAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = tanspoertRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TranporterProjection> ipo = tanspoertRepository.indexTransporter(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> indexTransporterDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = tanspoertRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TranporterProjection> ipo = tanspoertRepository.indexTransporter(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> searchByTransporetes(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		List<TranporterProjection> receipt = tanspoertRepository.searchByTransporters(search, p);

		int size = receipt.size();

		response.put("data", receipt);

		response.put("SearchCount", size);

		return response;
	}
	
	
	
	
	
}
