package com.SCM.service;

import java.util.List;

import com.SCM.dto.DistributorAddressDto;

public interface DistributorAddressService {
	
	public List<DistributorAddressDto> findDistributorAddressByDistributor(Long id);
	
	
	public String deleteByDistributorAddressId(Long id);

}
