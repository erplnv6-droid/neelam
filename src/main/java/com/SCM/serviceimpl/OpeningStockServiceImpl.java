package com.SCM.serviceimpl;

import com.SCM.IndexDto.IndexOpeningStock;
import com.SCM.IndexDto.IndexOpeningStockEntity;
import com.SCM.dto.OpeningStockDto;
import com.SCM.mapper.IndexOpeningStockMapper;
import com.SCM.model.OpeningStock;
import com.SCM.repository.OpeningStockRepository;
import com.SCM.service.OpeningStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpeningStockServiceImpl implements OpeningStockService {


	
//    @Autowired
//    private OpeningStockRepository openingStockRepository;
//   @Autowired
//   private IndexOpeningStockMapper openingStockMapper;
//
//
//    @Override
//    public OpeningStock saveOpeningStock(OpeningStock openingStock) {
//        return openingStockRepository.save(openingStock);
//    }
//
//    
//    @Override
//    public List<OpeningStockDto> getAllOpeningStock() {
//    	
//    	List<OpeningStockDto> openingStockDtos = new ArrayList<>();
//    	
//    	for(OpeningStock openingStock : openingStockRepository.findAll())
//    	{
//    		OpeningStockDto openingStockDto = new OpeningStockDto();
//    		openingStockDto.setDate(openingStock.getDate());
//    		openingStockDto.setId(openingStock.getId());
//    		openingStockDto.setQty(openingStock.getQty());
//    		openingStockDto.setProduct(openingStock.getProduct());
//    		
//    		openingStockDtos.add(openingStockDto);
//    	}
//    	
//       return openingStockDtos;
//    }
//
//    @Override
//    public OpeningStock getOpeningStockById(int id) {
//        return openingStockRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public String deleteOpeningStock(int id) {
//    	
//        openingStockRepository.deleteById(id);
//
//        return "Opening Stock Removed !!"+ id;
//    }
//
//    @Override
//    public OpeningStock updateOpeningStock(OpeningStock openingStock, int id) {
//
//        OpeningStock existingOpeningStock = openingStockRepository.findById(id).orElse(null);
//        existingOpeningStock.setPid(openingStock.getPid());
//        existingOpeningStock.setWid(openingStock.getWid());
//        existingOpeningStock.setQty(openingStock.getQty());
//        existingOpeningStock.setDate(openingStock.getDate());
//        existingOpeningStock.setProduct(openingStock.getProduct());
//
//        return openingStockRepository.save(existingOpeningStock);
//    }

    

	@Autowired
	private OpeningStockRepository openingStockRepository;

	@Autowired
	private IndexOpeningStockMapper openingStockMapper;

	@Override
	public OpeningStock saveOpeningStock(OpeningStock openingStock) {
		return openingStockRepository.save(openingStock);
	}

	@Override
	public List<OpeningStockDto> getAllOpeningStock() {

		List<OpeningStockDto> openingStockDtos = new ArrayList<>();

		for (OpeningStock openingStock : openingStockRepository.findAll()) {
			
			OpeningStockDto openingStockDto = new OpeningStockDto();
			openingStockDto.setDate(openingStock.getDate());
			openingStockDto.setId(openingStock.getId());
			openingStockDto.setQty(openingStock.getQty());
			openingStockDto.setProduct(openingStock.getProduct());

			openingStockDtos.add(openingStockDto);
		}

		return openingStockDtos;
	}

	@Override
	public OpeningStock getOpeningStockById(int id) {
		return openingStockRepository.findById(id).orElse(null);
	}

	@Override
	public String deleteOpeningStock(int id) {

		openingStockRepository.deleteById(id);

		return "Opening Stock Removed !!" + id;
	}

	@Override
	public OpeningStock updateOpeningStock(OpeningStock openingStock, int id) {

		OpeningStock existingOpeningStock = openingStockRepository.findById(id).orElse(null);
		existingOpeningStock.setPid(openingStock.getPid());
		existingOpeningStock.setWid(openingStock.getWid());
		existingOpeningStock.setQty(openingStock.getQty());
		existingOpeningStock.setDate(openingStock.getDate());
		existingOpeningStock.setProduct(openingStock.getProduct());

		return openingStockRepository.save(existingOpeningStock);
	}


//    --------------------------sahil-----------------------------------------//

	@Override
	public Map<String, Object> IndexOpeningStockAsc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = openingStockRepository.count();
		long pages = countpages / pagesize;

		


		long rem = countpages % pagesize;
		if (rem > 0) {

			pages++;
		}

		List<IndexOpeningStock> ipo = openingStockRepository.indexOpeningStocks(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}




	@Override
	public Map<String, Object> IndexOpeningStockDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = openingStockRepository.count();
		long pages = countpages / pagesize;

		
	


		long rem = countpages % pagesize;
		if (rem > 0) {

			pages++;
		}

		List<IndexOpeningStock> ipo = openingStockRepository.indexOpeningStocks(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}



	
//	@Override
//	public Map<String, Object> SearchOpeningStock(int pageno, int pagesize, String search) {
//		
//		Pageable p=PageRequest.of(pageno, pagesize);
//		Map<String, Object> response = new HashMap<>();		
//
//		
//		List<IndexOpeningStockEntity>  entity=openingStockRepository.SearchByOpeningStock(search, p);
//		long searchcount = entity.size();
//		
//		response.put("SearchCount", searchcount);
//		
//		List<IndexOpeningStock> dto=entity.stream().map((osd)-> openingStockMapper.toDto(osd)).collect(Collectors.toList());

	@Override
	public Map<String, Object> SearchOpeningStock(int pageno, int pagesize, String search) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<IndexOpeningStockEntity> entity = openingStockRepository.SearchByOpeningStock(search, p);
		long searchcount = entity.size();

		response.put("SearchCount", searchcount);

		List<IndexOpeningStock> dto = entity.stream().map((osd) -> openingStockMapper.toDto(osd))
				.collect(Collectors.toList());

		response.put("data", dto);

		return response;
	}


}
