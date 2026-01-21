package com.SCM.service;

import java.util.List;

import com.SCM.dto.RetailerAddressDto;

public interface RetailerAddressService {
	
	public List<RetailerAddressDto> findAllAddressByRetailerId(Long id);
	
	public String deleteRetailerAddress(Long id);

}
