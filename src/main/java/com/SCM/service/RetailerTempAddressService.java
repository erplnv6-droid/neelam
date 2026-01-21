package com.SCM.service;

import java.util.List;

import com.SCM.dto.RetailerTemporaryAddressDto;

public interface RetailerTempAddressService {
	
	
	public List<RetailerTemporaryAddressDto> findRetailerTempAddByRTId(Long id);
	
	public String deleteById(Long id);

}
