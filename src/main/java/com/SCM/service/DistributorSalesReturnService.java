package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.dto.DistributorSalesReturnDto;
import com.SCM.model.DistributorSalesReturn;

public interface DistributorSalesReturnService {
	
	public DistributorSalesReturn saveDistributorSalesReturn(DistributorSalesReturnDto distributorSalesReturnDto);
	
	public List<DistributorSalesReturn> getAll();
	
	public Optional<DistributorSalesReturn> findByDistrubutorSalesReturnId(long id);
	
	public DistributorSalesReturn updateDistributorSalesReturn(DistributorSalesReturnDto distributorSalesReturnDto,long id);
	
	public void deleteDistributorSalesReturn(long id);

}
