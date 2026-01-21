package com.SCM.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.RetailerAddressDto;
import com.SCM.dto.RetailerAddressMapper;
import com.SCM.model.RetailerAddress;
import com.SCM.repository.RetailerAddressRepository;
import com.SCM.service.RetailerAddressService;

@Service
public class RetailerAddressServiceImpl implements RetailerAddressService{
	
	@Autowired
	private RetailerAddressRepository retailerAddressRepository;
	
	@Autowired
	private RetailerAddressMapper retailerAddressMapper;

	@Override
	public List<RetailerAddressDto> findAllAddressByRetailerId(Long id) {
		
		List<RetailerAddress> retailerId = retailerAddressRepository.findAllAddressByRetailerId(id);
		
		return retailerId.stream().map(retailerAddressMapper :: mapToDto).collect(Collectors.toList());
	}

	@Override
	public String deleteRetailerAddress(Long id) {
		
		retailerAddressRepository.deleteById(id);
		
		return "";
	}

}
