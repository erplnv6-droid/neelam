package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.SCM.dto.DistributorOpeningStockDto;
import com.SCM.model.DistributorOpeningStock;

public interface DistributorOpeningService {

	public DistributorOpeningStockDto createOpeningStock(DistributorOpeningStockDto distributorOpeningStockDto);

	public List<DistributorOpeningStockDto> findAllOpeningStock();

	public Optional<DistributorOpeningStockDto> findByOpeningStockId(long id);
	
	public DistributorOpeningStock findByOpeningStockId1(long id);

	public DistributorOpeningStockDto updateByOpeningStock(DistributorOpeningStockDto distributorOpeningStockDto);
	
	public DistributorOpeningStock update(DistributorOpeningStockDto distributorOpeningStockDto,long id);

	public String deleteByOpeningStockId(long id);

	public String deleteAllOpeningStock();

	
	public Map<String, Object> indexDistributorOpeningStockDesc(int pagno, int pagesize, String field);

	public Map<String, Object> searchByDistributorOpeningStock(int pageno, int pagesize, String search);

	public Map<String, Object> indexDistributorOpeningStockAsc(int pagno, int pagesize, String field);

}
